A propos:
	Application DataReceiver pour r�cup�rer les donn�es du ports s�rie et les r��mettre via un socket IP.
	R�alis� en Java par Etienne Glossi dans le cadre du projet "Ballon" de l'IUT de Valence.

Contact pour l'application: 
	etienne.glossi@gmail.com

Liens:
	www.iut-valence.fr
	www.rom.buisson.free.fr/ballon/
	
Utilisation:
	Double-cliquez simplement le DataReceiver.jar.
	N�cessite Java 1.6 update 12 ou ult�rieur: http://www.java.com/fr/
	Test� sous Windows XP, Windows Vista SP1.
	
Pr�cisions sur les r�pertoires:
	- Le dossier "doc" contient la documentation javadoc de l'application. Vous trouverez aussi un diagramme de classe dans le r�pertoire "src".
	- Le doccier "lib" contient les packages n�cessaires au bon fonctionnement de l'application.
	- Le dossier "ressource" contient l'ic�ne par d�faut de l'application, ainsi que son th�me.
	- Le dossier "src" contient les sources, librement modifiables, de l'application.

Classes principales:
	L'application est centr�e autour de la classe InterfaceGraphique.java qui repr�sente la fen�tre principale.
	La classe D�codeur.java, quant � elle, r�alise le d�codage des donn�es.
	Il peut aussi �tre utile de regarder les classes EcouteSerial.java, qui s'occupe de r�cup�rer les donn�es, et la classe InterfaceEcouteSerial.java, qui se charge d'�mettre sur le socket IP les donn�es d�cod�es.
	
Bugs:
	1. Si le fichier de sauvegarde existe d�j�, les donn�es existantes sont effa��es. Les nouvelles donn�es ne sont pas ajout�es � la suite.
	2. Probl�me li� au changement du socket de sortie via le menu de configuration (passage du mode Test � un socket). R�d�marrer l'application si c'est le cas.
	
	Si vous rencontrez d'autres bugs, ou imperfections, merci de le signaler.