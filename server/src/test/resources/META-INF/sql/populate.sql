-- ALTER TABLE ingredientpizza DROP CONSTRAINT FK_ingredientpizza_idpizza;
-- ALTER TABLE ingredientpizza DROP CONSTRAINT FK_ingredientpizza_idingredient;
DROP TABLE IF EXISTS ingredientpizza CASCADE;
DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS pizza CASCADE;
CREATE TABLE ingredient (id BIGINT IDENTITY NOT NULL, nom VARCHAR UNIQUE NOT NULL, PRIMARY KEY (id));
CREATE TABLE pizza (id BIGINT IDENTITY NOT NULL, base VARCHAR, nom VARCHAR UNIQUE NOT NULL, prix_grande DOUBLE, prix_petite DOUBLE, PRIMARY KEY (id));
CREATE TABLE ingredientpizza (idpizza BIGINT NOT NULL, idingredient BIGINT NOT NULL, PRIMARY KEY (idpizza, idingredient));
ALTER TABLE ingredientpizza ADD CONSTRAINT FK_ingredientpizza_idpizza FOREIGN KEY (idpizza) REFERENCES pizza (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE ingredientpizza ADD CONSTRAINT FK_ingredientpizza_idingredient FOREIGN KEY (idingredient) REFERENCES ingredient (id) ON DELETE CASCADE ON UPDATE CASCADE;

insert into ingredient(id, nom) values(1, 'tomate');
insert into ingredient(id, nom) values(2, 'lardons');
insert into ingredient(id, nom) values(3, 'fromage');
insert into ingredient(id, nom) values(4, 'oeuf');
insert into ingredient(id, nom) values(5, 'jambon');
insert into ingredient(id, nom) values(6, 'merguez');
insert into ingredient(id, nom) values(7, 'champignon');
insert into ingredient(id, nom) values(8, 'ananas');

insert into  pizza(id, nom, base, prix_petite, prix_grande) values (1, 'oranaise', 'tomate', 5.0, 8.0);
insert into  pizza(id, nom, base, prix_petite, prix_grande) values (2, 'margarita', 'tomate', 4, 7.5);
insert into  pizza(id, nom, base, prix_petite, prix_grande) values (3, 'carbonara', 'creme', 5.5, 9);
insert into  pizza(id, nom, base, prix_petite, prix_grande) values (4, '4 saisons', 'tomate', 10.0, 15.0);
insert into  pizza(id, nom, base, prix_petite, prix_grande) values (5, 'hawai', 'creme', 11.0, 11.5);

insert into ingredientpizza(idingredient, idpizza) values(1, 1);
insert into ingredientpizza(idingredient, idpizza) values(1, 2);
insert into ingredientpizza(idingredient, idpizza) values(3, 2);
insert into ingredientpizza(idingredient, idpizza) values(2, 3);
insert into ingredientpizza(idingredient, idpizza) values(3, 3);
insert into ingredientpizza(idingredient, idpizza) values(5, 5);
insert into ingredientpizza(idingredient, idpizza) values(8, 5);
