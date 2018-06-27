A propos:
	Application DataSender pour r�aliser l'envoie de donn�es via le port S�rie.
	R�alis� en Java par Etienne Glossi dans le cadre du projet "Ballon" de l'IUT de Valence.

Contact pour l'application: 
	etienne.glossi@gmail.com

Liens:
	www.iut-valence.fr
	www.rom.buisson.free.fr/ballon/
	
Utilisation:
	Double-cliquez simplement le DataSender.jar.
	N�cessite Java 1.6 update 12 ou ult�rieur: http://www.java.com/fr/
	Test� sous Windows XP, Windows Vista SP1.
	
Pr�cisions sur les r�pertoires:
	- Le dossier "doc" contient la documentation javadoc de l'application. Vous trouverez aussi un diagramme de classe dans le r�pertoire "src".
	- Le doccier "lib" contient les packages n�cessaires au bon fonctionnement de l'application.
	- Le dossier "ressource" contient l'ic�ne par d�faut de l'application, ainsi que son th�me.
	- Le dossier "test" doit contenir le fichier utilis� lors du test de la transmission (voir MenuTestTransmission et TestTransmission)
	- Le dossier "src" contient les sources, librement modifiables, de l'application.

Classes principales:
	L'application est centr�e autour de la classe InterfaceGraphique.java qui repr�sente la fen�tre principale.
	La classe SendData.java, quant � elle, r�alise le codage et l'envoie des donn�es sur le port S�rie.
	
Bugs:
	Aucun connus. N'h�sitez pas � les signaler.