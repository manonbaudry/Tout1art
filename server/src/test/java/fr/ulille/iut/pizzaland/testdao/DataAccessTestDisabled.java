package fr.ulille.iut.pizzaland.testdao;

import static org.junit.Assert.*;

import fr.ulille.iut.pizzaland.dao.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


/*
 * Summary for everey test to make sure they are independent and that they may be run simultaneously
 *
 *   Initial Data (populate.sql)
 *
 *   Ingredients
 *
 *   id  nom             modified by
 *   1   tomate
 *   2   lardons
 *   3   fromage
 *   4   oeuf
 *   5   jambon
 *   6   merguez
 *   7   champignons
 *   8   ananas
 *
 *   Pizzas
 *
 *   id  nom         base    prix_petite prix_grande ingredients         modified by         action
 *   1   oranaise    tomate  5.0         8.0         { 1 }
 *   2   margarita   tomate  4           7.5         { 1, 3 }
 *   3   carbonara   creme   5.5         9           { 2, 3 }
 *   4   4 saisons   tomate  10.0        15.0        { }
 *   5   hawaii      creme   11.0        11.5        { 5, 8 }
 */

public class DataAccessTestDisabled {

    @Test
	public void testUpdatePizzaInfosOk() throws PizzaNameExistsException {
        DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza = dataAccess.getPizzaById(4);
		pizza.setPrix_petite(3.0f);
		pizza.setBase("eau");
		pizza.setPrix_grande(7.0f);
		dataAccess.updatePizza(pizza);

		PizzaEntity pizza2 = dataAccess.getPizzaByName("4 saisons");
		assertEquals(pizza, pizza2);
        dataAccess.closeConnection(false);
	}

	@Test(expected = PizzaNameExistsException.class)
	public void testUpdatePizzaInfosKo() throws PizzaNameExistsException {
        DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza = dataAccess.getPizzaById(4);
		pizza.setNom("oranaise");
		try {
            dataAccess.updatePizza(pizza);
        } finally {
            dataAccess.closeConnection(false);
        }
	}

    @Test
    public void testAddPizzaIngredientsOk() throws PizzaNameExistsException {
        DataAccess dataAccess = DataAccess.begin();
        Set<IngredientEntity> added = new HashSet<>();
        added.add(dataAccess.getIngredientById(3));
        added.add(dataAccess.getIngredientById(6));
        added.add(dataAccess.getIngredientById(7));
        PizzaEntity pizza = dataAccess.getPizzaById(2);

        pizza.getIngredients().addAll(added);
        dataAccess.updatePizza(pizza);

        PizzaEntity pizza2 = dataAccess.getPizzaByName("margarita");
        assertEquals(4, pizza2.getIngredients().size());    //  { 1, 3 } (already there) + { 6, 7 } (added)
        assertTrue(pizza2.getIngredients().containsAll(added));
        dataAccess.closeConnection(false);
    }

    @Test
    public void testRemovePizzaIngredientsOk() throws PizzaNameExistsException {
        DataAccess dataAccess = DataAccess.begin();
        Set<IngredientEntity> removed = new HashSet<>();
        removed.add(dataAccess.getIngredientById(1));
        PizzaEntity pizza = dataAccess.getPizzaById(2);

        pizza.getIngredients().removeAll(removed);
        dataAccess.updatePizza(pizza);

        PizzaEntity pizza2 = dataAccess.getPizzaByName("margarita");
        assertEquals(1, pizza2.getIngredients().size());    // { 1 , 3 } aready there - { 1 } (removed)
        assertTrue(pizza2.getIngredients().contains(dataAccess.getIngredientById(3))); // Checks 3 stil there
        assertFalse(pizza2.getIngredients().contains(dataAccess.getIngredientById(1))); // checks 1 removed
        dataAccess.closeConnection(false);
    }

    @Test
    public void testAddIngredientsOk() throws DatabaseConstraintException {
        DataAccess dataAccess = DataAccess.begin();
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setNom("artichaut");
        long id = dataAccess.createIngredient(ingredient);

        IngredientEntity ingredient2 = dataAccess.getIngredientById(id);
        assertEquals("artichaut", ingredient2.getNom());
        dataAccess.closeConnection(false);
    }

    @Test(expected = DatabaseConstraintException.class)
    public void testAddIngredientsExists() throws DatabaseConstraintException {
        DataAccess dataAccess = DataAccess.begin();
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setNom("tomate");
        try {
            long id = dataAccess.createIngredient(ingredient);
        } finally {
            dataAccess.closeConnection(false);
        }
    }

    @Test
	public void testRemovePizzaIngredients() throws PizzaNameExistsException {
        DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza = dataAccess.getPizzaById(5);
		int actualCount = pizza.getIngredients().size();
		IngredientEntity ingredient = dataAccess.getIngredientById(5);
		pizza.getIngredients().remove(ingredient);
		dataAccess.updatePizza(pizza);

		PizzaEntity pizza2 = dataAccess.getPizzaByName("hawai");
		assertEquals(actualCount-1, pizza2.getIngredients().size());
		assertFalse(pizza2.getIngredients().contains(dataAccess.getIngredientById(5)));
        dataAccess.closeConnection(false);
	}
}
