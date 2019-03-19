# Serveur Java

Le serveur Java fourni dans ce dossier correspond à la solution du dernier TP de programmation répartie. Il permet notamment de :
- fournir une api REST/JSON sur l'url `http://localhost:8080/api/v1/xxxxx` :
	- http://localhost:8080/api/v1/pizzas permet de manipuler la liste des pizzas
	- http://localhost:8080/api/v1/ingredients permet de manipuler la liste des ingrédients
- servir les fichiers statiques de l'application sur http://localhost:8080/ : lorsque l'utilisateur charge cette URL, le serveur retourne le fichier `index.html` contenu dans le dossier `server/src/main/resources/static`.

	***NB:*** le fichier `index.html` a besoin du fichier `main.bundle.js` pour fonctionner. Ce fichier est généré par la compilation du projet JS contenu dans le dossier `client-js` : voir [README](../client-js/README.md)

## Lancement du serveur
```bash
cd /chemin/vers/dossier/server/
mvn compile
mvn exec:java
```