/**
	Interface graphique de l'application de réception de données du projet Ballon.
	Permet de recevoir les données par un lien série configurable, les décoder, et les retransmettre sur un socket. Si en mode test, traitement des données par l'application.
	@author Etienne Glossi
	
	==> Implémenter le changement de langue et un menu d'aide
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
		this.mc = new MenuConfig(root);
		this.rd = new UIReceiveData(root);
		
		//initialisation du menu
		this.initMenu();
	}
	
	public void run()
	{
		//lancement de la fenetre
		mc.showMenu(); //on lance la boite de dialogue de configuration au lancement
		if(mc.getInputStream()==null) exit(); //on force l'utilisateur à spécifier un flux de sortie
		
		//on met à jour les informations
		setIG();
		
		//démarrage du thread d'écoute
		this.updateUI();		
		this.root.setVisible(true);
	}
	
	// Configure la fenêtre principale.
	private void paramFrame()
	{
		this.root.setIconImage(Toolkit.getDefaultToolkit().getImage("ressources/icone.gif")); //L'icone du programme dans la barre des taches de windows
		this.root.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		this.root.setResizable(false); //On interdit la redimensionnement de la fenêtre
		this.root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix rouge
	}
	
	private void setIG()
	{
		//JPanel principal
		JPanel ig = new JPanel(new BorderLayout());
		ig.add(informationPanel(), BorderLayout.NORTH);
		ig.add(rd, BorderLayout.CENTER);
		this.root.setContentPane(ig);
	}
	
	// Initialise le menu
	private void initMenu()
	{
		JMenuBar menu = new JMenuBar();
		this.root.setJMenuBar(menu);
		
		/* Menu fichier */
		JMenu fichier = new JMenu("Fichier");		
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
		
		//configuration de la liaison série et du socket de sortie
		JMenuItem config = new JMenuItem("Configurer");
		config.addActionListener(new ConfigAction());
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
	}
	
	private JPanel informationPanel()
	{
		//Zone d'information
		
		//infos du port série
		JPanel information = new JPanel(new GridLayout(2, 4, 0, 3));
		information.add(new JLabel("Port série: ", SwingConstants.LEFT));
		serialName = new JLabel();
		serialName.setHorizontalAlignment(SwingConstants.LEFT);
		information.add(serialName);
		
		//infos de l'IP du serveur
		information.add(new JLabel("Serveur: "));
		socket = new JLabel();
		socket.setHorizontalAlignment(SwingConstants.LEFT);
		information.add(socket);
		
		//infos vitesse transmission
		information.add(new JLabel("Vitesse: ", SwingConstants.LEFT));
		speed = new JLabel();
		speed.setHorizontalAlignment(SwingConstants.LEFT);
		information.add(speed);
		
		//infos données eronnés
		information.add(new JLabel("T.E.B. : ", SwingConstants.LEFT));
		JLabel jl_octetRecu = new JLabel("0%");
		jl_octetRecu.setHorizontalAlignment(SwingConstants.LEFT);
		information.add(jl_octetRecu);
		
		information.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informations"),
					BorderFactory.createEmptyBorder(10,10,10,10)));
					
		//Demarage du thread de stat
		st = new StatThread(jl_octetRecu);
		new Thread(st, "ThreadDeStats").start();
					
		return information;
	}
	
	//méthode appelé lorsque l'application est quittée
	private void exit()
	{
		rd.closeOutputStream(); //on ferme le socket
		rd.closeInputStream(); //on ferme proprement la liaison série :)
		System.exit(0);
	}
	
	//méthode appelée lorsque l'utilisateur à mis à jour la configuration de l'application
	private void updateUI()
	{
		if(mc.isTest())
		{
			this.rd.startEcouteSerial(mc.getInputStream(), this.st, mc.getBackupFile());
			this.serialName.setText(mc.getSerialName());
			this.speed.setText(String.valueOf(mc.getSpeed()));
			this.socket.setText("Mode \"Test\"");
		}
		else
		{
			this.rd.startEcouteSerial(mc.getInputStream(), mc.getOutputStream(), this.st, mc.getBackupFile());
			this.serialName.setText(mc.getSerialName());
			this.speed.setText(String.valueOf(mc.getSpeed()));
			this.socket.setText(mc.getIp()+":"+mc.getPort());
		}
	}
	
	//Classes privées utilisées par le menu (actionListener)
	private class ConfigAction implements ActionListener
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
	
	private class AboutAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(root, ApplicationBallonConstants.ABOUT_TEXT_FR, "A propos...", JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	//attributs
	private UIReceiveData rd;
	private JFrame root;
	private MenuConfig mc;
	private JLabel serialName, speed, socket;
	private StatThread st;
}
