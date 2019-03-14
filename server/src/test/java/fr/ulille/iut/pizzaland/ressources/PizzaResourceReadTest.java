package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.Main;
import fr.ulille.iut.pizzaland.dto.IngredientDto;
import fr.ulille.iut.pizzaland.dto.PizzaDto;
import fr.ulille.iut.pizzaland.dto.PizzaShortDto;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;


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
 *   4   hawaii      creme   11.0        11.5        { 5, 8 }
 */

public class PizzaResourceReadTest {
    private final static int PORT = 63100;

    static ServerManager serverManager;

    @BeforeClass
    public static void initServerManager() {
        serverManager = ServerManager.getSingleton();
    }

    @AfterClass
    public static void shutdownServer() {
        serverManager.shutdown();
    }

    @Test
    public void testGetAllPizzas() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/pizzas")
                .request()
                .get();
        List<PizzaShortDto> pizzas;
        pizzas = response.readEntity(new GenericType<List<PizzaShortDto>>(){});
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(5, pizzas.size());
    }

    @Test
    public void testGetOnePizza() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/pizzas/3")
                .request()
                .get();
        PizzaDto pizza;
        pizza = response.readEntity(PizzaDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(5.5, pizza.getPrix_petite(), 0.009);
        assertEquals(9.0, pizza.getPrix_grande(), 0.009);
        assertEquals("creme", pizza.getBase());
        assertEquals(3, pizza.getId());
    }

    @Test
    public void testGetOnePizza_404() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/pizzas/332")
                .request()
                .get();

        String returned = response.readEntity(String.class);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Pizza not found", returned);
    }

    @Test
    @Produces(MediaType.APPLICATION_JSON)
    public void testGetAllPizzaIngredients() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/pizzas/2/ingredients")
                .request()
                .get();
        List<IngredientDto> ingredients;
        ingredients = response.readEntity(new GenericType<List<IngredientDto>>(){});
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(2, ingredients.size());
    }

    @Test
    public void testGetAllPizzaIngredients_404() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/pizzas/150/ingredients")
                .request()
                .get();
        List<IngredientDto> ingredients;
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
