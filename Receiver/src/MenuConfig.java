/**
	Classe permettant de configurer un lien série en fournissant une interface graphique afin de séléctionner le port et sa vitesse de transmission.
	Permet également la configuration du socket TCP/UDP de sortie.
	@author Etienne Glossi
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;

public class MenuConfig extends JDialog
{
	public MenuConfig(JFrame root)
	{
		super(root, "Menu de configuration", true);
		super.setSize(305, 400);
		super.setResizable(false);
		
		this.rootFrame = root;
		
		//création du nom de fichier de sauvegarde
		this.DEFAULT_FILENAME = new String("Backup_" + System.currentTimeMillis() + ".txt");
		
		//initialisation
		this.initMenu();
	}
	
	private void initMenu()
	{	
		JPanel cont1 = new JPanel(new BorderLayout()); //conteneur pour les boutons et la config de l'application
		cont1.add(this.panelBouton(), BorderLayout.SOUTH);
		cont1.add(this.configAppli(), BorderLayout.NORTH);
		
		JPanel main = new JPanel(new BorderLayout()); //main conteneur
		main.add(this.configSerial(), BorderLayout.NORTH); //ajout des éléments de configuration du lien série
		main.add(this.configSocket(), BorderLayout.CENTER); //ajout des éléments de configuration du socket de comm
		main.add(cont1, BorderLayout.SOUTH); //ajout des boutons
		main.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); //creation d'une bordure vide
		super.add(main); //ajout au conteneur principal
	}
	
	private JPanel configAppli()
	{
		//creation du JPanel contenant la config de l'application
		JPanel configAppli = new JPanel(new BorderLayout());
		
		//JPanel pour le nom du fichier  texte de test
		JPanel testPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		testPane.add(new JLabel("Nom du fichier de test:              "));
		fichTest = new JTextField("fichierDeTest.txt", 10);
		fichTest.setEnabled(false);
		testPane.add(fichTest);
		
		//JPanel pour le fichier de sauvegarde
		JPanel sauvPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sauvPane.add(new JLabel("Nom du fichier de sauvegarde: "));
		fichSauv = new JTextField(DEFAULT_FILENAME, 10);
		sauvPane.add(fichSauv);
		
		//ajout au JPanel principal
		configAppli.add(testPane, BorderLayout.NORTH);
		configAppli.add(sauvPane, BorderLayout.CENTER);
		
		//création d'une bordure
		configAppli.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fichiers"),
					BorderFactory.createEmptyBorder(0,10,10,10)));
		
		return configAppli;
	}
	
	private JPanel panelBouton()
	{
		//creation d'un JPanel contenant les boutons
		JPanel conteneurBouton = new JPanel();
		
		//bouton annuler		
		conteneurBouton.add(new JButton(new AbstractAction("Annuler")
		{
			public void actionPerformed(ActionEvent e) {
				//on rétablis les valeurs
				speedBox.setSelectedItem(lstSpeed);
				serialBox.setSelectedItem(lstSerial);
				ip.setText(lstIp);
				port.setText(String.valueOf(lstPort));
				fichTest.setText(lstFichTest);
				fichSauv.setText(lstFichSauv);
				testBox.setSelected(lstTest);
				test=lstTest;
				hideMenu();
			}
		}));

		//bouton continuer
		JButton continuer = new JButton(new AbstractAction("Continuer")
		{
			public void actionPerformed(ActionEvent a)
			{
				try { //ouverture des flux d'entrées/sorties
					if(!(lstSerial.equals(getSerialName()) && lstSpeed==getSpeed()))
					{ //changement !!
						ComSerial.closeAllOpenedSerialPort(rootFrame);
						is = ComSerial.getSerialInputStream(getSerialName(), getSpeed(), rootFrame);
					}
					if(lstTest!=test && test)
					{
						ComSocket.closeAllOpenedSocket(rootFrame);
						os = null;
					}
					else if(!(lstIp.equals(getIp()) && lstPort==getPort() && lstTest==test) && !test)
					{ //changement !!
						ComSocket.closeAllOpenedSocket(rootFrame);
						os = ComSocket.getSocketOutputStream(getIp(), getPort(), rootFrame);
					}
					if(!(lstFichSauv.equals(fichSauv.getText())))
					{
						backupFile = new FichierTexte(fichSauv.getText());
					}
					//conservation des paramètres
					lstIp = getIp();
					lstPort = getPort();
					lstSerial = getSerialName();
					lstSpeed = getSpeed();
					lstFichSauv = fichSauv.getText();
					lstFichTest = getTestFilename();
					lstTest=test;
					hideMenu();
				}
				catch(SerialInitException e){  //si le port est indisponible
					JOptionPane.showMessageDialog(rootFrame,e.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
					showMenu();
				}
				catch(SocketException e){ //serveur injoignable ou IP invalide
					JOptionPane.showMessageDialog(rootFrame,e.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
					ComSerial.closeAllOpenedSerialPort(rootFrame); //sinon ne peut réouvrir le même port...
					lstSerial = "";
					if(e.isIncorrect())
					{
						if(e.getIp().equals("")) { //problème sur le port
							port.setText("3113");
							port.requestFocus();
						}
						else { //problème sur l'ip
							ip.setText("127.0.0.1");
							ip.requestFocus();
						}
					}
					is = null;
					showMenu();
				}
				catch(FileNotFoundException e){
					JOptionPane.showMessageDialog(null,"Erreur : fichier de sauvegarde non trouvé","ERREUR", JOptionPane.ERROR_MESSAGE);
					fichSauv.setText(DEFAULT_FILENAME);
					showMenu();
				}
			}
		});
		conteneurBouton.add(continuer);
		super.getRootPane().setDefaultButton(continuer); //bouton par défaut lors de l'appui sur la touche Entrée
		
		return conteneurBouton;
	}
	
	private JPanel configSocket()
	{
		//JPanel principal
		JPanel configSocket = new JPanel(new BorderLayout());
		
		//Creation d'un JPanel pour la séléction du port
		JPanel portPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		portPane.add(new JLabel("Port du serveur:                                        "));
		port = new JTextField("3113", 4);
		portPane.add(port);
		
		//Creation d'un JPanel pour la séléction de l'IP
		JPanel ipPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ipPane.add(new JLabel("IP du serveur:                         "));
		ip = new JTextField("127.0.0.1", 11);
		ipPane.add(ip);
		
		//Creation d'un bouton pour le mode "Test"
		testBox = new JCheckBox("  Mode 'Test'");
		testBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(testBox.isSelected())
				{
					ip.setEnabled(false);
					port.setEnabled(false);
					test = true;
				}
				else
				{
					ip.setEnabled(true);
					port.setEnabled(true);
					test = false;
				}
			}
		});
		
		//ajout au JPanel principal
		configSocket.add(portPane, BorderLayout.CENTER);
		configSocket.add(ipPane, BorderLayout.NORTH);
		configSocket.add(testBox, BorderLayout.SOUTH);
		
		//creation d'une bordure
		configSocket.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Serveur TCP"),
					BorderFactory.createEmptyBorder(0,10,10,10)));
		
		return configSocket;		
	}
	
	private JPanel configSerial()
	{
		//JPanel principal
		JPanel configSerial = new JPanel(new BorderLayout());
		
		//creation d'un JPanel pour la selection du port
		JPanel serie = new JPanel(new FlowLayout(FlowLayout.LEFT));
		serie.add(new JLabel("Sélectionnez un port série:            "));
		serialBox = new JComboBox();
		serialBox.setEditable(false);
		for(String s : ComSerial.getSerialPortList()) serialBox.addItem(s);
		serie.add(serialBox);
		
		//creation d'un JPanel pour la vitesse
		JPanel vitesse = new JPanel(new FlowLayout(FlowLayout.LEFT));
		vitesse.add(new JLabel("Vitesse de la transmission:            "));
		speedBox = new JComboBox();
		speedBox.setEditable(false);
		for(int v : new int[]{300, 600, 1200, 2400, 4800}) speedBox.addItem(v);	
		speedBox.setSelectedItem(4800);
		vitesse.add(speedBox);
		
		//ajout au JPanel principal
		configSerial.add(serie, BorderLayout.NORTH);
		configSerial.add(vitesse, BorderLayout.CENTER);
		
		//création d'une bordure
		configSerial.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Lien série"),
					BorderFactory.createEmptyBorder(0,10,10,10)));
		
		return configSerial;
	}
	
	public void showMenu(Component c)
	{
		super.setLocationRelativeTo(c);
		super.setVisible(true); //methode bloquante jusqu'à ce que l'utilisateur renvoie la boite de dialogue
	}
	
	public void showMenu()
	{
		this.showMenu(null);
	}
	
	public void hideMenu()
	{
		super.setVisible(false);
	}
	
	//methodes qui permettent d'obtenir la configuration	
	public InputStream getInputStream()
	{	
		return this.is;
	}
	
	public OutputStream getOutputStream()
	{
		return this.os;
	}
	
	public String getSerialName()
	{
		return (String)serialBox.getSelectedItem();
	}
	
	public int getSpeed()
	{
		return (Integer)speedBox.getSelectedItem();
	}
	
	public String getIp()
	{
		return ip.getText();
	}
	
	public int getPort()
	{
		try {return Integer.parseInt(port.getText());}
		catch(NumberFormatException e){return -1;}
	}
	
	public String getTestFilename()
	{
		return fichTest.getText();
	}
	
	public FichierTexte getBackupFile()
	{
		return backupFile;
	}
	
	public boolean isTest()
	{
		return this.test;
	}
	
	//attributs
	private InputStream is = null; //le flux d'entrée du lien série configuré.
	private OutputStream os = null; //le flux de sortie
	private JFrame rootFrame;
	private JComboBox serialBox, speedBox;
	private JTextField ip, port, fichTest, fichSauv;
	private JCheckBox testBox;
	private boolean test = false;
	private FichierTexte backupFile;
	private final String DEFAULT_FILENAME;
	
	//rétablir les valeurs quand on clique sur 'annuler'
	private String lstSerial = "";
	private int lstSpeed = 0;
	private String lstIp = "";
	private int lstPort = 0;
	private boolean lstTest = false;
	private String lstFichTest = "";
	private String lstFichSauv = "";
}