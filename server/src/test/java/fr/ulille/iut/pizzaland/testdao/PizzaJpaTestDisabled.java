package fr.ulille.iut.pizzaland.testdao;

import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.IngredientEntity;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;
import fr.ulille.iut.pizzaland.dao.PizzaNameExistsException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class PizzaJpaTestDisabled {

    private final static Logger logger = LoggerFactory.getLogger(PizzaJpaTestDisabled.class);

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

	// Retreive Pizzas

	@Test
	public void testGetAllPizzas() {
		DataAccess dataAccess = DataAccess.begin();
		List<PizzaEntity> pizzas;
		pizzas = dataAccess.getAllPizzas();
//		if (pizzas.size() != 5) {
			for (PizzaEntity pizza : pizzas) {
				logger.error(pizza.toString());
			}
//		}
		assertEquals(5, pizzas.size());	// { 1, 2, 3, 4, 5 } already there
		dataAccess.closeConnection(false);
	}

    @Test
	public void testGetPizzaById_found() {
        DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza;
		pizza = dataAccess.getPizzaById(2);
		assertEquals("margarita", pizza.getNom());
		assertEquals(4.0, pizza.getPrix_petite(), 0.01);
		assertEquals(7.5, pizza.getPrix_grande(), 0.01);
		assertEquals("tomate", pizza.getBase());
		assertEquals(2, pizza.getIngredients().size());

		assertTrue(pizza.getIngredients().contains(dataAccess.getIngredientById(1)));
		assertTrue(pizza.getIngredients().contains(dataAccess.getIngredientById(3)));
		dataAccess.closeConnection(false);
	}

    @Test
    public void testGetPizzaById_NotFound() {
		DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza;
		pizza = dataAccess.getPizzaById(12);
		assertNull(pizza);
		dataAccess.closeConnection(false);
	}

    @Test
	public void testGetPizzaByName_Found() {
		DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza;
		pizza = dataAccess.getPizzaByName("carbonara");
		assertEquals(dataAccess.getPizzaById(3), pizza);
		dataAccess.closeConnection(false);
	}

	@Test
	public void testGetPizzaByName_NotFound() {
		DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza;
		pizza = dataAccess.getPizzaByName("fantome");
		assertNull(pizza);
		dataAccess.closeConnection(false);
	}

	// Update pizza Info

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
		try {
			PizzaEntity pizza = dataAccess.getPizzaById(4);
			pizza.setNom("oranaise");
			dataAccess.updatePizza(pizza);
		} finally {
			dataAccess.closeConnection(false);
		}
	}

	@Test
	public void testAddPizzaIngredient() throws PizzaNameExistsException {
		DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza = dataAccess.getPizzaById(4);
		Set<IngredientEntity> added = new HashSet<>();
		added.add(dataAccess.getIngredientById(1));
		added.add(dataAccess.getIngredientById(3));
		added.add(dataAccess.getIngredientById(4));
		pizza.getIngredients().addAll(added);
		dataAccess.updatePizza(pizza);

		PizzaEntity pizza2 = dataAccess.getPizzaByName("4 saisons");
		assertEquals(pizza, pizza2);
		assertTrue(pizza.getIngredients().containsAll(added));
		dataAccess.closeConnection(false);
	}

	@Test
	public void testRemovePizzaIngredient() throws PizzaNameExistsException {
		DataAccess dataAccess = DataAccess.begin();
		PizzaEntity pizza = dataAccess.getPizzaById(3);	// Initially { 2, 3 }
		Set<IngredientEntity> removed = new HashSet<>();
		removed.add(dataAccess.getIngredientById(3));
		pizza.getIngredients().removeAll(removed);			// Finally { 3 }
		dataAccess.updatePizza(pizza);

		PizzaEntity pizza2 = dataAccess.getPizzaByName("carbonara");
		assertEquals(pizza, pizza2);
		assertTrue(pizza.getIngredients().contains(dataAccess.getIngredientById(2)));
		assertFalse(pizza.getIngredients().contains(dataAccess.getIngredientById(3)));
		dataAccess.closeConnection(false);
	}
}
