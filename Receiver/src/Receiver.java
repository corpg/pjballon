/**
	L'application DataReceiver fournit une interface graphique permettant de gérer la récéption des données, émise par le ballon stratosphérique ou par l'application DataSender. L'application reste en écoute sur un lien série et retransmet les données qu'elle recoit à un socket pour leur traitement (ou les données sont traitées par l'application en mode test). Les données reçues sont décodées en utilisant le codage de Hamming[7, 4, 3] --> voir la classe Décodeur.
	
	Réalisé dans le cadre du projet Ballon mené par l'IUT de Valence.
	Programmation et algo: Etienne Glossi (RT2 promo 2009)
	Contact: etienne.glossi@gmail.com
	@version 1.0
	
	Main qui définit le thème de l'interface graphique et l'éxecute.
	@author Etienne Glossi
*/

import javax.swing.UIManager;
import com.l2fprod.gui.plaf.skin.*;

public class Receiver
{
	public static void main(String[] args){
	
		try {
			// On indique le theme à utiliser
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