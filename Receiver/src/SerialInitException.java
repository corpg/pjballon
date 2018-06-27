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
	
	public SerialInitException()
	{
		super("Erreur d'initialisation du lien série");
	}
}