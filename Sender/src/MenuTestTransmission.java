/**
	Classe qui initialise une fen�tre de dialogue pour tester la transmission.
	@author Etienne Glossi
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;

public class MenuTestTransmission extends JDialog
{
	public MenuTestTransmission(JFrame root, StatThread st)
	{
		super(root, "Test de la Transmission", true);
		super.setSize(300, 340);
		this.rootFrame = root;
		this.os = null;
		this.st = st;
		this.testFilename = TestTransmission.TESTFILENAME;
		this.initMenu();
	}
	
	private void initMenu()
	{
		//cr�ation du panel principal
		JPanel main = new JPanel(new BorderLayout());
		
		//ajout d'un texte d'information
		JPanel textPane = new JPanel();
		JLabel text = new JLabel("<html>Le test de la transmission permet de<br />v�rifier que l'envoi et la r�c�ption<br />des donn�es se font correctement.<br />La proc�dure de test envoie des<br />informations utiles ainsi que le contenu<br />du fichier "+TestTransmission.TESTFILENAME+"(par d�faut) pr�sent dans le <br />r�pertoire test. Le fichier doit <br />aussi �tre pr�sent dans le r�pertoire<br />test de l'application DataRceiver.<br />Apr�s avoir activ� le mode test du cot�<br />r�cepteur, cliquez sur \"D�marrer\"<br />pour commencer le test. Les r�sultats<br />sont disponible du cot� r�cepteur uniquement !", SwingConstants.TRAILING);
		text.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "A propos du test..."),
					BorderFactory.createEmptyBorder(5,10,10,10)));
		textPane.add(text);
		main.add(textPane, BorderLayout.CENTER);
		
		//test area
		JPanel testarea = new JPanel(new BorderLayout());
		
		//ajout de la barre de progression
		JPanel progressBarPane = new JPanel();
		pb = new JProgressBar(0, 100);
		progressBarPane.add(pb);
		pb.setStringPainted(true);
		testarea.add(progressBarPane, BorderLayout.NORTH);
		
		//ajout d'un bouton pour d�marrer le test
		JPanel boutonPane = new JPanel();
		bouton = new JButton();
		boutonPane.add(bouton);
		
		testarea.add(boutonPane, BorderLayout.SOUTH);
		
		main.add(testarea, BorderLayout.SOUTH);
		
		//initialisation des composants
		initUITest();
		
		//ajout � la boite de dialogue
		super.add(main);
	}
	
	private void initUITest()
	{
		bouton.setAction(new AbstractAction("D�marrer")
		{
			public void actionPerformed(ActionEvent e)
			{
				startTest();
			}
		});
		bouton.setEnabled(true);
		
		pb.setValue(0);
		pb.setString("En attente de d�marrage...");
	}
	
	private void startTest()
	{
		super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //on n'annule pas le test
		super.setVisible(true);
		
		Runnable tt = null;
		
		bouton.setText("En cours...");
		bouton.setEnabled(false);
		
		try {
			tt = new TestTransmission(this, os, testFilename, st);
			threadTT = new Thread(tt, "TestTransmission");
			threadTT.start();
		}
		catch(TransmissionTestException e)
		{
			JOptionPane.showMessageDialog(rootFrame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
			initUITest();
			hideMenu();
		}
	}
	
	public void showFinishBouton()
	{
		bouton.setAction(new AbstractAction("Terminer")
		{
			public void actionPerformed(ActionEvent e){hideMenu();initUITest();}
		});
		bouton.setEnabled(true);
	}
	
	public void setPbProgressValue(int value)
	{
		pb.setValue(value);
	}
	
	public void updatePbProgressValue(int value)
	{
		pb.setValue(pb.getValue()+value);
	}
	
	public void setPbString(String text)
	{
		pb.setString(text);
	}
	
	public void showMenu(Component c, OutputStream os, String filename)
	{
		this.os = os;
		this.testFilename = filename;
		super.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		super.setLocationRelativeTo(c);
		super.setVisible(true);
		
	}
	
	public void hideMenu()
	{
		super.setVisible(false);
	}
	
	private JFrame rootFrame;
	private JProgressBar pb;
	private OutputStream os;
	private JButton bouton;
	private Thread threadTT;
	private String testFilename;
	private StatThread st;
}