package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.ApiV1;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticFilesTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ApiV1();
    }

    @Test
    public void testStaticFiles() {
        String responseMsg = target("").request().get(String.class);
        assertEquals("static content test", responseMsg);
    }
}
