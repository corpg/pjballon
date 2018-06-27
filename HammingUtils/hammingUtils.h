/* Etienne Glossi - Maxime Chandellier - Simon Latrille-Débat
/* Groupe du projet Ballon
/* IUT de Valence - 26 Janvier 2009 
/* Fichier d'en-tete utilisé pour définir les prototypes des fonctions de hamminUtils.c 
/* pour la correction/détéction d'erreur en utilisant le codage hamming [7, 4, 3] .
/* 
/* Le programme utilise le type de base caractère (char) pour manipuler les octets et les bits.
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#ifndef BROWSER_H
#define BROWSER_H

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* Nom: coder												    */
char* coder(const char* donnee, int len);
/* Fonction: Code les données passé en parametre (ajoute la correction d'erreur hamming) 
/* Post et precondition: Aucune
/* Parametres: un pointeur vers une chaine de caractères, la taille de la chaine
/* Valeur rendue: un pointeur vers la chaîne de caractères encapsulées
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/* Nom: traiteQuartet												    */
char traiteQuartet(char quartet, int fort);
/* Fonction: Permet de traiter chaque quartet afin de calculer les parités et construire le nouvel octet
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
/* Valeur rendue: 1 bit indiquant la parité(octet du type 0000 000x)
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

#endif
