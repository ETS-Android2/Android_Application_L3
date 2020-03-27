# Parcours Univ
<br>

## Sommaire

- [Installation](#installation)
	* [Serveur](#serveur)
	* [Android](#android)
- [Utilisation](#utilisation)
- [Outils](#outils)
	* [Swagger](#swagger)
	* [H2-Console](#h2-console)
<br>

## Installation

### Serveur
Vous pouvez lancer le serveur sans lancer IntelliJ : 
- en exécutant le fichier [install.sh](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/blob/master/Server/install.sh) pour installer toutes les dépendances. <br> Vous n'avez besoin de le faire qu'une seul fois entre chaque pull.
- en lançant ensuite le fichier [start.sh](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plplg/blob/master/Server/start.sh). <br>Une fois que la console affiche le message `: Started PlplgApplication ...` le serveur est correctement lancé.

### Android
Pour accéder à l'application le lancement d'Android Studio est obligatoire.

**Attention :** Il existe deux Build Variants pour l'application Android, merci de [vérifier](documentation/Build%20Variant.md) que vous êtes bien dans la bonne.

**Attention :** Les tests exécutés un par un et exécutés par classe passent. En revanche l’exécution de l'ensemble des tests d'un package échoue.

<br>

## Utilisation
Pour se connecter il faut saisir les identifiants suivants :
* Email : `toto@gmail.com`
* Mot de passe : `12345`

<br>

## Outils

### Swagger
#### Qu'est ce que c'est ?
Swagger est un framework [open-source](https://github.com/swagger-api) qui permet grâce à leur ensemble de modules de concevoir, de construire, de documenter et de tester les Web Services REST d'un serveur. Dans notre serveur nous avons installer le module swagger-ui qui permet, grâce à une interface web, de visualiser et de tester l'ensemble des services mis à disposition par notre API.
<br>

#### Comment y accéder ?
Pour y accéder il suffit d'aller sur ce [lien](http://localhost:8080/swagger-ui.html) lorsque votre serveur est lancé.
<br>

#### Comment l'utiliser ?
Puisque pour accéder aux services de notre API il faut être authentifié, il faut d'abord se login.
* Vous trouverez dans l'Authentication Controller la requête POST permettant de se connecter. <br>
<p align="center"><img src="https://user-images.githubusercontent.com/29798789/77718974-7edf2000-6fe4-11ea-950c-a28c06c923c3.png" alt="1 - Le Web Service Login" width="500px"></p>

* Vous pouvez apercevoir un exemple des paramètres à envoyer dans la partie **Parameters**, et l'ensemble des status HTTP que vous pouvez recevoir dans la partie **Responses** (**Attention :** La partie response est actuellement configurée par défaut, il se peut donc que certains exemples soient faux)

* Cliquez sur "Try it out".

* Modifiez le corps de la requête en rentrant les identifiants comme tel : <br>
<p align="center"><img src="https://user-images.githubusercontent.com/29798789/77719102-bea60780-6fe4-11ea-8856-ff6bc7555901.png" alt="2 - Saisie des identifiants"></p>

* Cliquez sur "Execute". La réponse du serveur a dû s'afficher dessous.

* Copiez la valeur du token qui se trouve dans le corps de la réponse du serveur. <br>
<p align="center"><img src="https://user-images.githubusercontent.com/29798789/77719180-f44af080-6fe4-11ea-83ba-8e87b93e255b.png" alt="3 - Récupération du token"></p>

* Cliquez sur le bouton Authorize se trouvant au dessus de la liste des Controllers.

* Dans la pop-up qui a dû s'ouvrir écrivez dans le champ value le mot "Bearer" suivit d'un espace et de la valeur de votre token : <br>
<p align="center"><img src="https://user-images.githubusercontent.com/29798789/77719206-06c52a00-6fe5-11ea-85d2-10841e39d2e8.png" alt="4 - Sauvegarde du token" width="500px"></p>

* Cliquez sur Authorize et fermez la pop-up. Swagger-UI a enregistré votre token et ajoutera automatiquement à chacune de vos requêtes l'en-tête `Authorization` contenant votre token.

Voilà vous êtes authentifié et vous savez vous servir de Swagger-UI. Vous pouvez désormais tester n'importe lequel des Web Services proposés par notre serveur.
<br>

### H2-Console
#### Qu'est ce que c'est ?
Nous utilisons [H2](https://www.h2database.com/html/main.html) qui est une base de données embarquée dans notre serveur. Ce qui veut dire que notre base de donnée est créée dynamiquement au démarrage du serveur et elle est supprimée lorsque le serveur est arrêté. Il est donc impossible d'effectuer des requêtes lorsque le serveur est arrêté. <br> J'expliquerais lors d'une itération future comment rendre nos données persistantes et ainsi gardé nos données entre chaque redémarrage.
<br>

#### Comment y accéder ?
Nous avons laissé accessible la console web de H2. Vous pouvez y accéder lorsque le serveur est lancé sur ce [lien](http://localhost:8080/h2-console).
Les informations de connections sont les suivantes :
* Driver Class : org.h2.Driver
* JDBC URL : jdbc:h2:mem:plplg
* User Name : user
* Password : pass
<br>

#### Comment l'utiliser ?
Vous pouvez voir à gauche la liste des tables de notre base de données et vous pouvez exécuter une requête SQL au centre de la page.
<br>
