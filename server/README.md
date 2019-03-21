# Serveur Java

Le serveur Java fourni dans ce dossier correspond à la solution du dernier TP de programmation répartie. Il permet notamment de :
- fournir une api REST/JSON sur l'url `http://localhost:8080/api/v1/xxxxx` :
	- http://localhost:8080/api/v1/pizzas permet de manipuler la liste des pizzas
	- http://localhost:8080/api/v1/ingredients permet de manipuler la liste des ingrédients
- servir les fichiers statiques de l'application sur http://localhost:8080/ : lorsque l'utilisateur charge cette URL, le serveur retourne le fichier `index.html` contenu dans le dossier `server/src/main/resources/static`.

	***NB:*** le fichier `index.html` a besoin du fichier `main.bundle.js` pour fonctionner. Ce fichier est généré par la compilation du projet JS contenu dans le dossier `client-js` : voir [README](../client-js/README.md)

##  Développement sous eclipse

File 'import ' -> maven >> Existing maven projects
Parcourir jusque le répertoire 'server'
sélectionner le projet /pom.xml fr.ulille.iut.squelette...

## Développement sous IntelliJ

  ...TODO...
  
## Lancement du serveur
```bash
cd /chemin/vers/dossier/server/
mvn compile # compilation
mvn exec:java # execution (version main)
mvn package # création d'un jar exécutable
mvn test # évaluation des tests (version test)
```

Les ressources utilisées sont spécialisées (main ou test):
* persistence.xml (h2/fichier pour main, h2/memoire pour test)
* log4j et logback (définition des logs)

## Structure du projet:

```
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── fr
│   │   │       └── ulille
│   │   │           └── iut
│   │   │               └── tout1art              
│   │   │                   ├── ApiV1.java                         Application web
│   │   │                   ├── CORSFilter.java              
│   │   │                   ├── dao
│   │   │                   │   ├── DataAccess.java                Facade BDD
│   │   │                   │   ├── IngredientEntity.java       |
│   │   │                   │   ├──  .....                      |  Entités java
│   │   │                   │   └── PizzaEntity.java            |
│   │   │                   ├── dto
│   │   │                   │   ├── IngredientDto.java          |
│   │   │                   │   ├── IngredientPayloadDto.java   |  
│   │   │                   │   ├── PizzaCreateDto.java         |   .. Dto(s)
│   │   │                   │   ├── PizzaDto.java               |
│   │   │                   │   └── PizzaShortDto.java          |
│   │   │                   ├── Main.java                          Server complet
│   │   │                   └── ressources
│   │   │                       ├── IngredientRessource.java    |
│   │   │                       ├── MyResource.java             |  Ressources
│   │   │                       ├── PizzaRessource.java         |
│   │   │                       └── UncaughtException.java
│   │   └── resources
│   │       ├── log4j.properties     | 
│   │       ├── log4j.xml            | Définition des logs
│   │       ├── logback.xml          |
│   │       ├── META-INF
│   │       │   ├── persistence.xml  | Propriétés de la couche ORM (JPA) (h2, Postgres, ...)
│   │       │   └── sql
│   │       │       └── populate.sql | script d'intialisation de la base
│   │       └── static               | Fichiers statiques
│   │           ├── build
│   │           │   ├── main.bundle.js        | Résultat de compilation js
│   │           │   └── main.bundle.js.map    | 
│   │           ├── css
│   │           │   ├── flatly-bootstrap.css
│   │           │   ├── fonts
│   │           │   │   └── glyphicons-halflings-regular.woff2
│   │           │   └── main.css
│   │           ├── images
│   │           │   ├── bg.png
│   │           │   ├── carbonara.jpg
│   │           │   ├── hawaienne.jpg
│   │           │   ├── hot.svg
│   │           │   ├── logo.svg
│   │           │   ├── napolitaine.jpg
│   │           │   ├── oranaise.jpg
│   │           │   ├── regina.jpg
│   │           │   ├── savoyarde.jpg
│   │           │   └── spicy.jpg
│   │           └── index.html
│   └── test
│       ├── http         | illustration des accés à l'API (documentation)
│       │   ├── ingredients
│       │   │   ├── delete_id_404.http
│       │   │   ├── ....
│       │   │   └── put.http
│       │   ├── pizzas
│       │   │   ├── delete_id_404.http
│       │   │   ├── ....
│       │   │   └── put.http
│       │   └── test_serveur.http
│       ├── java
│       │   └── fr
│       │       └── ulille
│       │           └── iut
│       │               └── tout1art              | méthodes de test
│       │                   ├── ressources
│       │                   │   ├── IngredientRessourceTest.java
│       │                   │   ├── ....
│       │                   │   └── StaticFilesTest.java
│       │                   └── testdao
│       │                       ├── AllTestsDisabled.java
│       │                       ├── ....
│       │                       └── PizzaJpaTestDisabled.java
│       └── resources ' Structure équivalente à la version 'main'
│           │       .......
│           └── static
```
