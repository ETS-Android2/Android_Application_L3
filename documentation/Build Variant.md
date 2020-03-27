# Build Variants

### Qu'est ce que c'est ?
Gradle nous permet de générer des versions différentes de notre application en déclarant plusieurs types de Build différents. Pour donner un exemple, on pourrait vouloir autoriser le débogage dans un environnement de développement mais le désactiver dans un environnement de production. A l'inverse on pourrait vouloir [minifier](https://fr.wikipedia.org/wiki/Minification) son code en production mais pas en développement. <br>
Ces informations-là sont déclarées dans le build.gradle de notre application.

<br>

### Pourquoi on en a besoin ?
Dans notre cas nous avons besoin d'enregistrer une adresse serveur différente en fonction de si nous lançons notre application normalement ou si nous lançons les tests. En effet l'application (dans l'émulateur) doit se connecter au serveur à l'adresse http://10.0.2.2:8080/, tandis que dans les tests la communication avec le serveur est mocké, or nous ne pouvons pas mocké le hostname 10.0.2.2. Il nous faut donc une adresse différente pour ces deux utilisations.

Nous avons donc un type de build `debug` pour l’exécution normale de notre application, et un build `staging` pour effectuer nos tests

<br>

### Comment changer de Build Variant ?
- Dans Android Studio, allez dans l'onglet `Build` et cliquez sur `Select Build Variant...`<br>
![1 - build](https://user-images.githubusercontent.com/29798789/77727724-3717c300-6ffb-11ea-8c0e-931b9874df4a.png)


- Dans la barre latérale qui s'affiche sélectionnez l'`Active Build Variant` désirée : 
	- **debug** pour le lancement normal de l'application, 
	- **staging** pour lancer l'environnement de test.<br>
![2- select](https://user-images.githubusercontent.com/29798789/77727746-43038500-6ffb-11ea-8ed1-12d45e96a3ad.png)


Dans le doute je vous invite à faire un Build -> Clean Project, suivi d'un Build -> Rebuild Project, ça ne coûte rien.
