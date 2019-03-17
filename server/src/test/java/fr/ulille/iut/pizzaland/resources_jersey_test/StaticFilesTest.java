package fr.ulille.iut.pizzaland.resources_jersey_test;

import fr.ulille.iut.pizzaland.ressources.IngredientRessource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class StaticFilesTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig();
    }

    @Test
    public void testStaticFiles() {
        Response response = target("/").request().get();
        String content = response.readEntity(String.class);
        assertEquals("Http content should be: ", "static content test", content);
    }
}
