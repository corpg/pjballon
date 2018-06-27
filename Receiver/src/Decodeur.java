/**
	Classe qui permet de décoder un octet reçu
	@author Etienne
	
	==> http://www.java.happycodings.com/Core_Java/code1.html
*/

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;


public class Decodeur implements Runnable
{
	public Decodeur(byte[] data, LinkedBlockingQueue<Byte> receiveBuffer, StatThread st)
	{
		/*if(data.length() > 2) 
		{
			System.out.println("Tros d'octets à décoder. Doivent-être égaux à 2.");
			return null;
		}*/
		
		this.dataReceive = data.clone(); //attention, on copie les données !
		this.receiveBuffer = receiveBuffer;
		this.octet = 0x00;
		this.st = st;
	}
	
	public Decodeur(byte[] data, StatThread st)
	{
		this(data, null, st);
	}

	public void run()
	{
		//decodage de hamming
		this.decode();
		
		if(this.receiveBuffer==null)
			System.out.println(this.getCaractere()); //si sortie null, on affiche le caractère
		else
			receiveBuffer.offer(this.octet); //sinon, on l'insére dans le buffer du thread d'écoute
	}
	
	/** Decodage des donnees en utilisant le codage de Hamming[7, 4, 3] */
	private void decode() {
		int errorbit = 0; //variable qui stocke le numero du bit où il y a eu une erreur
		
		for(int i=0;i<2;i++){
			if((errorbit = traiteOctet(this.dataReceive[i]))!=0){
				System.out.printf("Erreur detecte sur le bit %d du caratere '%s'. Apres correction: ", errorbit+1,UnicodeFormatter.byteToHex(this.dataReceive[i]));
				this.dataReceive[i] = this.corrigeError(this.dataReceive[i], errorbit);
				System.out.printf("%s\n", UnicodeFormatter.byteToHex(this.dataReceive[i]));
			}
		}
			
		//creation du caractère
		octet = (byte)(this.removeParite(this.dataReceive[0]) |  this.removeParite(this.dataReceive[1]));
	}
	
	public char getCaractere() {
		return (char)octet;
	}
	
	
	
	/* pour traitet un octet lors du décodage. Retourne un octet indiquant l'emplacement de l'erreur */
	private int traiteOctet(byte octet)
	{
		/* bits de donnee: b1, b2, b3, b4, b5, b6, b7, b8 */
		byte bp = (byte) ((octet & 0x80) >> 7);
		byte p1 = (byte) ((octet & 0x40) >> 6);
		byte p2 = (byte) ((octet & 0x20) >> 5);
		byte b1 = (byte) ((octet & 0x10) >> 4);
		byte p3 = (byte) ((octet & 0x08) >> 3);
		byte b2 = (byte) ((octet & 0x04) >> 2);
		byte b3 = (byte) ((octet & 0x02) >> 1);
		byte b4 = (byte) (octet & 0x01);
		
		//System.out.printf("%d%d%d%d %d%d%d%d\n",bp,b1,b2,b3,b4,b5,b6,b7);
		
		return (Parite.paire(p3, b2, b3, b4) << 2) | (Parite.paire(p2, b1, b3, b4) << 1) | (Parite.paire(p1, b1, b2, b4));
	}
	
	/* methode pour coriger une erreur sur un bit avec un OU-Exclusif*/
	private byte corrigeError(byte car, int bit)
	{
		int[] position = {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
		this.st.nbErrorByte += 1; //on corrige une erreur donc il y a eu une erreur...
		return (byte)(car ^ position[bit]);
	}
	
	/** Pour accéder a la valeur d'un bit dans un octet*/
	public static byte getBit(byte car, int index){
		int[] position = {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
		return (byte)((car & position[index-1]) >> (8 - index));
	}
	
	private byte removeParite(byte car){
		if(this.getBit(car, 1) == 1) return (byte)((car & 0x10) << 3 | (car & 0x04) << 4 | (car & 0x02) << 4 | (car & 0x01) << 4); //quartet fort
		else return (byte)((car & 0x10) >> 1| (car & 0x04)| (car & 0x02)| (car & 0x01)); //quartet faible
	}
	
	private byte octet;
	private byte[] dataReceive;
	private LinkedBlockingQueue<Byte> receiveBuffer; //un buffer tampon qui stocke les données reçus
	private StatThread st;
}