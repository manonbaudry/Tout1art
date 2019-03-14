package fr.ulille.iut.pizzaland.testdao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DataAccessTestDisabled.class, IngredientJpaTestDisabled.class, PizzaJpaTestDisabled.class })
public class AllTestsDisabled {

}
