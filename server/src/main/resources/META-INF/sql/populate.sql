DROP TABLE IF EXISTS artisan CASCADE;
DROP TABLE IF EXISTS produit CASCADE;

CREATE TABLE artisan (id BIGINT IDENTITY NOT NULL, login VARCHAR UNIQUE NOT NULL,mdp VARCHAR NOT NULL,nom VARCHAR NOT NULL,prenom VARCHAR NOT NULL,adresse VARCHAR,mail VARCHAR,tel VARCHAR(10),PRIMARY KEY (id));

CREATE TABLE produit (id BIGINT IDENTITY NOT NULL,nom VARCHAR NOT NULL,description VARCHAR NOT NULL,srcImage VARCHAR,prix DOUBLE,idArtisan INTEGER,PRIMARY KEY (id));

ALTER TABLE produit ADD FOREIGN KEY (idArtisan)  REFERENCES artisan(id);

INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('login', 'mdp', 'nom', 'prenom', 'adresse', 'mail', '0320000000');
INSERT INTO artisan (login , mdp, nom, prenom, adresse, mail, tel) VALUES ('login2', 'mdp2', 'nom2', 'prenom2', 'adresse2', 'mail2', '0320000001');

INSERT INTO produit (nom , description, prix, idArtisan) VALUES ('table', 'Une table simple', '10.5', '1');
INSERT INTO produit (nom , description, prix, idArtisan) VALUES ('vase', 'Un vase simple', '3.5', '1');
INSERT INTO produit (nom , description, prix, idArtisan) VALUES ('lampe', 'Une lampe simple', '5.5', '1');
/*
DROP TABLE IF EXISTS ingredientpizza CASCADE;
DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS pizza CASCADE;
CREATE TABLE ingredient (id BIGINT IDENTITY NOT NULL, nom VARCHAR UNIQUE NOT NULL, PRIMARY KEY (id));
CREATE TABLE pizza (id BIGINT IDENTITY NOT NULL, base VARCHAR, nom VARCHAR UNIQUE NOT NULL, prix_grande DOUBLE, prix_petite DOUBLE, PRIMARY KEY (id));
CREATE TABLE ingredientpizza (idpizza BIGINT NOT NULL, idingredient BIGINT NOT NULL, PRIMARY KEY (idpizza, idingredient));
ALTER TABLE ingredientpizza ADD CONSTRAINT FK_ingredientpizza_idpizza FOREIGN KEY (idpizza) REFERENCES pizza (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE ingredientpizza ADD CONSTRAINT FK_ingredientpizza_idingredient FOREIGN KEY (idingredient) REFERENCES ingredient (id) ON DELETE CASCADE ON UPDATE CASCADE;

insert into ingredient(id, nom) values(1, 'mozzarella');
insert into ingredient(id, nom) values(2, 'jambon');
insert into ingredient(id, nom) values(3, 'champignons');
insert into ingredient(id, nom) values(4, 'olives');
insert into ingredient(id, nom) values(5, 'tomate');
insert into ingredient(id, nom) values(6, 'merguez');
insert into ingredient(id, nom) values(7, 'lardons');
insert into ingredient(id, nom) values(8, 'fromage');
insert into ingredient(id, nom) values(9, 'oeuf');
insert into ingredient(id, nom) values(10, 'poivrons');
insert into ingredient(id, nom) values(11, 'ananas');
insert into ingredient(id, nom) values(12, 'reblochon');

insert into pizza(id, nom, base, prix_petite, prix_grande) values (1, 'Regina', 'tomate', 5.5, 7.5);
insert into pizza(id, nom, base, prix_petite, prix_grande) values (2, 'Napolitaine', 'tomate', 6.2, 8);
insert into pizza(id, nom, base, prix_petite, prix_grande) values (3, 'Spicy', 'crème', 6.5, 9.95);
insert into pizza(id, nom, base, prix_petite, prix_grande) values (4, 'Oranaise', 'tomate', 5.0, 8.0);
insert into pizza(id, nom, base, prix_petite, prix_grande) values (5, 'Carbonara', 'crème', 5.5, 9);
insert into pizza(id, nom, base, prix_petite, prix_grande) values (6, 'Hawaienne', 'crème', 11.0, 11.5);

insert into ingredientpizza(idingredient, idpizza) values(1, 1);
insert into ingredientpizza(idingredient, idpizza) values(2, 1);
insert into ingredientpizza(idingredient, idpizza) values(3, 1);
insert into ingredientpizza(idingredient, idpizza) values(4, 1);
insert into ingredientpizza(idingredient, idpizza) values(1, 2);
insert into ingredientpizza(idingredient, idpizza) values(5, 2);
insert into ingredientpizza(idingredient, idpizza) values(8, 3);
insert into ingredientpizza(idingredient, idpizza) values(6, 3);
insert into ingredientpizza(idingredient, idpizza) values(7, 3);
insert into ingredientpizza(idingredient, idpizza) values(10, 3);
insert into ingredientpizza(idingredient, idpizza) values(1, 4);
insert into ingredientpizza(idingredient, idpizza) values(5, 4);
insert into ingredientpizza(idingredient, idpizza) values(6, 4);
insert into ingredientpizza(idingredient, idpizza) values(7, 5);
insert into ingredientpizza(idingredient, idpizza) values(8, 5);
insert into ingredientpizza(idingredient, idpizza) values(2, 6);
insert into ingredientpizza(idingredient, idpizza) values(8, 6);
insert into ingredientpizza(idingredient, idpizza) values(11, 6);
*/