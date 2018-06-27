/**
	Thread qui éxecute la procédure de test de la connection.
	7 phases:	* Ouverture du fichier de test (5%)
					* Détermination du nombre d'octets à envoyer (6%)
					* Envoi d'un fanion indiquant le début du test (8%)
					* Envoi de l'heure de début de test (10%)
					* Envoi des données (97%)
					* Envoi de l'heure de fin de test (99%)
					* Envoi d'un fanion indiquant la fin du test (100%)
		
	@author Etienne Glossi
*/

import java.util.Date;
import java.io.*;

public class TestTransmission implements Runnable
{
	public TestTransmission(MenuTestTransmission menuRoot, OutputStream os, StatThread st) throws TransmissionTestException
	{
		this(menuRoot, os, TESTFILENAME, st);
	}
	
	public TestTransmission(MenuTestTransmission menuRoot, OutputStream os, String filename, StatThread st) throws TransmissionTestException
	{
		this.menuRoot = menuRoot;
		this.os = os;
		this.tailleFichier = 0;
		this.st = st;
		
		//ouverture du fichier
		this.menuRoot.setPbString("Ouverture du fichier de test");
		this.fichier = null;
		File file = new File("test/"+filename);
		try {
			this.fichier = new FileInputStream(file);
		}
		catch(FileNotFoundException e){
			this.menuRoot.setPbString("Echec du test !");
			throw new TransmissionTestException("Impossible d'ouvrir le fichier "+filename);
		}
		this.menuRoot.setPbProgressValue(2);
		
		this.tailleFichier = (int)file.length();
	}

	public void run()
	{
		String start = String.valueOf(new Date().getTime());
		
		//phase 1: Initilisation
		this.menuRoot.setPbProgressValue(5);
		this.menuRoot.setPbString("Initialisation");
		
		//phase 2: Détermination du nombre d'octets à envoyer
		int taille = tailleFichier + (start.length() * 2);
		this.menuRoot.setPbProgressValue(6);
		
		//phase 3: Envoie d'un fanion de début de test
		new SendData(new String("#-@DébutTransmissionTest@\n"), os, st).run();
		this.menuRoot.setPbProgressValue(8);
	
		//phase 4: Envoie de l'heure de début de test
		new SendData(new String("@DateDébut@\n"+start+"\n"), os, st).run();
		this.menuRoot.setPbProgressValue(10);
		this.menuRoot.setPbString("Envoi des données");
		
		//phase5: Envoi des données
		new SendData(new String("@FichierTest@\n"), os, st).run();
		SendData sd = new SendData(fichier, tailleFichier, os, st);
		Thread ed = new Thread(sd, "SendData: EnvoieDesDonnees");
		ed.start();
		while(ed.isAlive()){
			this.menuRoot.setPbProgressValue(10+(int)(sd.getNombreOctetTraite()*87/tailleFichier));
			try{Thread.sleep(1000);}
			catch(InterruptedException e){break;}
		}
		this.menuRoot.setPbProgressValue(97);
		this.menuRoot.setPbString("Finalisation");
		
		//phase 6: Envoie de l'heure de fin
		new SendData(new String("\n@DateFin@\n"+String.valueOf(new Date().getTime())+"\n"), os, st).run();
		this.menuRoot.setPbProgressValue(99);
		
		//phase 7: Envoie d'un fanion de fin de test
		new SendData(new String("@FinTransmissionTest@-#"), os, st).run();
		this.menuRoot.setPbProgressValue(100);
		this.menuRoot.setPbString("Complete !");
		
		menuRoot.showFinishBouton();
	}

	private MenuTestTransmission menuRoot;
	private OutputStream os;
	private int tailleFichier;
	private FileInputStream fichier;
	private StatThread st;
	public static final String TESTFILENAME = "fichierDeTest.txt";
}