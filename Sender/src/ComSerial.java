/** 
	Fournit des méthodes statiques pour gérer les ports série et créer des liens série.
	@author Etienne Glossi
	@version 1.0
*/
	
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.CommPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import java.io.OutputStream;
import java.util.Vector;
import java.util.Enumeration;

public class ComSerial
{
	/** Retroune l'OutputStream du port série dont le nom est fourni en paramètre.
		@param id Nom du port série.
		@param vitesse Vitesse de la transmission.
		@param owner L'objet responsable de l'ouverture du lien série.
		@return Le flux de sortie du lien série ouvert.
	*/
	public static OutputStream getSerialOutputStream(String id, int vitesse, Object owner) throws SerialInitException
	{
		SerialPort serial = null;
		try {
			serial = (SerialPort) CommPortIdentifier.getPortIdentifier(id).open("BallonStratospherique", 2000);
			serial.setSerialPortParams(vitesse, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serial.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			ComSerial.openedCommPort.add(new Object[]{owner, serial}); //ajoute a la liste des ports ouverts
			return serial.getOutputStream();
		}
		catch(Exception e){
			throw new SerialInitException("ComSerial: Erreur lors de l'initialisation du port série "+id+".", e.getCause());
		}
	}
	
	/** Méthode qui liste les ports série diponibles.
		@return Un tableau de String contenant le nom des ports série disponibles.
	*/	
	public static String[] getSerialPortList()
    {
		Vector<String> serieDispo = new Vector<String>(); //stocke les noms des ports série trouvés
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = (CommPortIdentifier) portEnum.nextElement();
			if(portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL && !ComSerial.isUsed(portIdentifier)) serieDispo.add(portIdentifier.getName());
		}
		return serieDispo.toArray(new String[serieDispo.size()]);
	}
	
	/** Test si le port serie est utilisé.
		@param cpi L'identifiant du port série. Voir CommPortIdentifier.getPortIdentifier(String).
		@return Un booléen indiquant sur le port série est déjà utilisé.
	*/
	public static boolean isUsed(CommPortIdentifier cpi)
	{
		CommPort cp = null;
		try {cp = cpi.open("BallonStratospherique", 2000);}
		catch(PortInUseException e){return true;} //port série deja utilisé
		cp.close();
		return false;
	}
	
	/** Test si le port série est utilisé.
		@param com Le nom du port série.
		@return Un booléen indiquant sur le lien série est déjà utilisé.
	*/
	public static boolean isUsed(String com)
	{
		try {
			return ComSerial.isUsed(CommPortIdentifier.getPortIdentifier(com));
		}
		catch(NoSuchPortException e){ //port inexistant !!!
			return true;
		}
	}
	
	/** Methode qui ferme tout les ports série ouvert par owner avec la méthode getSerialOutputStream().
		@param owner L'objet responsable de l'ouverture du lien série.
	*/
	public static void closeAllOpenedSerialPort(Object owner)
	{
		for(Object[] comPort: ComSerial.openedCommPort)
		{
			if(comPort[0] == owner){
				SerialPort sp = (SerialPort) comPort[1];
				sp.close();
			}
		}
	}
	
	/** Methode qui ferme le port série de nom id ouvert par owner avec la méthode getSerialOutputStream().
		@param owner L'objet responsable de l'ouverture.
		@param id Le nom du port série.
	*/
	public static void closeOpenedSerialPort(Object owner, String id)
	{
		SerialPort sp = null;
		
		for(Object[] comPort: ComSerial.openedCommPort)
		{
			sp = (SerialPort) comPort[1];
			//on recherche le port serie
			if(comPort[0]==owner && id.equals(sp.getName()))
			{
				sp.close();
				break;
			}
		}
	}	
	
	/** Contient la liste de tous les ports série ouverts. */
	private static Vector<Object[]> openedCommPort = new Vector<Object[]>(1);
}