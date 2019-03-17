package fr.ulille.iut.pizzaland.resources_jersey_test;

import fr.ulille.iut.pizzaland.dto.IngredientDto;
import fr.ulille.iut.pizzaland.dto.PizzaDto;
import fr.ulille.iut.pizzaland.dto.PizzaShortDto;
import fr.ulille.iut.pizzaland.ressources.PizzaRessource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PizzaResourceReadTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(PizzaRessource.class);
    }

    @Test
    public void testGetAllPizzas() {
        Response response = target("/pizzas").request().get();

        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        List<PizzaShortDto> pizzas;
        pizzas = response.readEntity(new GenericType<List<PizzaShortDto>>(){});
        assertEquals("ingredient[] size should be: ", 5, pizzas.size());
    }

    @Test
    public void testGetOnePizzas() {
        Response response = target("/pizzas/3").request().get();

        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        PizzaDto pizza = response.readEntity(PizzaDto.class);
        assertEquals("pizza name should be: ", "carbonara", pizza.getNom());
        assertEquals("pizza petit prix should be: ", 5.5, pizza.getPrix_petite(), 0.009);
        assertEquals("pizza grand prix should be: ", 9.0, pizza.getPrix_grande(), 0.0009);
        assertEquals("pizza base should be: ", "creme", pizza.getBase());
        assertEquals("pizza Id should be: ", 3, pizza.getId());
    }

    @Test
    public void testGetOnePizza_404() {
        Response response = target("/pizzas/332").request().get();
        String content = response.readEntity(String.class);

        assertEquals("Http response should be 404: ", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Http content should be: ","Pizza not found", content);
    }

    @Test
    public void testGetAllPizzaIngredients() {
        Response response = target("/pizzas/2/ingredients").request().get();
        List<IngredientDto> ingredients = response.readEntity(new GenericType<List<IngredientDto>>(){});

        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        assertEquals("ingredients[] size should be: ", 2, ingredients.size());
    }

    @Test
    public void testGetAllPizzaIngredients_404() {
        Response response = target("/pizzas/150/ingredients").request().get();
        String content = response.readEntity(String.class);

        assertEquals("Http response should be 404: ", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Http content should be: ","Pizza not found", content);
    }
}
