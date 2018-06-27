import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class FichierTexte
{
	PrintStream ft = null;
	
	public FichierTexte(String nomFichier) throws FileNotFoundException
	{
		try
		{
			ft = new PrintStream(new FileOutputStream(nomFichier), true, "cp1252");
		} 
		catch (java.io.UnsupportedEncodingException e)
		{
			ft = new PrintStream(new FileOutputStream(nomFichier));
		}
		enteteFichierTexte();
	}

	void ouvertureFichierTexte(String nomFichier)
	{
		try
		{
			ft = new PrintStream(new FileOutputStream(nomFichier), true, "cp1252");
		} 
		catch (java.io.UnsupportedEncodingException e)
		{
			try {ft = new PrintStream(new FileOutputStream(nomFichier));}
			catch (FileNotFoundException e1)
			{
				JOptionPane.showMessageDialog(null,
					"Erreur : fichier de stockage non trouvé",
					"ERREUR SYSTEME", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		catch (FileNotFoundException e1)
		{
			JOptionPane.showMessageDialog(null,
					"Erreur : fichier de stockage non trouvé",
					"ERREUR SYSTEME", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	void enteteFichierTexte()
	{
		try
		{
			Date dateDuFichier = new Date();
			ft.println("Fichier de sauvegarde créé le : " + dateDuFichier);
		} catch (Exception e2)
		{
			JOptionPane.showMessageDialog(null,
					"Erreur : Impossible d'écrire l'entête", "ERREUR SYSTEME",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	void stockageFichierTexte(String ligne_recue)
	{
		try
		{
			ft.printf(ligne_recue);
		} catch (Exception e3)
		{
			JOptionPane.showMessageDialog(null,
					"Erreur : impossible d'écrire dans le fichier",
					"ERREUR SYSTEME", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
