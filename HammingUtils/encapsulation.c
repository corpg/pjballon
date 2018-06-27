/* Etienne Glossi - Maxime Chandellier - Simon Latrille-Débat 
/* Groupe du projet Ballon RT2
/* IUT de Valence - 26 Janvier 2009 
/* Exemple d'utilisation de la bibliothèque hammingUtils.h
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include <string.h>
#include "hammingUtils.h"

int main (int argc, char* argv[]){
	char* data;
	
	if(argc==2){
		data = argv[1]; /*données à emettre*/
	}
	else {
		data = "bonjour";
	}
	
	printf("Avant codage: %s\n", data);
	printf("Aprés codage: %s", coder(data, strlen(data)));
	
	return 0;
}
