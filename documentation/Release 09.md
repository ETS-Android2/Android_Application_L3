# Release 09

#### Tag: [v9](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v9)
<br>

### Bilan de l'itération des fonctionnalités implémentées
Les principales modifications apportées lors de cette itération ont été développées sur le serveur et leurs impacts ne sont 
pas encore visibles dans l'application Android.
Parmi celles-ci se trouvent :
- La modification de la table Career (qui contenait **le** parcours d'un étudiant) : cette table n'est désormais plus une table de jointure
mais une entité. Ainsi un parcours contient maintenant un nom et un étudiant peut posséder plusieurs parcours.<br> 
Cependant, le serveur ne propose encore aucun service permettant de créer de nouveaux parcours.
- La base de données n'est désormais plus stockée en mémoire. Elle est par défaut supprimée à chaque exécution d'un `mvn clean`.<br>
Une explication sur la modification du mode de persistance de la base de données sera bientôt ajoutée à la documentation.
- Le serveur propose désormais un service pour exporter un parcours aux formats texte ou PDF.<br>
L'application ne possédant pas encore la fonctionnalité permettant de télécharger ces fichiers sur le téléphone, 
ce service peut être testé en attendant grâce à l'outil [Swagger](../README.md#swagger) sur la route `/career/{careerId}` dans CareerController.

Sur l'application Android, certains bugs graphiques ont été corrigé. 
Une "solution" temporaire à été trouvée pour le bouton enregistrer masquant la dernière checkbox dans l'activité listant les UE : 
ce bouton peut être déplacé vers la gauche ou la droite en le faisant glisser.

<br>

### Bilan de l'itération sur les tests
Côté Android, de nombreux tests sur la page d'inscription ont été créés.
Côté serveur, certains tests ont été temporairement mis en commentaires suite aux modifications apportées par la réarchitecture 
des parcours dans la base de données, car nous n'avons pas eu le temps de les mettre à jour. 

<br>

### Prévision pour la prochaine itération
Pour le rendu final il faudra permettre la gestion des parcours depuis l'application Android.<br>
Optionnellement il faudra aussi que l'étudiant puisse accéder aux parcours publiés par d'autres étudiants.<br>
Enfin, nous permettrons à l'utilisateur de télécharger sur son téléphone l'un de ses parcours dans un fichier texte ou PDF.
