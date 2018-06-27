/**
	Fournit des méthodes statiques pour gérer les sockets.
	@author Etienne Glossi
	@version 1.0
*/

import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.StringTokenizer;

public class ComSocket
{
	/** Retourne l'OutputStream du socket TCP dont l'ip et le port sont fournis en paramètre.
		@param ip IP du serveur distant.
		@param port Port sur lequel envoyer les données.
		@param owner L'objet responsable de l'ouverture du socket.
		@return Le flux de sortie du socket ouvert.
		@throw SocketInitException Si aucune connection n'a pu être établie avec le serveur distant.
	*/
	public static OutputStream getSocketOutputStream(String ip, int port, Object owner) throws SocketException
	{
		ComSocket.checkSocketFormat(ip, port);
		try
		{
			Socket s = new Socket(ip, port);
			s.setSendBufferSize(255);
			s.shutdownInput();
			ComSocket.openedCommPort.add(new Object[]{owner, s});
			return s.getOutputStream();
		}
		catch(IOException e)
		{ 
			throw new SocketException("Impossible de se connecter au serveur "+ip+":"+port, e.getCause());
		}
	}
	
	/** Vérifie si le socket IP:port est un socket correct.
		@param ip IP
		@param port Port
		@throw SocketInitException Si le port ou l'IP est incorrect.
	*/
	public static void checkSocketFormat(String ip, int port) throws SocketException
	{
		//on commence par le port
		if(port<0 || port>65535) {
			SocketException e = new SocketException("Port incorrect ! Doit-être compris entre 0 et 65535.", true);
			e.setPort(port);
			throw e;
		}
		
		//l'IP
		StringTokenizer tokenizer = new StringTokenizer(ip, ".");
		SocketException se = new SocketException("Adresse IP incorrecte !", true);
		se.setIp(ip);
		for(int i=0;i<4;i++)
		{
			int b = 0;
			if(!tokenizer.hasMoreTokens()) throw se;
			try
			{
				b = Integer.parseInt(tokenizer.nextToken());
			}
			catch(NumberFormatException e){throw se;}
			
			if(b<0 || b>256) throw se;
		}
		if(tokenizer.hasMoreTokens()) throw se;
		
		//Tout est ok :)
		return;
	}
	
	/** Méthode qui ferme le socket IP:port ouvert par owner avec la méthode getSocketOutputStream().
		@param ip L'IP du socket (serveur).
		@param port Le port du socket (serveur).
		@param owner L'objet responsable de l'ouverture.
	*/
	public static void closeOpenedSocket(String ip, int port, Object owner)
	{	
		Socket s = null;
		
		for(Object[] comPort: ComSocket.openedCommPort)
		{
			s = (Socket) comPort[1];
			//on recherche le socket
			if(comPort[0]==owner && port==s.getPort() && ip.equals(s.getInetAddress().toString()))
			{
				try{s.close();}
				catch(IOException e){e.printStackTrace();}
				ComSocket.openedCommPort.remove(comPort);
				break;
			}
		}
	}
	
	/** Méthode qui ferme tous les sockets ouverts par owner avec la méthode getSocketOutputStream().
		@param owner L'objet responsable de l'ouverture.
	*/
	public static void closeAllOpenedSocket(Object owner)
	{
		Socket s = null;
		
		for(Object[] comPort: ComSocket.openedCommPort)
		{
			if(comPort[0]==owner)
			{
				s = (Socket) comPort[1];
				try{s.close();}
				catch(IOException e){e.printStackTrace();}
				ComSocket.openedCommPort.remove(comPort);
			}
		}
	}
	
	/** Contient la liste de tous les sockets ouverts. */
	private static Vector<Object[]> openedCommPort = new Vector<Object[]>(1);
}