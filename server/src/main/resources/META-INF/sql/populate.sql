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
	idp BIGINT IDENTITY NOT NULL, nom des prix art
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