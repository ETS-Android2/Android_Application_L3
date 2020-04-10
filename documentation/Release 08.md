# Release 08

#### Tag: [v8](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v8)
<br>

### Bilan de l'itération des fonctionnalités implémentées
Plusieurs fonctionnalités ont vu le jour lors de cette itération. <br>
Tout d'abord la barre de recherche est enfin fonctionnelle et elle est accessible à la fois depuis la liste des Semestres
et à la fois depuis la liste des UE.<br>
Un utilisateur peut désormais s'inscrire dans la page prévue à cet effet.<br>
Une page permettant de voir les UE sélectionnées dans son parcours (de manière assez grossière pour l'instant) est désormais accessible depuis la barre latérale.<br>
Les bugs concernant la récupération et l'affichage des UE sélectionnées ont été fixés, mais le bouton permettant d'ajouter ou de retirer une UE de son parcours qui se trouve 
dans la page de description n'effectue encore la modification qu'uniquement en local.  


<br>

### Bilan de l'itération sur les tests
En effectuant la mise à jour de Gradle vers la version 3.6.2, nos tests peuvent enfin passer en les exécutant tous ensemble.
En effet l'Orchestrator permettant cela ne fonctionnait pas suite à bug causé par les précédentes versions ([source](https://stackoverflow.com/questions/60721508/android-studio-3-6-test-framework-quit-unexpectedly-crash-of-orchestrator))<br>
Quelques tests Android ont été ajoutés, mais aucun test côté serveur n'a été créé.

<br>

### Prévision pour la prochaine itération
Pour la prochaine itération nous nous focaliserons surtout sur la gestion des parcours.
Il faudra permettre à un étudiant de créer plusieurs parcours, de les nommer, d'accéder à des parcours modèles et à des parcours publiés par d'autres étudiants.
