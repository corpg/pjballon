/**
	L'application DataReceiver fournit une interface graphique permettant de g�rer la r�c�ption des donn�es, �mise par le ballon stratosph�rique ou par l'application DataSender. L'application reste en �coute sur un lien s�rie et retransmet les donn�es qu'elle recoit � un socket pour leur traitement (ou les donn�es sont trait�es par l'application en mode test). Les donn�es re�ues sont d�cod�es en utilisant le codage de Hamming[7, 4, 3] --> voir la classe D�codeur.
	
	R�alis� dans le cadre du projet Ballon men� par l'IUT de Valence.
	Programmation et algo: Etienne Glossi (RT2 promo 2009)
	Contact: etienne.glossi@gmail.com
	@version 1.0
	
	Main qui d�finit le th�me de l'interface graphique et l'�xecute.
	@author Etienne Glossi
*/

import javax.swing.UIManager;
import com.l2fprod.gui.plaf.skin.*;

public class Receiver
{
	public static void main(String[] args){
	
		try {
			// On indique le theme � utiliser
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