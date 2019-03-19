# Configuration projet JS

Ce projet JS correspond à la solution du TP Pizzaland.
<br>L'objectif est double :
- vous mettre à disposition un projet JS prêt à l'emploi, c'est à dire déjà configuré pour utiliser les différents outils vus en cours et qui permettent de faire du développement JS moderne :
	- [Babel](https://babeljs.io/) pour la compilation du JS ES6+ en ES5
	- [Webpack](https://webpack.js.org/) pour la gestion des modules
	- [Flow](https://flow.org/) pour le typage statique en JS
- vous donner un exemple de code JS qui utilise le serveur REST/JSON fourni dans le dossier `server`

## Installation
Si ce n'est pas fait sur votre poste, pensez à configurer le proxy pour npm :
```bash
npm config set proxy http://cache.univ-lille1.fr:3128
```

Lancez la commande `npm install` dans ce dossier (au même niveau que le fichier `package.json`) pour installer les dépendances du projet (Babel, Webpack, Flow, etc.).

## Compilation
La compilation du JS est nécessaire pour supporter les anciens navigateurs notamment pour la syntaxe ES6+ et le système de modules.

Lorsque la compilation est lancée, un fichier `main.bundle.js` est généré dans le dossier `server/src/main/resources/static` (cf. `webpack.config.js`). C'est ce fichier qui est chargé dans la page `index.html` affichée sur http://localhost:8080/

### Compilation en mode dev (avec watch pour recompiler à chaque changement de fichier)
Lancez la commande `npm run watch` dans ce dossier (au même niveau que le fichier `package.json`).

### Compilation en mode prod (pour le déploiement final)
Lancez la commande `npm run build` dans ce dossier (au même niveau que le fichier `package.json`).

## Explications code
Le code qui est fourni correspond au projet Pizzaland finalisé et nettoyé. Il est remis à titre d'exemple mais rien ne vous oblige à reprendre la structure JS qui est proposée.

L'arborescence est la suivante :
```bash
src/
   ├─ components /
   |   ├─ Component.js
   |   ├─ Img.js
   |   ├─ Menu.js
   |   └─ PizzaThumbnail.js
   ├─ pages /
   |   ├─ AddPizzaPage.js
   |   ├─ HomePage.js
   |   └─ Page.js
   ├─ PageRenderer.js
   └─ main.js
```

Les principaux fichiers sont :
- `src/main.js` : point d'entrée de l'application. C'est dans ce fichier que l'on instancie les différentes pages de l'application et qu'on gère la navigation (le clic sur les liens du menu)
- `src/PageRenderer.js` : Classe qui permet d'afficher une page dans le html (gère le h1 et le contenu de la page)
- `src/components/Menu.js` : classe qui permet d'activer le bon lien du menu de navigation selon la page affichée. Utilisée dans le `src/main.js`
- `src/pages/*` : pages de l'application. Deux approches sont présentées :
	1. la classe `HomePage` utilise le système de composants imbriqués (propriétés tagName, attributes, et children) comme vu dans le 2e TP.
	2. la classe AddPizzaPage propose une approche plus simple : la méthode `render()` est overridée pour retourner juste une chaîne de caractères au format HTML.

	**A vous de voir quelle approche vous semble la plus simple pour créer vos propres pages, ou d'inventer la votre !**