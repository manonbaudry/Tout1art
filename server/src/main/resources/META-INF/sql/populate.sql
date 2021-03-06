DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS artisan CASCADE;
DROP TABLE IF EXISTS produit CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS com CASCADE;

CREATE TABLE notification	(id BIGINT IDENTITY NOT NULL,	concerne VARCHAR,	type VARCHAR,		  contenu VARCHAR,	 lu INTEGER,		PRIMARY KEY (id));
CREATE TABLE artisan  		(id BIGINT IDENTITY NOT NULL,	login VARCHAR,		mdp VARCHAR,		  nom VARCHAR, 		 prenom VARCHAR,		adresse VARCHAR,		mail VARCHAR,			tel VARCHAR(10),		PRIMARY KEY (id));
CREATE TABLE produit  		(id BIGINT IDENTITY NOT NULL,	nom VARCHAR,		description VARCHAR , srcImage VARCHAR,	 prix DOUBLE, 			idArtisan INTEGER, 		categorie VARCHAR,  	sousCategorie VARCHAR,  commande INTEGER, 	delai INTEGER, 	statut VARCHAR,		PRIMARY KEY (id));
CREATE TABLE client   		(id BIGINT IDENTITY NOT NULL,	login VARCHAR,		mdp VARCHAR ,		  nom VARCHAR , 	 prenom VARCHAR,		adresse VARCHAR,		mail VARCHAR,			tel VARCHAR(10),		PRIMARY KEY (id));
CREATE TABLE com 	  		(id BIGINT IDENTITY NOT NULL,	idProduit INTEGER, 	idClient INTEGER,	  idArtisan INTEGER, statut VARCHAR,		PRIMARY KEY (id));

ALTER TABLE produit ADD FOREIGN KEY (idArtisan)  REFERENCES artisan(id);
ALTER TABLE com ADD FOREIGN KEY (idProduit)  REFERENCES produit(id);
ALTER TABLE com ADD FOREIGN KEY (idClient)  REFERENCES client(id);


INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('artisan1',	'azerty',		'ART1',		'art1',	'adresse',		'mail@toto.fr',		'0320000000');
INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('artisan2',	'azerty',		'ART2',		'art2',	'adresse2',		'mail2@toto.fr',	'0320000001');
INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('admin',	'root',		'admin',		'Franck',	'adresse',		'mail@toto.fr',		'0320000000');

INSERT INTO client (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('client1',	'123456789ABC',		'nom',		'prenom',	'adresse',		'mail@toto.fr',		'0320000000');
INSERT INTO client (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('client2',	'123456789ABC',		'nom2',		'prenom2',	'adresse2',		'mail2@toto.fr',	'0320000001');


INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Miroir',	 'Un miroir moderne',	'images/miroir.png',	'10.5',	 '2',	 'deco',	'miroir',	'0','3', 'en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Lampe chêne',	 'Inspiration nature pour cette lampe à poser en bois massif.  La technique du chantournage utilisée sur le pied confère à l’objet un look design végétal.',	'images/lampeChene.jpg',	'10.5',	 '1',	 'luminaire',	'lampe',	'0','3', 'en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Lampe ampoule',	 'Cette lampe en forme d’ampoule qui joue avec la lumière est en frêne massif, protégé d’un vernis mat incolore 2 couches. Les 8 lamelles de bois qui la composent se déplient à l’envie.',	'images/lampeAmpoule.jpg',	'15.5',	 '1',	 'luminaire',	'lampe',	'0','3', 'en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Chaise simple',	 'Une chaise simple',	'images/chaise2.png',		'10.5',	 '1',	 'mobilier',	'chaise',	'0', '1', 'en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Chaise moderne',	 'Une chaise moderne',	'images/chaise.png',		'150',	 '2',	 'mobilier',	'chaise',	'0', '1', 'en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Lampe bulles',	 'Lampe bulles réalisée en Freine olivier recouvert d’une double couche de vernis acrylique. Technique de chantournage.',	'images/lampeBulles.png',		'120',	 '1',	 'luminaire',	'lampe',	'0', '1','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Chaise',	 'Une chaise simple',	'images/chaise.jpg',	'10.5',	 '1',	 'mobilier',	'chaise',	'0','3','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Chaise noire',	 'Une chaise simple',	'images/chaiseNoire.jpg',	'10.5',	 '1',	 'mobilier',	'chaise',	'0','3','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Chaise blanche',	 'Une chaise simple',	'images/chaiseBlanche.jpg',	'10.5',	 '1',	 'mobilier',	'chaise',	'0','3','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Vase simple',	 'Un vase simple',		'images/vase.png',	 	'3.5',	 '1',	 'deco',	 	'vase',	 	'0', '5','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Lampe simple',	 'Une lampe simple',	'images/lampe.jpg', 	'5.5',	 '2',	 'luminaire',	'lampe',	'0', '7','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Table basse', 	 'Une table basse 1',		'images/tableBasseSimple.jpg',		'100',	 '1',	 'mobilier',	'table',	'1','4','en vente');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai, statut) VALUES ('Table basse', 	 'Une table basse 2',		'images/tableBasseComplexe.jpg',		'100',	 '1',	 'mobilier',	'table',	'1','4','en attente');

INSERT INTO notification (concerne, type, contenu, lu) VALUES ('admin', 	 'commande',	'en cours',		'0');
INSERT INTO notification (concerne, type, contenu, lu) VALUES ('admin', 	 'commande',	'en attente',	'1');
INSERT INTO notification (concerne, type, contenu, lu) VALUES ('artisan', 	 'produit',		'en cours',		'0');
INSERT INTO notification (concerne, type, contenu, lu) VALUES ('artisan', 	 'produit',		'en cours',		'1');
