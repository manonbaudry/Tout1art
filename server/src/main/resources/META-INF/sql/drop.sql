-- ALTER TABLE ingredientpizza DROP CONSTRAINT FK_ingredientpizza_idpizza;
-- ALTER TABLE ingredientpizza DROP CONSTRAINT FK_ingredientpizza_idingredient;
DROP TABLE IF EXISTS ingredientpizza CASCADE;
DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS pizza CASCADE;
