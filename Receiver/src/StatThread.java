/**
	Classe qui permet de mettre à jour les informations sur les données reçues.
	@author Etienne Glossi
*/

import javax.swing.JLabel;

public class StatThread implements Runnable
{
	//constructeurs
	public StatThread()
	{
		this(null);
	}
	
	public StatThread(JLabel jl_receiveByte)
	{
		this.jl_receiveByte = jl_receiveByte;
		this.receiveByte = 0;
		this.nbErrorByte = 0;
	}
	
	//méthode run
	public void run()
	{
		float teb;
		//calcul du taux d'erreur bit et affichage
		while(true)
		{
			if(this.receiveByte!=0) teb = ((float)this.nbErrorByte/(float)this.receiveByte) * 100;
			else teb = 0;
			
			if(jl_receiveByte!=null) jl_receiveByte.setText(String.valueOf(teb) + "%");
			try{Thread.sleep(ITERATIONTIME);}catch(InterruptedException e){}
		}
	}
	
	public void setJLabels(JLabel jl_receiveByte)
	{
		this.jl_receiveByte = jl_receiveByte;
	}
	
	//attributs
	private JLabel jl_receiveByte;
	public int receiveByte;
	public int nbErrorByte;
	public static final int ITERATIONTIME = 500; //en millisecondes
}