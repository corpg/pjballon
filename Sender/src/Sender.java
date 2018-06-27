/**
	L'application DataSender fournit une interface graphique permettant l'envoie de donn�es via un port s�rie. Les donn�es sont cod�es en utilisant le codage de Hamming[7, 4, 3] --> voir la classe SendData.
	
	R�alis� dans le cadre du projet Ballon men� par l'IUT de Valence.
	Programmation et algo: Etienne Glossi (RT2 promo 2009)
	Contact: etienne.glossi@gmail.com
	@version 1.0
	
	Main qui d�finit le th�me de l'interface graphique et l'�x�cute.
	@author Etienne Glossi
*/

import javax.swing.UIManager;
import com.l2fprod.gui.plaf.skin.*;

public class Sender
{
	public static void main(String[] args){
	
		try {
			// On indique le th�me � utiliser
			Skin theSkinToUse = SkinLookAndFeel.loadThemePack("ressources/theme.zip");
			SkinLookAndFeel.setSkin(theSkinToUse);

			// on l'applique
			UIManager.setLookAndFeel(new SkinLookAndFeel());
		}
		catch (Exception e){ 
			System.out.println("Le theme n'a pu etre charge. Utilisation du theme par defaut...");
		}
		
		//Lancement de l'application
		new InterfaceGraphique().run();
	}
}