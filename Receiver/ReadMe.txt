A propos:
	Application DataReceiver pour récupérer les données du ports série et les réémettre via un socket IP.
	Réalisé en Java par Etienne Glossi dans le cadre du projet "Ballon" de l'IUT de Valence.

Contact pour l'application: 
	etienne.glossi@gmail.com

Liens:
	www.iut-valence.fr
	www.rom.buisson.free.fr/ballon/
	
Utilisation:
	Double-cliquez simplement le DataReceiver.jar.
	Nécessite Java 1.6 update 12 ou ultérieur: http://www.java.com/fr/
	Testé sous Windows XP, Windows Vista SP1.
	
Précisions sur les répertoires:
	- Le dossier "doc" contient la documentation javadoc de l'application. Vous trouverez aussi un diagramme de classe dans le répertoire "src".
	- Le doccier "lib" contient les packages nécessaires au bon fonctionnement de l'application.
	- Le dossier "ressource" contient l'icône par défaut de l'application, ainsi que son thème.
	- Le dossier "src" contient les sources, librement modifiables, de l'application.

Classes principales:
	L'application est centrée autour de la classe InterfaceGraphique.java qui représente la fenêtre principale.
	La classe Décodeur.java, quant à elle, réalise le décodage des données.
	Il peut aussi être utile de regarder les classes EcouteSerial.java, qui s'occupe de récupérer les données, et la classe InterfaceEcouteSerial.java, qui se charge d'émettre sur le socket IP les données décodées.
	
Bugs:
	1. Si le fichier de sauvegarde existe déjà, les données existantes sont effaçées. Les nouvelles données ne sont pas ajoutées à la suite.
	2. Problème lié au changement du socket de sortie via le menu de configuration (passage du mode Test à un socket). Rédémarrer l'application si c'est le cas.
	
	Si vous rencontrez d'autres bugs, ou imperfections, merci de le signaler.