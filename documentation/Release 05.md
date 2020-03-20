# Release 05

#### Tag: [v5](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v5)
<br>

### Bilan de l'itération des fonctionnalités implémentées
L'utilisateur doit à présent se connecter pour pouvoir accéder au reste de l'application.
Il n'existe qu'un seul utilisateur actuellement (email : `toto@gmail.com`, mot de passe : `12345`) mais nous prévoyons d'en créer 
plusieurs afin de vérifier qu'aucun conflit ne peut survenir lors de l'enregistrement des parcours. <br>
Le problème rencontré lors de l'itération précédente concernant la récupération et l'affichage du parcours enregistré par l'étudiant
a été corrigé. Les matières sont correctement cochées lorsque celles-ci font partie du parcours de l'étudiant et ce même après le redémarrage de
l'application. <br>
Nous n'avons, cependant, toujours pas commencé la gestion des contraintes entre les différentes UE demandé par la première modification de projet, 
car la mise en place de l'authentification côté serveur nous a demandé plus de temps que prévu.  

<br>

### Bilan de l'itération sur les tests
Côté serveur, les tests ont été mis à jour pour passer l'authentification. 
La route `/login` permettant à l'utilisateur de se connecter est aussi testée. <br>
Côté client, le retard s'intensifie. Nous sommes actuellement face à un problème, 
l'architecture actuelle de notre application nous empêche d'effectuer des tests sans avoir à simuler une connexion avec le serveur. 
Il nous faudra donc revoir la conception de certaines classes très rapidement pour la prochaine itération.

<br>

### Prévision pour la prochaine itération
Pour la prochaine itération il est prévu de mettre en place un menu latéral qui permettra à l'utilisateur d'accéder à une page résumant
la liste de toutes les UE qu'il aura choisis, mais aussi qui lui permettra de se déconnecter de l'application.
Mais en priorité nous devrons travailler sur les contraintes entre les UE et sur les tests Android.
