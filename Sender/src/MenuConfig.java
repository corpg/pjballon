/**
	Classe permettant de configurer un lien série en fournissant une interface graphique afin de séléctionner le port et sa vitesse de transmission.
	@author Etienne Glossi
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.text.NumberFormat;

import java.io.OutputStream;

public class MenuConfig extends JDialog
{
	public MenuConfig(JFrame root)
	{
		super(root, "Menu de configuration", true);
		super.setSize(305, 250);
		super.setResizable(false);
		
		this.rootFrame = root;
		
		lstSerial = "";
		lstFichTest = TestTransmission.TESTFILENAME;
		lstSpeed = 4800;
		
		//initialisation
		this.initMenu();
	}
	
	private void initMenu()
	{	
		JPanel main = new JPanel(new BorderLayout()); //main conteneur
		
		main.add(this.configSerial(), BorderLayout.NORTH); //ajout des éléments de configuration du lien série
		main.add(this.configAppli(), BorderLayout.CENTER);
		main.add(this.panelBouton(), BorderLayout.SOUTH); //ajout des boutons
		
		main.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); //création d'une bordure vide
		
		super.add(main); //ajout au conteneur principal
	}
	
	private JPanel configAppli()
	{
		//création du JPanel contenant la config de l'application
		JPanel configAppli = new JPanel(new BorderLayout());
		
		//JPanel pour le nom du fichier  texte de test
		JPanel testPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		testPane.add(new JLabel("Nom du fichier de test:              "));
		fichTest = new JTextField(lstFichTest, 10);
		testPane.add(fichTest);
		
		//ajout au JPanel principal
		configAppli.add(testPane, BorderLayout.NORTH);
		
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
				fichTest.setText(lstFichTest);
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
						os = ComSerial.getSerialOutputStream(getSerialName(), getSpeed(), rootFrame);
						lstSerial = getSerialName();
						lstSpeed = getSpeed();
					}
					lstFichTest = getTestFilename();
					hideMenu();
				}
				catch(SerialInitException e){  //si le port est indisponible
					JOptionPane.showMessageDialog(rootFrame,e.getMessage(),"Erreur", JOptionPane.ERROR_MESSAGE);
					showMenu();
				}
			}
		});
		conteneurBouton.add(continuer);
		super.getRootPane().setDefaultButton(continuer); //bouton par défaut lors de l'appui sur la touche Entrée
		
		return conteneurBouton;
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
	public OutputStream getOutputStream()
	{
		return os;
	}
	
	public String getSerialName()
	{
		return (String)serialBox.getSelectedItem();
	}
	
	public int getSpeed()
	{
		return (Integer)speedBox.getSelectedItem();
	}
	
	public String getTestFilename()
	{
		return fichTest.getText();
	}
	
	//attributs
	private OutputStream os = null; //le flux de sortie
	private JFrame rootFrame;
	private JComboBox serialBox, speedBox;
	private JTextField fichTest;
	
	//garde une trace des valeurs précédentes
	private String lstSerial, lstFichTest;
	private int lstSpeed;
}