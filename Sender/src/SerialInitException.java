/**
	Exception g�n�r�e lors d'un probl�me � l'initialisation du port s�rie.
	@author Etienne Glossi
*/

public class SerialInitException extends java.io.IOException
{
	public SerialInitException(String mes, Throwable e)
	{
		super(mes, e);
	}
	
	public SerialInitException(String name)
	{
		super("Erreur d'initialisation du port s�rie "+name+".");
	}
}