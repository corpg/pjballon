/**
	Classe qui remplie l'InterfaceGraphique avec les composants nécessaires à la récéption de données
	@author Etienne Glossi
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gnu.io.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

public class UIReceiveData extends JPanel
{
	public UIReceiveData(JFrame root)
	{
		this(root, null); //on spécifie le flux d'entrée comme étant inexistant
	}
	
	public UIReceiveData(JFrame root, InputStream is)
	{
		super(new BorderLayout());
		this.rootFrame = root;
		this.is = is;
		
		//initialisation...
		this.initRootFrame();
		this.initUI();
		super.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));		
		this.ies = null;
		this.t = null;
	}
	
	private void initRootFrame()
	{
		this.rootFrame.setSize(400, 445);
		this.rootFrame.setTitle("DataReceiver - Projet Ballon");
	}
	
	private void initUI()
	{		
		//Zone de réception des données
		JPanel envoie = new JPanel(new FlowLayout(FlowLayout.LEFT));
		reception = new JTextArea(17, 44);
		reception.setEditable(false);
		reception.setLineWrap(true); //retour automatique à la ligne
		reception.setWrapStyleWord(true); //on ne coupe pas les mots
		
		//creation d'une barre de défilement
		JScrollPane scroll = new JScrollPane(reception);
		scroll.setBounds(0,350,950,300);
		envoie.add(scroll);
		envoie.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Réception"));
	
		super.add(envoie, BorderLayout.CENTER);
	}
	
	public void startEcouteSerial(InputStream is, OutputStream os, StatThread st, FichierTexte ft)
	{
		if(this.t != null) 
		{
			this.ies.stopEcoute(); //arret de l'ecoute du port série
			this.t.interrupt(); //arret du thread d'interface
		}
		this.ies = new InterfaceEcouteSerial(is, os, this.reception, st, ft);
		this.t = new Thread(ies, "ThreadEcouteSerial");
		this.t.start();
	}
	
	public void startEcouteSerial(InputStream is, StatThread st, FichierTexte ft)
	{
		if(this.t != null)
		{
			this.ies.stopEcoute(); //arret de l'ecoute du port série
			this.t.interrupt();  //arret du thread d'interface
		}
		this.ies = new InterfaceEcouteSerial(is, this.reception, st, ft);
		this.t = new Thread(ies, "ThreadEcouteSerial");
		this.t.start();
	}
	
	public void closeInputStream()
	{
		ComSerial.closeAllOpenedSerialPort(rootFrame);
	}
	
	public void closeOutputStream()
	{
		ComSocket.closeAllOpenedSocket(rootFrame);
	}

	private JFrame rootFrame;
	private InputStream is;
	private JTextArea reception;
	private InterfaceEcouteSerial ies;
	private Thread t;
}