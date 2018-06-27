/**
	Fichier main qui ecoute sur le port série et décode chaque couple d'octet reçu
	@author Etienne
	
	==> voir les design pattern "chainf of responsibility", et "head first"
	==> architecture procducteur - consommateur pour le décodeur
*/

import gnu.io.*;
import java.io.*;
import java.util.concurrent.*;

public class EcouteSerial implements Runnable
{
	public EcouteSerial(InputStream is, StatThread st)
	{
		this.is = is;
		this.dataBuf = new LinkedBlockingQueue<Byte>(1200); //taille de 1200 octets pour stocker 2secondes max de données à 4800bauds
		this.buf = new BufferedInputStream(this.is);
		this.st = st;
	}
	
	public void run()
	{
		ExecutorService threadsDecodeurs = Executors.newSingleThreadExecutor(); //3 fois plus rapide qu'un jeu de thread
		
		byte[] data = new byte[2];
		
		/*
		
		// Algorithme de décodage détectant la parte de paquet ainsi que les erreurs éventuelles sur le bit de poids
		// A soumettre à des tests approfondis
		
		try{
			while(buf.available()<2){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
			buf.read(data, 0, 2); //lecture de deux octets a partir du flux d'entrée
		
			while(true){
				if(Decodeur.getBit(data[0], 1)==1 && Decodeur.getBit(data[1], 1)==0)
				{
					threadsDecodeurs.submit(new Decodeur(data, dataBuf, this.st));
					st.receiveByte += 2;
					
					while(buf.available()<2){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
					buf.read(data, 0, 2);
				}
				else //perte d'un paquet ou erreur ?
				{
					while(buf.available()<1){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
					byte nbyte = (byte) buf.read();

					if(Decodeur.getBit(data[1], 1)==1 && Decodeur.getBit(data[0], 1)==1)
					{
						if(Decodeur.getBit(nbyte, 1)==0) //on a perdu un paquet !
						{
							data[0] = data[1];
							data[1] = nbyte;
						}
						else //bit de poid de nbyte = 1 --> erreur sur le bit de poids de data[1]?
						{
							threadsDecodeurs.submit(new Decodeur(data, dataBuf, this.st));
							st.receiveByte += 2;
							
							data[0] = nbyte;
							
							while(buf.available()<1){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
							data[1] = (byte) buf.read();
						}
					}
					else if(Decodeur.getBit(data[1], 1)==1 && Decodeur.getBit(data[0], 1)==0) //perte de paquet !
					{
						data[0] = data[1];
						data[1] = nbyte;
					}
					else if(Decodeur.getBit(data[1], 1)==0 && Decodeur.getBit(data[0], 1)==0)
					{
						if(Decodeur.getBit(nbyte, 1)==0) //perte de paquet ou erreur ?
						{
							data[0] = data[1];
							data[1] = nbyte;
							threadsDecodeurs.submit(new Decodeur(data, dataBuf, this.st));
							st.receiveByte += 2;
							
							while(buf.available()<2){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
							buf.read(data, 0, 2);
						}
						else //erreur sur le bit de poids
						{
							threadsDecodeurs.submit(new Decodeur(data, dataBuf, this.st));
							st.receiveByte += 2;
							data[0] = nbyte;
							
							while(buf.available()<1){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
							data[1] = (byte) buf.read();
						}
					}
				}
			}
		}
		catch(IOException e){
			System.out.printf("Erreur lors de l'écoute du port série.");
		}
		
		*/
		
		// Algorithme se chargeant uniquement de détecter la perte de paquet		
		while(true){
			try
			{
				if(buf.available()<2) //attente...
				{
					try{Thread.sleep(50);}
					catch(InterruptedException e){}
					continue;
				}			
				
				buf.read(data, 0, 2); //lecture de deux octets à partir du flux d'entrée
				
				if(Decodeur.getBit(data[0], 1)!=1 || Decodeur.getBit(data[1], 1)!=0) //on peut soupconner un problème !
				{
					buf.mark(1); //on marque l'endroit où on en est dans la lecture du flux pour pouvoir revenir en arrière
					
					while(buf.available()<1){try{Thread.sleep(50);}catch(InterruptedException e){}} //on attend que les data soient dispo
					byte nbyte = (byte) buf.read(); //pour pouvoir réfléchir sur la suite de bit reçu
					
					if(Decodeur.getBit(data[1], 1)==1)
					{
						if(Decodeur.getBit(nbyte, 1)==0) //paquet perdu ! -- reçu x10
						{
							data[0] = data[1];
							data[1] = nbyte;
						}
						else // on a recu x11
						{
							buf.reset(); //on reprend la lecture du flux au marquage
							continue; //on continue la lecture du flux
						}
					}
					else // Decodeur.getBit(data[1], 1) = 0
					{
						if(Decodeur.getBit(nbyte, 1)==1) //reçu 001
						{
							buf.reset(); //on reprend au marquage pour pouvoir réflechir sur une nouvelle suite de bit
							continue; //on continue la lecture du flux
						}
						else //reçu 000
						{
							continue; //ben là, on peut rien faire et on recommence la lecture du flux
						}
					}
				}
				
				//décodage
				threadsDecodeurs.submit(new Decodeur(data, dataBuf, this.st));
				st.receiveByte += 2; //incrémentation du nombre d'octet reçus et traités
			}
			catch(IOException e){
				System.out.printf("Erreur lors de l'ecoute du port série.\nStop du Thread.");
				break;				
			}
		}
		// fin de l'écoute
	}
	
	public void setInputStream(InputStream is)
	{
		this.is = is;
		this.buf = new BufferedInputStream(this.is);
	}
	
	public Byte getByte()
	{
		return this.dataBuf.poll();
	}
	
	private InputStream is;
	private BufferedInputStream buf;
	public LinkedBlockingQueue<Byte> dataBuf;
	private StatThread st;
}