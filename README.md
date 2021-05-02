# SMTP-Prank
## Description
Ce projet envois des mails forgés a des groupes de personne. Le programme crée les groupes a partir d'une liste d'addresse mail fournie par l'utilisateur. Le contenu du message est également choisi par l'utilisateur.
## Instalation et implementation d'un serveur SMTP de test
L'installation d'un serveur SMTP en local permet de faire des tests sans envoyer les mails à d'autres personnes. Cela permet également de voir le contenu du mail généré.
Pour effectuer mes tests de fonctionnements j'ai utilisé MockMock. <br> Lien : https://github.com/tweakers/MockMock <br>
Cette application fournit une interface web supplémentaire qui permet de simuler une boîte de réception. Il est possible de choisir les ports que l'on souhaite utilisé pour l'interface web et pour le serveur SMTP. Par défaut le serveur STMP utilise le port 25, certain OS peuvent empécher le lancement du serveur sur ce port si l'application n'est pas executé par un utilisateur administateur.
## Configuration de l'outils de génération de prank
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
Ce fichier contient la liste de toutes les addresses mails qui seront utilisés par l'application. Les addresses sont séparés par un retour a la ligne. Il contient actuellement une liste de 19 addresses générées sur internet
## Implementation
L'application contient 5 packages différents. 
-config : permet de géré la partie de configuration des messages et des addresses mails fournie. Il est formé d'une classe et d'une interface, qui vont chercher les données dans le fichier de configuration et les stockés dans des listes ou des variables privée accessible par des méthodes "get".
- model : contient deux autres packages (mail et prank)
- mail : Fournis via 3 classes les méthodes permettant de "set" et "get" des attributs aux messages, aux persones et aux groupes. Ce package ne travaille pas les données il permet juste de représenter les 3 différentes entitées cités juste aux dessus.
- prank : Permet de généré le contenu d'un message ainsi que la personne qui l'envoie et ceux qui vont le recevoir.
- smtp : Permet de géré toute la connexion avec le serveur STMP. Il permet d'envoyer les emails aux victimes, un système de log permet d'affichger en console tout ce qui se passe lors des discussions avec le serveur SMTP. <br>

Le fichier main permet d'intialiser les différentes instances nécessaires pour l'exécution complète du programme. 
