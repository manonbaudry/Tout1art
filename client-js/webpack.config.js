const path = require('path');

module.exports = {
	// Fichier d'entrée
	entry: './src/main.js',
	// Fichier de sortie
	output: {
		path: path.resolve(__dirname, '../server/src/main/resources/static/build'),
		filename: 'main.bundle.js'
	},
	module: {
		rules: [
			{
				test: /\.js$/, // tous les fichiers .js
				exclude: /node_modules/, // sauf le dossier node_modules
				use: {
					// seront transpilés par babel
					loader: 'babel-loader'
				}
			}
		]
	},
	devtool: 'source-map'
};
