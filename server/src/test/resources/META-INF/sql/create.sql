CREATE TABLE ingredient (id BIGINT IDENTITY NOT NULL, nom VARCHAR UNIQUE NOT NULL, PRIMARY KEY (id));
CREATE TABLE pizza (id BIGINT IDENTITY NOT NULL, base VARCHAR, nom VARCHAR UNIQUE NOT NULL, prix_grande DOUBLE, prix_petite DOUBLE, PRIMARY KEY (id));
CREATE TABLE ingredientpizza (idpizza BIGINT NOT NULL, idingredient BIGINT NOT NULL, PRIMARY KEY (idpizza, idingredient));
ALTER TABLE ingredientpizza ADD CONSTRAINT FK_ingredientpizza_idpizza FOREIGN KEY (idpizza) REFERENCES pizza (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE ingredientpizza ADD CONSTRAINT FK_ingredientpizza_idingredient FOREIGN KEY (idingredient) REFERENCES ingredient (id) ON DELETE CASCADE ON UPDATE CASCADE;
