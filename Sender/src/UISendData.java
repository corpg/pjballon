/**
	Classe qui remplit l'InterfaceGraphique avec les composants nécessaires à l'envoie de données
	@author Etienne Glossi
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gnu.io.*;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UISendData extends JPanel
{
	public UISendData(JFrame root, StatThread st)
	{
		this(root, null, st); //on spécifie le flux de sortie comme étant inexistant
	}
	
	public UISendData(JFrame root, OutputStream os, StatThread st){
		super(new BorderLayout());
		this.rootFrame = root;
		this.os = os;
		this.threadEncodeurs = Executors.newSingleThreadExecutor();
		this.st = st;
		
		//initialisation...
		this.initRootFrame();
		this.initUI();
		super.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
	}
	
	private void initRootFrame()
	{
		this.rootFrame.setSize(400, 250);
		this.rootFrame.setTitle("DataSender - Projet Ballon");
	}
	
	private void initUI()
	{		
		//Zone d'envoie des données
		JPanel envoie = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.dataToSend = new JTextField(20); //creation d'une zone de texte
		this.dataToSend.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		envoie.add(new JLabel("Saisissez le texte à envoyer :"));
		envoie.add(this.dataToSend); //ajout de la zone de texte
		envoie.add(new JButton(new AbstractAction("Envoyer")  //ajout d'un bouton "Envoyer"
		{
			public void actionPerformed(ActionEvent e){send();}
		}));
		envoie.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Saisie directe"),
				BorderFactory.createEmptyBorder(5,10,0,10)));
				
		super.add(envoie, BorderLayout.CENTER);
	}
	
	public void setOutputStream(OutputStream os)
	{
		this.os = os;
	}
	
	public void closeOutputStream()
	{
		ComSerial.closeAllOpenedSerialPort(rootFrame);
	}
	
	public String getText()
	{
		return this.dataToSend.getText();
	}
	
	public void resetField()
	{
		this.dataToSend.setText("");
	}
	
	
	//methode pour envoyer les données
	public void send()
	{
		singleThreadState = threadEncodeurs.submit(new SendData(getText(), os, st));
		resetField();
	}

	public void send(File fichier)
	{
		//ouverture du fichier
		FileInputStream fin = null;
		
		try {fin = new FileInputStream(fichier);}
		catch(IOException e){JOptionPane.showMessageDialog(this.rootFrame,"Impossible d'ouvrir le fichier "+fichier.getName(),"Erreur", JOptionPane.ERROR_MESSAGE); return;}
		
		//envoie au thread qui se charge d'envoyer les données
		singleThreadState = threadEncodeurs.submit(new SendData(fin, (int)fichier.length(), os, st));
	}
	
	public boolean isBusy()
	{
		if(singleThreadState==null) return false;
		else return !singleThreadState.isDone();
	}

	private JFrame rootFrame;
	private JTextField dataToSend = null; //zone de texte remplit pas l'utilisateur et qui représente les données à envoyer
	private OutputStream os;
	private ExecutorService threadEncodeurs;
	private Future singleThreadState = null; //pour vérifier l'état du thread d'envoie
	private StatThread st;
}