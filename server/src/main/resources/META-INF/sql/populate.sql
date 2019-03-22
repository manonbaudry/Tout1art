DROP TABLE IF EXISTS artisan CASCADE;
DROP TABLE IF EXISTS produit CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS com CASCADE;


CREATE TABLE artisan  (id BIGINT IDENTITY NOT NULL,	login VARCHAR UNIQUE NOT NULL,	mdp VARCHAR NOT NULL,		  nom VARCHAR NOT NULL, prenom VARCHAR NOT NULL,		adresse VARCHAR,	mail VARCHAR,			tel VARCHAR(10),	PRIMARY KEY (id));
CREATE TABLE produit  (id BIGINT IDENTITY NOT NULL,	nom VARCHAR NOT NULL,			description VARCHAR NOT NULL, srcImage VARCHAR,		prix DOUBLE, idArtisan INTEGER, categorie VARCHAR,  sousCategorie VARCHAR,  commande INTEGER, delai INTEGER,	PRIMARY KEY (id));
CREATE TABLE client   (id BIGINT IDENTITY NOT NULL,	login VARCHAR UNIQUE NOT NULL,	mdp VARCHAR NOT NULL,		  nom VARCHAR NOT NULL, prenom VARCHAR NOT NULL,		adresse VARCHAR,	mail VARCHAR,			tel VARCHAR(10),	PRIMARY KEY (id));
CREATE TABLE com 	  (id BIGINT IDENTITY NOT NULL,	idProduit INTEGER, 				idClient INTEGER,			  idArtisan INTEGER,	statut VARCHAR,					PRIMARY KEY (id));

ALTER TABLE produit ADD FOREIGN KEY (idArtisan)  REFERENCES artisan(id);
ALTER TABLE com ADD FOREIGN KEY (idProduit)  REFERENCES produit(id);
ALTER TABLE com ADD FOREIGN KEY (idClient)  REFERENCES client(id);


INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('artisan1',	'123456789ABC',		'nom',		'prenom',	'adresse',		'mail@toto.fr',		'0320000000');
INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('artisan2',	'123456789ABC',		'nom2',		'prenom2',	'adresse2',		'mail2@toto.fr',	'0320000001');
INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('admin',	'root',		'admin',		'Franck',	'adresse',		'mail@toto.fr',		'0320000000');

INSERT INTO client (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('client1',	'123456789ABC',		'nom',		'prenom',	'adresse',		'mail@toto.fr',		'0320000000');
INSERT INTO client (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('client2',	'123456789ABC',		'nom2',		'prenom2',	'adresse2',		'mail2@toto.fr',	'0320000001');

INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('table simple',	 'Une table simple',	'images/tableSimple.jpg',		'10.5',	 '1',	 'mobilier',	'table',	'0', '1');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('table moderne',	 'Une table simple',	'images/tableModerne.jpg',		'150',	 '1',	 'mobilier',	'table',	'0', '1');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('table complexe',	 'Une table simple',	'images/tableComplexe.jpg',		'120',	 '1',	 'mobilier',	'table',	'0', '1');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('chaise simple',	 'Une chaise simple',	'images/chaise.jpg',	'10.5',	 '1',	 'mobilier',	'chaise',	'0','3');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('chaise noire',	 'Une chaise simple',	'images/chaiseNoire.jpg',	'10.5',	 '1',	 'mobilier',	'chaise',	'0','3');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('chaise blanche',	 'Une chaise simple',	'images/chaiseBlanche.jpg',	'10.5',	 '1',	 'mobilier',	'chaise',	'0','3');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('vase simple',	 'Un vase simple',		'images/vase.jpg',	 	'3.5',	 '1',	 'deco',	 	'vase',	 	'0', '5');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('lampe simple',	 'Une lampe simple',	'images/lampe.jpg', 	'5.5',	 '1',	 'luminaire',	'lampe',	'0', '7');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('table basse', 	 'Une table base 1',		'images/tableBasseSimple.jpg',		'100',	 '1',	 'mobilier',	'table',	'1','4');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('table basse', 	 'Une table base 2',		'images/tableBasseComplexe.jpg',		'100',	 '1',	 'mobilier',	'table',	'1','4');
INSERT INTO produit (nom, description, srcImage, prix, idArtisan, categorie, sousCategorie, commande, delai) VALUES ('lampe de chevet', 'Une lampe de chevet', 'images/lampe.jpg',		'10.5',	 '2',	 'luminaire',	'lampe', 	'0','42');

INSERT INTO com (idProduit, idClient, idArtisan, statut) VALUES ('5','1', '1', 'En cours');
INSERT INTO com (idProduit, idClient, idArtisan, statut) VALUES ('3','1', '1', 'En cours');
INSERT INTO com (idProduit, idClient, idArtisan, statut) VALUES ('6','2', '2', 'En cours');
