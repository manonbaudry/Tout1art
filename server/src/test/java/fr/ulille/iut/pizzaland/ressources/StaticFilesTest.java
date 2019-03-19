package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.Main;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticFilesTest {
    private static final int PORT = 63000;

    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void initServer() {
        server = Main.startServer(PORT);
        target = ClientBuilder.newClient().target(Main.getURI(PORT, true));
    }

    @AfterClass
    public static void shutdownServer() {
        server.shutdown();
    }

    @Test
    public void testStaticFiles() {
        String responseMsg = target.path("").request().get(String.class);
        assertEquals("static content test", responseMsg);
    }
}
