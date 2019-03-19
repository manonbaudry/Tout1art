# Configuration projet JS

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
