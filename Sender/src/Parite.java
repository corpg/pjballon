/** 
	Classe qui permet de calculer une parité paire ou impaire sur 2 à 8 bits (les octets passés en paramètres doivent-être du type 0000 000x).
	@author : Etienne Glossi
	@version : 1.0
*/

public final class Parite
{
	/** Pour calculer une parite paire sur 2 bits */
	public static byte paire(byte b1, byte b2)
	{
		if((b1+b2)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite paire sur 3 bits */
	public static byte paire(byte b1, byte b2, byte b3)
	{
		if((b1+b2+b3)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite paire sur 4 bits */
	public static byte paire(byte b1, byte b2, byte b3, byte b4)
	{
		if((b1+b2+b3+b4)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite paire sur 5 bits */
	public static byte paire(byte b1, byte b2, byte b3, byte b4, byte b5)
	{
		if((b1+b2+b3+b4+b5)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite paire sur 6 bits */
	public static byte paire(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6)
	{
		if((b1+b2+b3+b4+b5+b6)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite paire sur 7 bits */
	public static byte paire(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
	{
		if((b1+b2+b3+b4+b5+b6+b7)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite paire sur 8 bits */
	public static byte paire(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8)
	{
		if((b1+b2+b3+b4+b5+b6+b7+b8)%2==0) return (byte)0;
		else return (byte)1;
	}
	
	/** Pour calculer une parite impaire sur 2 bits */
	public static byte impaire(byte b1, byte b2)
	{
		if((b1+b2)%2==0) return (byte)1;
		else return (byte)0;
	}
	
	/** Pour calculer une parite impaire sur 3 bits */
	public static byte impaire(byte b1, byte b2, byte b3)
	{
		if((b1+b2+b3)%2==0) return (byte)1;
		else return (byte)0;
	}
	
	/** Pour calculer une parite impaire sur 4 bits */
	public static byte impaire(byte b1, byte b2, byte b3, byte b4)
	{
		if((b1+b2+b3+b4)%2==0) return (byte)1;
		else return (byte)0;
	}
	
	/** Pour calculer une parite impaire sur 5 bits */
	public static byte impaire(byte b1, byte b2, byte b3, byte b4, byte b5)
	{
		if((b1+b2+b3+b4+b5)%2==0) return (byte)1;
		else return (byte)0;
	}
	
	/** Pour calculer une parite impaire sur 6 bits */
	public static byte impaire(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6)
	{
		if((b1+b2+b3+b4+b5+b6)%2==0) return (byte)1;
		else return (byte)0;
	}
	
	/** Pour calculer une parite impaire sur 7 bits */
	public static byte impaire(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
	{
		if((b1+b2+b3+b4+b5+b6+b7)%2==0) return (byte)1;
		else return (byte)0;
	}
	
	/** Pour calculer une parite impaire sur 8 bits */
	public static byte impaire(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8)
	{
		if((b1+b2+b3+b4+b5+b6+b7+b8)%2==0) return (byte)1;
		else return (byte)0;
	}
}

