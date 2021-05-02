# SMTP-Prank
## Description
Ce projet est un laboratoire pour le cours de RES de l'HEIG-VD. Ce laboratoire à pour but de prank des utilisateur en leur envoyant des mails forgés.
## Instalation et implementation d'un serveur SMTP de test
L'installation d'un serveur SMTP en local permet de faire des tests sans envoyer les mails à d'autres personnes. Cela permet également de voir le contenu du mail généré.
Pour effectuer mes tests de fonctionnements j'ai utilisé MockMock. <br> Lien : https://github.com/tweakers/MockMock <br>
Cette application fournit une interface web supplémentaire qui permet de simuler une boîte de réception, accessible sur http://localhost:8282. Il est possible de choisir les ports que l'on souhaite utilisé pour l'interface web et pour le serveur SMTP. Par défaut le serveur STMP utilise le port 25, certain OS peuvent empécher le lancement du serveur sur ce port si l'application n'est pas executé par un utilisateur administateur.
## Configuration de l'outils de génération de prank
Pour utiliser cette application, il faut commencer par clone le repository. Ensuite il est possible de modifier les 3 fichiers suivants afin de renseigner aux programmes quelques informations nécessaire. Si les fichiers ne sont pas modifié l'application fonctionnera et enverra des messages sur des addresses non existantes et sur le serveur SMTP mock.
A la base du git se trouve le dossier "config". Il contient les trois différents fichiers de configuration du programme.
### Config.properties 
- SmtpServerAddress : Permet de configurer l'addresse du serveur SMTP, par défaut localhost est utilisé. 
- smtpServerPort : Permet de configurer le port d'écoute du serveur STMP, par défaut il utilise le port 25.
- numberOfGroupes : Permet de définir le nombre de groupe de personne que l'on veut crée, par défaut le programme sépare la liste d'utilisateur en 3.
- witnessToCc : permet d'ajouter une addresse en copie aux mails envoyés.
### Messages.utf8
Ce fichier permet de définir le contenu de différent message qui peuvent être envoyés par l'application. Les messages contiennt un sujet et son terminer par la suite de caractère suivante : ==
Le fichier contien actuellement des messages de tests, remplis par du Lorem ipsum
### victims.utf8
Ce fichier contient la liste de toutes les addresses mails qui seront utilisés par l'application. Les addresses sont séparés par un retour a la ligne. Il contient actuellement une liste de 19 addresses générées sur internet <br>
Une fois les configurations le plus simple et d'utiliser docker.
Construire l'image : <br>
``` ./docker/build.sh```<br>
Démarrer le container : <br>
``` ./docker/run.sh```<br>
Tout est en place, il reste plus qu'a exécuté le projet : <br>
``` java -jar /target/STMP-Prank-1.0-SNAPSHOT.jar ```<br>
## Implementation
![Diagramme de classe](figures/SMTP-prank.png)
L'application contient 5 packages différents. 
-config : permet de géré la partie de configuration des messages et des addresses mails fournie. Il est formé d'une classe et d'une interface, qui vont chercher les données dans le fichier de configuration et les stockés dans des listes ou des variables privée accessible par des méthodes "get".
- model : contient deux autres packages (mail et prank)
- mail : Fournis via 3 classes les méthodes permettant de "set" et "get" des attributs aux messages, aux persones et aux groupes. Ce package ne travaille pas les données il permet juste de représenter les 3 différentes entitées cités juste aux dessus.
- prank : Permet de généré le contenu d'un message ainsi que la personne qui l'envoie et ceux qui vont le recevoir.
- smtp : Permet de géré toute la connexion avec le serveur STMP. Il permet d'envoyer les emails aux victimes, un système de log permet d'affichger en console tout ce qui se passe lors des discussions avec le serveur SMTP. <br>

### Discussion avec le serveur SMTP
Client : connect<br>
Serveur : 220 OK<br>
Client : EHLO localhost<br>
Serveur : 250 OK<br>
Client : MAIL FROM<br>
Serveur : 250 OK<br>
Client : RCPT TO (Boucle si il y'a plusieurs receveur)<br>
Serveur : 250 OK<br>
Client : DATA<br>
Serveur : 354<br>
Client : Le contenu du mail (en-tête, sujet, corps)<br>
Client : .<br>
Serveur : 250 OK<br>
Client : QUIT<br>
Serveur : 221 Closing connection<br>



Le fichier main permet d'intialiser les différentes instances nécessaires pour l'exécution complète du programme. 
