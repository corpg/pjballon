/**
	Exception générée lors d'un problème à l'initialisation du port série.
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
		super("Erreur d'initialisation du port série "+name+".");
	}
}