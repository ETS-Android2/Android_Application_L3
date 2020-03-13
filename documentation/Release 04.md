# Release 04

#### Tag: [v4](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/tree/v4)
<br>

### Bilan de l'itération des fonctionnalités implémentées
Désormais un utilisateur (non connecté pour l'instant) peut enregistrer un ensemble d'UE dans son parcours en les sélectionnant grâce 
aux checkbox correspondantes et en appuyant sur le bouton "Enregistrer mon parcours". 
Malheureusement la récupération de ce parcours depuis l'application génère quelques bugs, nous sommes donc contraints de reporter cette fonctionnalité 
pour l'itération suivante. <br>
L'utilisateur peut aussi accéder aux détails d'une UE en cliquant sur son nom depuis la page listant les UE par semestre.

<br>

### Bilan de l'itération sur les tests
Côté serveur, quelques tests sont encore manquants.<br> 
Côté client, de nouveaux tests ont été créés pour la page affichant la liste des semestres, mais nous avons dû les désactiver car 
ils sont dépendants du serveur (La liste des semestres est demandée au serveur alors qu'elle devrait être mocké) 

<br>

### Prévision pour la prochaine itération
Pour la prochaine itération, l'utilisateur devra pouvoir s'authentifier, le problème de récupération du parcours de l'étudiant devra être résolu, 
et nous devrons commencer à mettre en place les contraintes de sélection des UE (données par la première modification du sujet du projet qui a eu lieu cette semaine). 
