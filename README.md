# Projet de Programmation Java OOP, L3 Université Paris-Cité

Ce projet a pour but de créer un programme Java qui simule une communauté d'agglomération afin d'optimiser l'installation de bornes de recharges.

La communauté est représentée par un graphe non-orienté, où les nœuds sont des villes et les arêtes des routes. Le programme permettra de configurer manuellement les zones de recharge, tout en respectant les contraintes d'accessibilité et d'économie. Des solutions optimales seront recherchées pour minimiser le nombre de zones de recharge nécessaires.

## Structure

Le fichier principal se trouve dans : `/PAAProjet_FEKIHHASSEN_Yassine_KADIC_Anais/src/main/java/src/up/mi/yfh/main/Main.java`

Le fichier texte qui contient les informations sur les villes, les routes, etc. se trouve dans : `/PAAProjet_FEKIHHASSEN_Yassine_KADIC_Anais/src/main/java/src/up/mi/yfh/loader`

## Fonctionnalités

### Fonctionnalités correctement implémentées

Dans notre programme, nous pouvons correctement :

- Importer depuis un fichier texte une communauté d'agglomération, c'est-à-dire les ajouts de villes, de routes et de bornes de recharge.
- Trouver une solution manuellement et automatique.

  - Solution manuelle : on peut retirer nous-mêmes les bornes ou en ajouter.
  - Solution automatique : exécution de l'algorithme d'optimisation, soit le deuxième du sujet ou un autre que nous avons implémenté.

- Visualisation textuelle des informations concernant la communauté d'agglomération.

### Fonctionnalités implémentées mais ...

Nous avons essayé d'implémenter un algorithme d'approximation, meilleur que celui du sujet qui est basé sur de l'aléatoire.
Mais il existe sûrement un algorithme qui donne une solution optimale malgré que ce soit un problème NP-Complet (ressemble au Vertex Cover Problem)


### Exécution

Pour exécuter le programme, vous pouvez utiliser la commande suivante (une fois dans le dossier **target**) :
```bash 
 /Volumes/SSD/PAA/ProjetPAA_KADIC_Anais_FEKIH_HASSEN_Yassine/src/main/java/src/up/mi/akhy/loader/communaute.txt
 ```


## Auteurs

- [FEKIH HASSEN Yassine]
- [KADIC Anais]

