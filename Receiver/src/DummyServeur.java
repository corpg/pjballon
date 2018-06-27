import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class DummyServeur
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		InetSocketAddress add = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
		
		ServerSocket ssock = null;
		try
		{
			 ssock = new ServerSocket();
			ssock.bind(add);
			
			
		} catch (IOException e1)
		{
			System.err.println("Echec de creation du serveur...");
			System.exit(1);
		}
		Socket sock = null;
		
		InputStreamReader isr = null;
		try
		{
			sock = ssock.accept();
			InputStream in = sock.getInputStream();
			isr = new InputStreamReader(in, "US-ASCII");
		} 
		catch (Exception e)
		{
			System.err.println("Echec de communication avec le client...");
			System.exit(1);
		}
		while (true)
		{
			int lu;
			try
			{
				lu = isr.read();
				
			} catch (IOException e)
			{
				System.out.println("Fin de communication");
				break;
			}
			System.out.print(""+ (char)lu);
			if (lu == ';') System.out.println();
		}
	}
}
