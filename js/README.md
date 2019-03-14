# Configuration projet JS

## Installation
Lancer la commande `npm install` dans ce dossier (au même niveau que le fichier `package.json`).

## Compilation
La compilation du JS est nécessaire pour supporter les anciens navigateurs notamment pour la syntaxe ES6+ et le système de modules.

Lorsque la compilation est lancée, un fichier `main.bundle.js` est généré dans le dossier `java/src/main/resources/static` (cf. `webpack.config.js`). C'est ce fichier qui est chargé dans la page `index.html`.

### compilation en mode dev (avec watch pour recompiler à chaque changement de fichier)
Lancer la commande `npm run watch` dans ce dossier (au même niveau que le fichier `package.json`).

### compilation en mode prod (pour le déploiement)
Lancer la commande `npm run build` dans ce dossier (au même niveau que le fichier `package.json`).
