package fr.ulille.iut.pizzaland.resources_jersey_test;

import fr.ulille.iut.pizzaland.dto.IngredientDto;
import fr.ulille.iut.pizzaland.dto.IngredientPayloadDto;
import fr.ulille.iut.pizzaland.ressources.ServerManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

public class IngredientRessourceTest {
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
    public void testGetAllIngredients() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/ingredients")
                .request()
                .get();
//        List<IngredientDto> ingredients;
//        ingredients = response.readEntity(new GenericType<List<IngredientDto>>(){});
//        assertEquals(8, ingredients.size());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreateIngredient() {
        WebTarget target = serverManager.getWebTarget();
        IngredientDto ingredient = new IngredientDto();
        ingredient.setNom("Chorizo");
        Response response = target.path("/ingredients")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(ingredient));
        IngredientDto returnedEntity = response.readEntity(IngredientDto.class);

        assertEquals(returnedEntity.getNom(), ingredient.getNom());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals((target.path("/ingredients/" + returnedEntity.getId())).getUri(), response.getLocation());
    }

    @Test
    public void testCreateIngredient_406() {
        WebTarget target = serverManager.getWebTarget();
        IngredientDto ingredient = new IngredientDto();
        ingredient.setNom(null);
        Response response = target.path("/ingredients")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(ingredient));
        String returned = response.readEntity(String.class);

        assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
        assertEquals("name not specified", returned);
    }

    @Test
    public void testCreateIngredient_409() {
        WebTarget target = serverManager.getWebTarget();
        IngredientDto ingredient = new IngredientDto();
        ingredient.setNom("fromage");
        Response response = target.path("/ingredients")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(ingredient));

        String returned = response.readEntity(String.class);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertEquals("Duplicated name", returned);
    }

    @Test
    public void testGetOneIngredient() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/ingredients/6")
                .request()
                .get();

        IngredientDto ingredient;
        ingredient = response.readEntity(IngredientDto.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("merguez", ingredient.getNom());
        assertEquals(6, ingredient.getId());
    }

    @Test
    public void testGetOneIngredient_404() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/ingredients/6000")
                .request()
                .get();

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateIngredient() {
        final long ID = 8;
        final String NOM = "olives";

        WebTarget target = serverManager.getWebTarget();
        IngredientDto ingredient = new IngredientDto();
        ingredient.setId(ID);
        ingredient.setNom(NOM);
        Response response = target.path("/ingredients/8")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(ingredient));
        IngredientDto returnedEntity = response.readEntity(IngredientDto.class);

        assertEquals(returnedEntity.getId(), ID);
        assertEquals(returnedEntity.getNom(), NOM);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateIngredient_404() {
        final long ID = 80000;
        final String NOM = "olives";

        WebTarget target = serverManager.getWebTarget();
        IngredientPayloadDto ingredient = new IngredientPayloadDto();
        ingredient.setNom(NOM);
        Response response = target.path("/ingredients/" + ID)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(ingredient));

        String returned = response.readEntity(String.class);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Ingredient not found", returned);
    }

    @Test
    public void testUpdateIngredient_409() {
        final long ID = 8;
        final String NOM = "lardons";

        WebTarget target = serverManager.getWebTarget();
        IngredientPayloadDto ingredient = new IngredientPayloadDto();
        ingredient.setNom(NOM);
        Response response = target.path("/ingredients/" + ID)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.json(ingredient));

        String returned = response.readEntity(String.class);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertEquals("Duplicated name", returned);
    }

    @Test
    public void testDeleteOneIngredient() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/ingredients/4")
                .request()
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteOneIngredient_404() {
        WebTarget target = serverManager.getWebTarget();
        Response response = target.path("/ingredients/41200")
                .request()
                .delete();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
