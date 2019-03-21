package fr.ulille.iut.tout1art.testdao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DataAccessTestDisabled.class, IngredientJpaTestDisabled.class, PizzaJpaTestDisabled.class })
public class AllTestsDisabled {

}
