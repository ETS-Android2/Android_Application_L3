# Release 07

#### Tag: [v7](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v7)
<br>

### Bilan de l'itération des fonctionnalités implémentées
L'application a subi une refonte graphique dans la majorité de ses activités.

De plus, la barre latérale a été mise à jour :
 - Il existe désormais un "hamburger boutton" pour l'ouvrir et la fermer.
 - Le bouton "Se déconnecter" déconnecte l'utilisateur et le redirige vers la page de connexion.
 - Les autres boutons seront implémentés lors de la prochaine itération : quand sera créée la page affichant le parcours de l'étudiant.
 
Néanmoins, cette barre latérale n'est toujours accessible que depuis l'activité listant les semestres.

Nous avons commencé le développement de la barre de recherche, mais elle n'est pas encore accessible.
Nous avons aussi ajouté une page d'inscription qui n'est pas encore fonctionnelle, elle sera finie comme prévu pour l'itération prochaine.

Enfin, les erreurs d'affichage des UE sélectionnées ont été résolu.  


<br>

### Bilan de l'itération sur les tests
Pour cette itération aucun test n'a été effectué. <br>
Nous rencontrons toujours des problèmes dans les tests Android car nous n'arrivons pas à Mocker la récupération du token,
ce qui aurait dû permettre la validation de tous les tests groupés.

<br>

### Prévision pour la prochaine itération
Pour la prochaine itération nous devrons permettre à l'utilisateur de voir un aperçu de toutes ses UE sélectionnées, ce 
qui implique l'implémentation des boutons de la barre latérale pour y accéder et revenir en arrière. <br>
Nous devrons aussi terminer la barre de recherche et la rendre accessible, ainsi que la barre latérale, accessible dans les autres activités de l'application. <br>
Enfin, il faudra terminer la page d'inscription pour que l'étudiant puisse correctement s'inscrire et accéder à notre application.
