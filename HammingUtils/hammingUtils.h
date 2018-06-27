/* Etienne Glossi - Maxime Chandellier - Simon Latrille-D�bat
/* Groupe du projet Ballon
/* IUT de Valence - 26 Janvier 2009 
/* Fichier d'en-tete utilis� pour d�finir les prototypes des fonctions de hamminUtils.c 
/* pour la correction/d�t�ction d'erreur en utilisant le codage hamming [7, 4, 3] .
/* 
/* Le programme utilise le type de base caract�re (char) pour manipuler les octets et les bits.
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#ifndef BROWSER_H
#define BROWSER_H

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* Nom: coder												    */
char* coder(const char* donnee, int len);
/* Fonction: Code les donn�es pass� en parametre (ajoute la correction d'erreur hamming) 
/* Post et precondition: Aucune
/* Parametres: un pointeur vers une chaine de caract�res, la taille de la chaine
/* Valeur rendue: un pointeur vers la cha�ne de caract�res encapsul�es
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* Nom: traiteQuartet												    */
char traiteQuartet(char quartet, int fort);
/* Fonction: Permet de traiter chaque quartet afin de calculer les parit�s et construire le nouvel octet
/* Post et precondition: Aucune
/* Parametres: un quartet (octet du type 0000 xxxx) et un entier indiquant le poid du quartet (0:faible, 1:fort) 
/* Valeur rendue: un octet (quartet + bit de poid + correction d'erreur) 
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* Nom: paritePaire													    */
char paritePaire(char b1, char b2, char b3);
/* Fonction: Calcul de la parite sur 3 bits.
/* Post et precondition: Aucune
/* Parametres: 3 bits (octet du type 0000 000x)
/* Valeur rendue: 1 bit indiquant la parit�(octet du type 0000 000x)
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#endif
