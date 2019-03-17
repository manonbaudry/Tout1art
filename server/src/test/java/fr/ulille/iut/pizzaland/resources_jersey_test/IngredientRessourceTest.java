package fr.ulille.iut.pizzaland.resources_jersey_test;

import fr.ulille.iut.pizzaland.dto.IngredientDto;
import fr.ulille.iut.pizzaland.ressources.IngredientRessource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.List;
import static org.junit.Assert.assertEquals;

//import java.util.List;


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
 *   4   oeuf           testDeleteOneIngredient             DELETE
 *   5   jambon
 *   6   merguez        testGetOneIngredient                READ
 *   7   champignons
 *   8   ananas         testUpdateIngredient                UPDATE # { nom : olives }
 *   9                  testCreateIngredient                ADD { id : 9 , nom : chorizo }
 *                      testCreateIngredient_406    [406]  ABORT # null
 *                      testCreateIngredient_409    [409]  ABORT # { nom : fromage }
 *                      testGetOneIngredient_404    [404]  ABORT # { id : 6000 }
 *                      testUpdateIngredient_404    [404]  ABORT # { id : 80000 }
 *                      testUpdateIngredient_409    [409]  ABORT # { id : 8 , nom : lardons }
 *                      testDeleteOneIngredient_404 [404]  ABORT # { id : 41200 }
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

public class IngredientRessourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(IngredientRessource.class);
    }

    @Test
    public void testGetAllIngredients() {
        Response response = target("/ingredients").request().get();

        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        List<IngredientDto> ingredients;
        ingredients = response.readEntity(new GenericType<List<IngredientDto>>(){});
        assertEquals("ingredient[] size should be: ", 8, ingredients.size());
    }

    @Test
    public void testCreateIngredient() {
        final String nom = "chorizo";

        Response response = target("/ingredients").request().post(Entity.json("{ \"nom\" : \"" + nom + "\" }"));
        IngredientDto returnedEntity = response.readEntity(IngredientDto.class);

        assertEquals("Http response should be 203: ", Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("Http content should display \"" + nom + "\": ", returnedEntity.getNom(), nom);
        assertEquals("Http location should be: ",
                response.getHeaderString("location"),
                target("/ingredients").getUri().toString() + "/" + returnedEntity.getId());
    }

    @Test
    public void testCreateIngredient_406() {
        Response response = target("/ingredients").request().post(Entity.json("{ \"truc\" : \"muche\" }"));
        String content = response.readEntity(String.class);

        assertEquals("Http response should be 406: ", Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        assertEquals("Http content should be: ","name not specified", content);
    }

    @Test
    public void testCreateIngredient_409() {
        final String nom = "fromage";

        Response response = target("/ingredients").request().post(Entity.json("{ \"nom\" : \"" + nom + "\" }"));
        String content = response.readEntity(String.class);

        assertEquals("Http response should be 406: ", Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertEquals("Http content should be: ","Duplicated name", content);
    }

    @Test
    public void testGetOneIngredients() {
        Response response = target("/ingredients/6").request().get();
        IngredientDto ingredient = response.readEntity(IngredientDto.class);

        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("ingredient name should be: ", "merguez", ingredient.getNom());
        assertEquals("ingredient id should be: ", 6, ingredient.getId());
    }


    @Test
    public void testUpdateIngredient() {
        final long ID = 8;
        final String NOM = "olives";

        Response response = target("/ingredients/" + ID).request().put(Entity.json("{ \"id\" : \"" + ID + "\" , \"nom\" : \"" + NOM + "\" }"));
        IngredientDto returnedEntity = response.readEntity(IngredientDto.class);

        assertEquals("Http response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http content should display \"" + NOM + "\": ", returnedEntity.getNom(), NOM);
        assertEquals("Http content should display \"" + ID + "\": ", returnedEntity.getId(), ID);
    }

    @Test
    public void testUpdateIngredient_404() {
        final long ID = 80000;
        final String NOM = "olives";

        Response response = target("/ingredients/" + ID).request().put(Entity.json("{ \"id\" : \"" + ID + "\" , \"nom\" : \"" + NOM + "\" }"));
        String content = response.readEntity(String.class);

        assertEquals("Http response should be 404: ", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Http content should be: ","Ingredient not found", content);
    }

    @Test
    public void testUpdateIngredient_409() {
        final long ID = 8;
        final String NOM = "lardons";

        Response response = target("/ingredients/" + ID).request().put(Entity.json("{ \"id\" : \"" + ID + "\" , \"nom\" : \"" + NOM + "\" }"));
        String content = response.readEntity(String.class);

        assertEquals("Http response should be 409: ", Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertEquals("Http content should be: ","Duplicated name", content);
    }

    @Test
    public void testDeleteOneIngredient() {
        final long ID = 4;
        Response response = target("/ingredients/" + ID).request().delete();
        assertEquals("Http response should be 201: ", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteOneIngredient_404() {
        final long ID = 41200;
        Response response = target("/ingredients/" + ID).request().delete();
        assertEquals("Http response should be 404: ", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
