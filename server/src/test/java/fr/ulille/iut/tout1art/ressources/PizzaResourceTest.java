package fr.ulille.iut.tout1art.ressources;

import fr.ulille.iut.tout1art.ApiV1;
import fr.ulille.iut.tout1art.dto.PizzaCreateDto;
import fr.ulille.iut.tout1art.dto.PizzaDto;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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

public class PizzaResourceTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ApiV1();
    }


    @Test
    public void testCreatePizza() {
        PizzaCreateDto pizza = new PizzaCreateDto();
        pizza.setNom("Quatre saisons");
        pizza.setBase("tomate");
        pizza.setPrix_petite(3.0F);
        pizza.setPrix_grande(6.5F);
        pizza.setIngredients(Arrays.asList(1L, 3L, 5L));
        Response response = target("/pizzas")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(pizza));

        PizzaDto returnedEntity = response.readEntity(PizzaDto.class);
        assertNotNull(returnedEntity);
        assertEquals(returnedEntity.getNom(), pizza.getNom());
        assertEquals(3, returnedEntity.getIngredients().size());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals((target("/pizzas/" + returnedEntity.getId())).getUri(), response.getLocation());
    }

    @Test
    public void testCreatePizza_406() {
        PizzaCreateDto pizza = new PizzaCreateDto();
//        pizza.setNom("oranaise");
        pizza.setBase("tomate");
        pizza.setPrix_petite(3.0F);
        pizza.setPrix_grande(6.5F);
        pizza.setIngredients(Arrays.asList(1L, 3L, 5L));
        Response response = target("/pizzas")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(pizza));

        String returned = response.readEntity(String.class);
        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        assertEquals("name not specified", returned);
    }

    @Test
    public void testDeleteOnePizza() {
        Response response = target("/pizzas/1")
                .request()
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteOnePizza_notFound() {
        Response response = target("/pizzas/608")
                .request()
                .delete();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}

