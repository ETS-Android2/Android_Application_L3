# Release 01

Pour cette première itération, nous devions prendre en main les technologies que nous allons utiliser (Android, Socket.IO, ...). Nous avons donc pensé à faire un chat textuel pour nous entrainer à la communication Client-Serveur.

#### Tag: [v1](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v1)
<br>

### Bilan de l'itération des fonctionnalités implémentées
La principale fonctionnalité implémentée est une messagerie instantanée : Un client se connecte et envoie un message au serveur qui sera redistribué à tous les clients connectés. Il s'agit d'une fonctionnalité **temporaire** qui sera retirée pour la prochaine release.

D'un point de vue technique, cette fonctionnalité nous a permis de voir la fonction des classes Adapter qui nous seront utiles pour les itérations suivantes.

<br>

### Bilan de l'itération sur les tests
Pour cette release aucun test n'a été effectué.

<br>

### Prévision pour la prochaine itération
Pour la prochaine release, le serveur devra envoyer au client la liste des catégories d'UE qu'il aura récupérée depuis une base de données.
Le client devra les afficher pour chacun des semestres.