/**
	Classe java permettant de rediriger les données reçu vers le serveur TCP. 
	Se charge aussi de l'interfaçage du lien série avec l'interface graphique.
	@author Etienne Glossi
*/

import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JTextArea;
import java.io.IOException;

public class InterfaceEcouteSerial implements Runnable
{
	public InterfaceEcouteSerial(InputStream is, OutputStream os, JTextArea jt, StatThread st, FichierTexte backFile)
	{
		this.os = os; // vers le flux de sortie (socket)
		this.es = new EcouteSerial(is, st);
		this.jt = jt;
		this.t = null;
		this.backFile = backFile;
	}
	
	public InterfaceEcouteSerial(InputStream is, JTextArea jt, StatThread st, FichierTexte backFile)
	{
		this(is, null, jt, st, backFile);
	}
	
	public void run()
	{
		//Démarrage du thread d'écoute
		this.t = new Thread(this.es, "ThreadEcouteSerial");
		this.t.start();
		Byte data;
		
		while(true)
		{
			if((data=this.es.getByte()) == null)
			{
				try{Thread.sleep(50);}
				catch(InterruptedException e){}
				continue;
			}	
			//else
			
			//todo: vérifier ici le fanion de début de test
			
			if(this.os!=null) 
			{
				try{os.write(data);}
				catch(IOException e){System.out.printf("Erreur lors de l'ecriture sur le socket\n");};
			}
			jt.append(new String(new byte[]{data}));
			jt.setCaretPosition(jt.getDocument().getLength ()); //on met le curseur à la fin pour déplacer la scrollbar
			backFile.stockageFichierTexte(new String(new byte[]{data}));
		}
	}
	
	public void stopEcoute()
	{
		this.t.interrupt();
	}
	
	public void setOutputStream(OutputStream os)
	{
		this.os = os;
	}
	
	public void setInputStream(InputStream is)
	{
		this.es.setInputStream(is);
	}
	
	private EcouteSerial es;
	private StatThread st;
	private OutputStream os;
	private JTextArea jt;
	private Thread t;
	private FichierTexte backFile; //fichier de sauvegarde
}