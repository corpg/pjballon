/**
	Exception générée lors d'un problème à l'initialisation du socket.
	@author Etienne Glossi
	
	==> créer des classes enfants pour distinguer plus facilement les différentes erreurs liées aux sockets
*/

public class SocketException extends java.io.IOException
{
	public SocketException(String mes, Throwable e)
	{
		super(mes, e);
		this.incorrect = false;
		this.ip = "";
		this.port = 0;
	}
	
	public SocketException()
	{
		this("Erreur d'initialisation du socket.");
	}
	
	public SocketException(String mes)
	{
		this(mes, false);
	}
	
	public SocketException(String mes, boolean inc)
	{
		super(mes);
		this.incorrect = inc;
		this.ip = ""; //indique que l'IP est bonne et que le port ne l'est pas.
		this.port = 0;
	}
	
	public boolean isIncorrect(){return this.incorrect;}
	
	public void setIp(String ip){this.ip=ip;}
	public void setPort(int port){this.port=port;}
	
	public String getIp(){return this.ip;}
	public int getPort(){return this.port;}
	
	private String ip;
	private int port;
	private boolean incorrect; //précise que le socket est incorrect (différent d'une erreur d'initialisation)
}