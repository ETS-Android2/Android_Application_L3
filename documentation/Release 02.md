# Release 02

Lors de cette itération le projet à subit beaucoup de grosses modifications.<br>
Côté serveur, nous l'avons entièrement remanié puisque nous avons migré vers le framework [Spring Boot](https://spring.io/projects/spring-boot).
Le serveur propose désormais une API REST et nous avons ajouté une base de données embarquée ([H2](https://www.h2database.com/html/main.html)).<br>
Côté Client, la connexion avec le serveur ne se fait plus en Socket mais en requêtes HTTP, 
et l'activité qui servait de chat textuel temporaire a laissé place à un affichage de liste de semestres et de catégories d'UE.

#### Tag: [v2](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v2)
<br>

### Bilan de l'itération des fonctionnalités implémentées
Les principales fonctionnalités de cette itération sont la récupération et l'affichage des listes des semestres et des catégories sur notre application Android, 
possible grâce à notre nouvelle API proposé par le serveur. 

<br>

### Bilan de l'itération sur les tests
Les contrôleurs et les services côté serveur sont testés, (des tests de cas d'erreurs sont sans doute manquants), 
mais nous n'avons toujours aucun test sur notre application Android.

<br>

### Prévision pour la prochaine itération
Pour la prochaine release, le serveur devra envoyer au client la liste des UE.
Le client devra les afficher dans leurs catégories respectives.
La liste des semestres et la liste des catégories d'UE seront sur deux activités distinctes. 
Une troisième activité servant de page d'accueil (permettant plus tard à l'utilisateur de se connecter) sera créée.
