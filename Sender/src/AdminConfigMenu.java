/**
	Pour faire bien et griser le menu "Configurer" quand l'application est en train d'envoyer des données.
	@author Etienne Glossi
*/

import javax.swing.JMenuItem;

public class AdminConfigMenu implements Runnable
{
	public AdminConfigMenu(JMenuItem configMenu, JMenuItem tt, UISendData sd)
	{
		this.configMenu = configMenu;
		this.tt = tt;
		this.sd = sd;
	}
	
	public void run()
	{
		while(true){
			if(sd.isBusy() || this.force){
				configMenu.setEnabled(false); //on grise !
				tt.setEnabled(false);
			}
			else {
				configMenu.setEnabled(true);
				tt.setEnabled(true);
			}
			try{Thread.sleep((int)(TEMPS_VERIFICATION * 1000));}
			catch(InterruptedException e){} //do nothing...
		}
	}
	
	public void forceDisabled()
	{
		this.force = true;
	}
	
	public void forceEnabled()
	{
		this.force = false;
	}
	
	private JMenuItem configMenu;
	private JMenuItem tt;
	private UISendData sd;
	private boolean force;
	public static final double TEMPS_VERIFICATION = 0.5; //vérification toutes les demies-secondes
}