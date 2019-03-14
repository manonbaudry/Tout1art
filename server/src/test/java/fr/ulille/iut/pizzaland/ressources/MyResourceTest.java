package fr.ulille.iut.pizzaland.ressources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import fr.ulille.iut.pizzaland.Main;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.*;

import fr.ulille.iut.pizzaland.dao.PizzaEntity;

import static org.junit.Assert.*;

public class MyResourceTest {
    static ServerManager serverManager;

    @BeforeClass
    public static void initServerManager() {        serverManager = ServerManager.getSingleton();

    }

    @AfterClass
    public static void shutdownServer() {
        serverManager.shutdown();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        WebTarget target = serverManager.getWebTarget();
        String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    @Test
    public void testInitH2() {
        WebTarget target = serverManager.getWebTarget();
        DataAccess dataAccess = DataAccess.begin();
        PizzaEntity pizza = dataAccess.getPizzaById(1);
        assertNotNull(pizza);
        assertEquals("oranaise", pizza.getNom());
        dataAccess.closeConnection(false);
    }
}
