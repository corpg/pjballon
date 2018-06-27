/**
	Interface graphique de l'application d'émission de données du projet Ballon.
	Permet d'envoyer un fichier (peu importe l'extension), de réaliser un test de la trasnmission (par l'envoie d'un fichier de test) et de configurer un lien série.
	@author Etienne Glossi
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceGraphique
{	
	public InterfaceGraphique()
	{		
		this.root = new JFrame();
		this.paramFrame();
		
		//creation des boites de dialogues et des fenêtres
		this.st = new StatThread();
		this.mc = new MenuConfig(root);
		this.sd = new UISendData(root, st);
		this.jfc = new JFileChooser();
		this.mtt = new MenuTestTransmission(root, st);
		
		//initialisation du menu
		this.initMenu();
		
		this.st.setJLabels(waitByte, sendByte);
		
		//on lance le thread de stat
		new Thread(st).start();
	}
	
	public void run()
	{
		//lancement de la fenetre
		mc.showMenu(); //on lance la boite de dialogue de configuration au lancement
		if(mc.getOutputStream()==null) exit(); //on force l'utilisateur à spécifier un flux de sortie
		updateUI();
		this.root.setVisible(true);
	}
	
	// Configure la fenêtre principale.
	private void paramFrame()
	{
		this.root.setIconImage(Toolkit.getDefaultToolkit().getImage("ressources/icone.gif")); //L'icône du programme dans la barre des taches de windows
		this.root.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		this.root.setResizable(false); //On interdit la redimensionnement de la fenêtre
		this.root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix rouge
	}
	
	// Initialise le menu
	private void initMenu()
	{
		JMenuBar menu = new JMenuBar();
		this.root.setJMenuBar(menu);
		
		/* Menu fichier */
		JMenu fichier = new JMenu("Fichier");
		
		//envoyer un fichier
		JMenuItem euf = new JMenuItem("Envoyer un fichier...");
		euf.addActionListener(new SendFileAction());
		fichier.add(euf);
		
		//test
		JMenuItem eft = new JMenuItem("Test de la transmission");
		eft.addActionListener(new TestAction());
		fichier.add(eft);
		
		fichier.addSeparator();
		
		//quitter
		JMenuItem quit = new JMenuItem("Quitter");
		quit.addActionListener(new QuitAction());
		fichier.add(quit);
		
		menu.add(fichier);
		
		/* Menu option */
		JMenu option = new JMenu("Option");
		
		//changement de langue -- non encore implémenté
		JMenuItem langue = new JMenuItem("Langue");
		option.add(langue);
		langue.setEnabled(false);
		
		//configuration de la liaison série
		JMenuItem config = new JMenuItem("Configurer");
		config.addActionListener(new ConfigSerialAction());
		option.add(config);
		
		menu.add(option);
		
		/* Menu d'aide */
		JMenu help = new JMenu("Aide");
		
		//Menu d'aide -- non encore implémenté
		JMenuItem aide = new JMenuItem("Menu d'aide");
		help.add(aide);
		aide.setEnabled(false);
		
		help.addSeparator();
		
		//A propos
		JMenuItem apropos = new JMenuItem("A propos...");
		apropos.addActionListener(new AboutAction());
		help.add(apropos);
		
		menu.add(help);
		
		//Zone d'information
		JPanel information = new JPanel(new GridLayout(2, 4, 0, 3));
		information.add(new JLabel("Port série: ", SwingConstants.LEFT));
		serialName = new JLabel("Unknow");
		serialName.setHorizontalAlignment(SwingConstants.LEFT);
		information.add(serialName);
		information.add(new JLabel("Octet envoyés"));
		sendByte = new JLabel("0", SwingConstants.RIGHT);
		information.add(sendByte);
		information.add(new JLabel("Vitesse: ", SwingConstants.LEFT));
		speed = new JLabel("0");
		speed.setHorizontalAlignment(SwingConstants.LEFT);
		information.add(speed);
		information.add(new JLabel("Octets en attente"));
		waitByte = new JLabel("~0", SwingConstants.RIGHT);
		information.add(waitByte);
		
		information.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informations"),
					BorderFactory.createEmptyBorder(10,10,10,10)));
					
		//IG
		JPanel ig = new JPanel(new BorderLayout());
		ig.add(sd, BorderLayout.CENTER);
		ig.add(information, BorderLayout.NORTH);
		
		this.root.setContentPane(ig);
		
		//on lance le thread d'admin du menuConfig
		new Thread(new AdminConfigMenu(config, eft, sd), "AdminConfigMenu").start(); //grise le menu "configurer" et "test de la transmission" quand le programme est en train d'envoyer des données
	}
	
	//méthode appelée lorsque l'application est quittée
	public void exit()
	{
		sd.closeOutputStream(); //on ferme proprement la liaison série :)
		System.exit(0);
	}
	
	// met à jour l'interface graphique et le flux de sortie
	private void updateUI()
	{
		sd.setOutputStream(mc.getOutputStream());
		this.serialName.setText(mc.getSerialName());
		this.speed.setText(String.valueOf(mc.getSpeed()));
	}
	
	//Classes privées utilisées par le menu (actionListener)
	private class ConfigSerialAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mc.showMenu(root);
			updateUI();
		}
	}
	
	private class QuitAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			exit();
		}
	};
	
	private class SendFileAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int returnVal = jfc.showOpenDialog(root);
			if(returnVal == JFileChooser.APPROVE_OPTION) sd.send(jfc.getSelectedFile());
		}
	};
	
	private class AboutAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(root, ApplicationBallonConstants.ABOUT_TEXT_FR, "A propos...", JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private class TestAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mtt.showMenu(root, mc.getOutputStream(), mc.getTestFilename());
		}
	};
	
	//attributs
	private UISendData sd;
	private JFrame root;
	private MenuConfig mc;
	private JFileChooser jfc;
	private MenuTestTransmission mtt;
	private StatThread st;
	private JLabel serialName, speed, sendByte, waitByte;
}
