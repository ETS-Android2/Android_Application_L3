# Release 06

#### Tag: [v6](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v6)
<br>

### Bilan de l'itération des fonctionnalités implémentées
De manière générale, nous prenons un léger retard sur les fonctionnalités développées.
Nous avons ajouté certaines contraintes de validation des UE dans un parcours : 
- un utilisateur ne peut pas sélectionner plus de 4 UE dans un semestre
- il ne peut pas non plus ajouter une UE d'une catégorie en 2ème année s'il n'a pas sélectionné une UE de la même catégorie l'année précédente.

Un menu latéral a été développé, il est accessible depuis la fenêtre listant l'ensemble des semestres.
L' "hamburger button" permettant de l'ouvrir n'a pas encore été développé, mais nous pouvons l'ouvrir en swipant vers la droite.  
Côté serveur, un service de recherche d'UE par nom est mis à disposition dans l'API, mais la barre de recherche sur l'application Android n'a pas encore été développée.


<br>

### Bilan de l'itération sur les tests
Nous avons préféré dans cette itération nous concentrés principalement sur les tests Android.<br>
Nous utilisons les Build Variants de Gradle pour changer d'environnement et passé dans un environnement de test.
Je vous invite à lire [cette documentation](Build%20Variant.md) pour savoir comment changer de Build Variant.<br>
Ainsi l'activité Login et l'activité SemesterList sont désormais testées. 
Cependant nous sommes toujours confrontés à des erreurs que nous n'arrivons pas à résoudre : tous nos tests passent individuellement,
ils passent aussi en lançant les tests sur chaque fichier individuellement, 
mais lorsque nous effectuons les tests de tout le package `activity` les tests de SemesterActivity échouent.
A l'heure actuelle nous savons pourquoi, mais nous ne savons pas encore comment résoudre ce problème.

<br>

### Prévision pour la prochaine itération
Pour la prochaine itération nous devrons rattraper notre retard en finissant le menu latéral ainsi que le "hamburger button".
Il faudra aussi rendre accessible ce menu depuis n'importe quelle activité. <br>
Nous devrons développer la barre de recherche permettant de rechercher une UE.
Enfin, nous sommes au courant d'un bug sur l'application Android : lorsque l'utilisateur coche une UE, puis qu'il déroule la liste d'UE d'une catégorie, la case cochée se décoche.
Il nous faudra donc résoudre ce problème.
