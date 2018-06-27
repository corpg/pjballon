A propos:
	Application DataSender pour réaliser l'envoie de données via le port Série.
	Réalisé en Java par Etienne Glossi dans le cadre du projet "Ballon" de l'IUT de Valence.

Contact pour l'application: 
	etienne.glossi@gmail.com

Liens:
	www.iut-valence.fr
	www.rom.buisson.free.fr/ballon/
	
Utilisation:
	Double-cliquez simplement le DataSender.jar.
	Nécessite Java 1.6 update 12 ou ultérieur: http://www.java.com/fr/
	Testé sous Windows XP, Windows Vista SP1.
	
Précisions sur les répertoires:
	- Le dossier "doc" contient la documentation javadoc de l'application. Vous trouverez aussi un diagramme de classe dans le répertoire "src".
	- Le doccier "lib" contient les packages nécessaires au bon fonctionnement de l'application.
	- Le dossier "ressource" contient l'icône par défaut de l'application, ainsi que son thème.
	- Le dossier "test" doit contenir le fichier utilisé lors du test de la transmission (voir MenuTestTransmission et TestTransmission)
	- Le dossier "src" contient les sources, librement modifiables, de l'application.

Classes principales:
	L'application est centrée autour de la classe InterfaceGraphique.java qui représente la fenêtre principale.
	La classe SendData.java, quant à elle, réalise le codage et l'envoie des données sur le port Série.
	
Bugs:
	Aucun connus. N'hésitez pas à les signaler.