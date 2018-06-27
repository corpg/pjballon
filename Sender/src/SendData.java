/** 
	Classe permettant de coder les données (inclusion d'un bit de poid et du codage de Hamming[7, 4, 3]) et de les envoyer sur le flux de sortie.
	--> Améliorer le debug lors de l'échec de l'envoie (ligne 69 et 85).
	@author Etienne Glossi
*/

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Date;

public class SendData implements Runnable
{
	/** Constructeur permettant d'initialiser l'objet pour qu'il envoie une chaine de caractères.
		@param text Le texte à envoyer.
		@param os Le flux de sortie sur lequel envoyer les données codées.
		@param st Le thread de statistique
	*/
	public SendData(String text, OutputStream os, StatThread st)
	{
		this.data = text;
		this.os = os;
		this.toSend = new byte[2];
		this.octetTraite = 0;
		st.waitByte += text.length()*2;
		this.st = st;
	}
	
	/** Constructeur permettant d'initialiser l'objet pour qu'il envoie un fichier.
		@param fichier Un flux d'entrée vers un fichier existant.
		@param os Le flux de sortie sur lequel envoyer les données codées.
		@param st Le thread de statistique
	*/
	public SendData(FileInputStream fichier, int length, OutputStream os, StatThread st)
	{
		this("", os, st);
		this.isFile = true;
		this.file = fichier;
		st.waitByte += length*2;
	}
	
	/** Méthode run appelé lors du start(), aprés la création du thread. */
	public void run()
	{
		/* Information de debug... */
		System.out.println("Start encode...");
		Date start = new Date();
		/* end */
		
		if(isFile) this.runFile();
		else this.runString();
		
		/* Information de debug... */
		Date finish = new Date();
		System.out.println("Temps d'execution: " + (finish.getTime() - start.getTime()) + " millisecondes.");
		/* end */
	}
	
	/** Méthode run pour coder et envoyer un fichier. */
	private void runFile()
	{
		Scanner in = new Scanner(this.file); //creation d'un scanner pour parcourir le fichier texte
		do
		{
			for(byte c: in.nextLine().getBytes())
			{
				sendChar(c);
			}
		} while(in.hasNextLine());
	}
	
	/** Méthode run pour coder et envoyer une chaîne de caractères. */
	private void runString()
	{
		for(byte c: this.data.getBytes())
		{
			sendChar(c);
		}
	}
	
	private void sendChar(byte c)
	{
		this.code(c);
		try
		{
			this.os.write(this.toSend[0]); //envoie du quartet fort
			this.os.write(this.toSend[1]); //envoie du quartet faible
		}
		catch(IOException e){e.printStackTrace();} //ameliorer le debug au moment de l'échec de l'envoi
		this.octetTraite++;
		st.sendByte+=2;
		st.waitByte-=2;
	}
	
	/** Codage des données en utilisant le codage de Hamming[7, 4, 3]. Stocke le résultat du codage dans le tableau toSend[].
		@param octet L'octet à coder.
	*/
	private void code(byte octet)
	{	
		// on parcours chaque quartet de chaque octet
		this.toSend[0] = this.traiteQuartet((byte)((octet & 0xf0) >> 4), true); // traitement du quartet de poid fort
		this.toSend[1] = this.traiteQuartet((byte)(octet & 0x0f), false); // traitement du quartet de poid faible
	}
	
	/** Traite séparément chaque quartet et y inclue les bits utiles pour coder les données.
		@param quartet Le quartet à coder (octet du type 0000 xxxx).
		@param quartetFort Booléen indiquant si le quartet est fort (true) ou faible (false).
		@return Le quartet codé (un octet BPxxx xxxx)
	*/
	private byte traiteQuartet(byte quartet, boolean quartetFort)
	{
		byte bp = 0x00;
		if(quartetFort) bp = 0x01;
		
		/* bits de donnee: b1, b2, b3, b4 */
		byte bit1 = (byte) ((quartet & 0x08) >> 3);
		byte bit2 = (byte) ((quartet & 0x04) >> 2);
		byte bit3 = (byte) ((quartet & 0x02) >> 1);
		byte bit4 = (byte) (quartet & 0x01);
		
		/* bits de parites paire */
		byte bitp1 = Parite.paire(bit1, bit4, bit2);
		byte bitp2 = Parite.paire(bit1, bit4, bit3);
		byte bitp3 = Parite.paire(bit2, bit3, bit4);
		
		// octet à envoyer: bp,bitp1,bitp2,bit1,bitp3,bit2,bit3,bit4
		return (byte)((bp << 7) | (bitp1 << 6) | (bitp2 << 5) | (bit1  << 4) | (bitp3 << 3) | (bit2 << 2) | (bit3 << 1) | (bit4));
	}
	
	/** Methode qui retourne le nombre d'octet traite (codé et envoyés).
		@return Le nombre d'octets
	*/
	public int getNombreOctetTraite()
	{
		return this.octetTraite;
	}

	//attributs
	/** La chaine de caractères à envoyer. */
	private String data;
	
	/** Le flux de sortie sur lequel envoyer les données. */
	private OutputStream os;
	
	/** Tableau contenant un octet codé.*/
	private byte[] toSend;
	
	/** Booléen qui indique si l'objet envoie un fichier (utilisé lors de la séléction de la méthode run). */
	private boolean isFile = false;
	
	/** Le flux du fichier d'entrée. */
	private FileInputStream file = null;
	
	/** Thread de stat */
	private StatThread st;
	
	/** Nombre d'octet traite */
	private int octetTraite;

}