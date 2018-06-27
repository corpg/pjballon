/**
	Classe java permettant de rediriger les donn�es re�u vers le serveur TCP. 
	Se charge aussi de l'interfa�age du lien s�rie avec l'interface graphique.
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
		//D�marrage du thread d'�coute
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
			
			//todo: v�rifier ici le fanion de d�but de test
			
			if(this.os!=null) 
			{
				try{os.write(data);}
				catch(IOException e){System.out.printf("Erreur lors de l'ecriture sur le socket\n");};
			}
			jt.append(new String(new byte[]{data}));
			jt.setCaretPosition(jt.getDocument().getLength ()); //on met le curseur � la fin pour d�placer la scrollbar
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