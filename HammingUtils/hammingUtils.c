/* Etienne Glossi - Maxime Chandellier - Simon Latrille-Débat 
/* Groupe du projet Ballon 
/* IUT de Valence - 26 Janvier 2009 
/* Bibliothèque de fonctions utilisées pour l'utilisation de la correction/détection d'erreur
/* en utilisant le codage hamming [7, 4, 3] 
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#include "hammingUtils.h"

char* coder(const char* donnee, int len){
	int i = 0; /* indice de parcours des données source */
	int j = 0; /* indice de parcours des données aprés encapsulation */
	char* toSend; /* tableau des données codées */
	
	toSend = (char*)malloc(toSend, ((len*2)+1)*sizeof(toSend)); /* taille des données multiplié par 2 */
	
	for(;i < len;i++){ /* pour chaque octet de données... */
		toSend[j++] = traiteQuartet((*(donnee + i)) >> 4, 1); /* ...on traite la quartet de poid fort... */
		toSend[j++] = traiteQuartet((*(donnee + i)), 0); /* ...et de poid faible */
	}
	
	toSend[j] = '\0'; /*ne pas oublier l'indicateur de fin de chaîne */
	return toSend;
}

char traiteQuartet(char quartet, int fort){
	/* bits de donnees */
	char bit1;
	char bit2;
	char bit3;
	char bit4;
	
	/* bits de parités */
	char bitp1;
	char bitp2;
	char bitp3;
	
	/* calcul des bits de donnees:  b1, b2, b3, b4 */
	bit4 = (char) (quartet & 0x01);
	bit3 = (char) ((quartet & 0x02) >> 1); /* le "et" binaire permet de ne garder que le bit que nous voulons. On le decale tout à droite pour obtenir un bit du type 0000 000x. */
	bit2 = (char) ((quartet & 0x04) >> 2);
	bit1 = (char) ((quartet & 0x08) >> 3);
	
	/* calcul des bits de parités paires */
	bitp1 = paritePaire(bit1, bit2, bit4);
	bitp2 = paritePaire(bit1, bit3, bit4);
	bitp3 = paritePaire(bit2, bit3, bit4);
	
	/* octet à envoyer: fort,bitp1,bitp2,bit1,bitp3,bit2,bit3,bit4*/
	return (char)((fort << 7) | (bitp1 << 6) | (bitp2 << 5) | (bit1  << 4) | (bitp3 << 3) | (bit2 << 2) | (bit3 << 1) | (bit4));
}

char paritePaire(char b1,char b2, char b3){
	if((b1+b2+b3)%2==0) return (char)0;
	else return (char)1;
}

