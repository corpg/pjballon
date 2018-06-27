/* Etienne Glossi - Maxime Chandellier - Simon Latrille-D�bat 
/* Groupe du projet Ballon RT2
/* IUT de Valence - 26 Janvier 2009 
/* Exemple d'utilisation de la biblioth�que hammingUtils.h
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include <string.h>
#include "hammingUtils.h"

int main (int argc, char* argv[]){
	char* data;
	
	if(argc==2){
		data = argv[1]; /*donn�es � emettre*/
	}
	else {
		data = "bonjour";
	}
	
	printf("Avant codage: %s\n", data);
	printf("Apr�s codage: %s", coder(data, strlen(data)));
	
	return 0;
}
