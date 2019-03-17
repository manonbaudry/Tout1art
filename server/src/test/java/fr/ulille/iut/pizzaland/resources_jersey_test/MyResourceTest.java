package fr.ulille.iut.pizzaland.resources_jersey_test;

import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;
import fr.ulille.iut.pizzaland.ressources.MyResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        Application application = new ResourceConfig(MyResource.class);
        return application;
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        Response response = target("/myresource").request().get();
        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-type should be: ", MediaType.TEXT_PLAIN, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        String content = response.readEntity(String.class);
        assertEquals("Content of response should is: ", "Got it!", content);
    }

    @Test
    public void testInitH2() {
        DataAccess dataAccess = DataAccess.begin();
        PizzaEntity pizza = dataAccess.getPizzaById(1);
        assertNotNull(pizza);
        assertEquals("oranaise", pizza.getNom());
        dataAccess.closeConnection(false);
    }
}
