/**
	Classe qui permet de mettre � jour les informations sur les donn�es envoy�es.
	@author Etienne Glossi
*/

import javax.swing.JLabel;

public class StatThread implements Runnable
{
	//constructeurs
	public StatThread()
	{
		this(null, null);
	}
	
	public StatThread(JLabel jl_sendByte, JLabel jl_waitByte)
	{
		this.jl_waitByte = jl_waitByte;
		this.jl_sendByte = jl_sendByte;
		this.sendByte = 0;
		this.waitByte = 0;
	}
	
	//m�thode run
	public void run()
	{	
		//recup�re et affiche les octets envoy�s et ceux en attente
		while(true)
		{
			jl_sendByte.setText(String.valueOf(sendByte));
			jl_waitByte.setText("~ " + String.valueOf(waitByte));
			try{Thread.sleep(ITERATIONTIME);}catch(InterruptedException e){}
		}
	}
	
	public void setJLabels(JLabel jl_waitByte, JLabel jl_sendByte)
	{
		this.jl_sendByte = jl_sendByte;
		this.jl_waitByte = jl_waitByte;
	}
	
	//attributs
	private JLabel jl_sendByte, jl_waitByte;
	public int sendByte, waitByte;
	public static final int ITERATIONTIME = 100; //en millisecondes
}