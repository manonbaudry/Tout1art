DROP TABLE IF EXISTS artisan CASCADE;
DROP TABLE IF EXISTS produit CASCADE;

CREATE TABLE artisan (
	ida BIGINT IDENTITY NOT NULL, 
	login VARCHAR UNIQUE NOT NULL,
	nom VARCHAR NOT NULL,
	prenom VARCHAR NOT NULL,
	adresse VARCHAR,
	mail VARCHAR,
	tel VARCHAR(10),
	PRIMARY KEY (id));

CREATE TABLE produit (
	idp BIGINT IDENTITY NOT NULL,
	nom VARCHAR NOT NULL,
	description VARCHAR NOT NULL,
	prix DOUBLE,
	idArtisan INT
	PRIMARY KEY (id));

ALTER TABLE produit
    ADD FOREIGN KEY (idArtisan) 
    REFERENCES artisan(ida);

INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel)
	VALUES ('login', 'mdp', 'nom', 'prenom', 'adresse', 'mail', '0320000000');
INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel)
	VALUES ('login2', 'mdp2', 'nom2', 'prenom2', 'adresse2', 'mail2', '0320000001');

INSERT INTO produit (nom , description, prix, idArtisan)
	VALUES ('table', 'Une table simple', '10.5', '1');
INSERT INTO produit (nom , description, prix, idArtisan)
	VALUES ('vase', 'Un vase simple', '3.5', '1');
INSERT INTO produit (nom , description, prix, idArtisan)
	VALUES ('lampe', 'Une lampe simple', '5.5', '1');