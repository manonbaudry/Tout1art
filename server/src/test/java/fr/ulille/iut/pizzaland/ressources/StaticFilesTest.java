package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticFilesTest {
    static ServerManager serverManager;

    @BeforeClass
    public static void initServerManager() {        serverManager = ServerManager.getSingleton();

    }

    @AfterClass
    public static void shutdownServer() {
        serverManager.shutdown();
    }

    @Test
    public void testStaticFiles() {
        WebTarget target = serverManager.getWebTarget(true);
        String responseMsg = target.path("").request().get(String.class);
        assertEquals("static content test", responseMsg);
    }
}
