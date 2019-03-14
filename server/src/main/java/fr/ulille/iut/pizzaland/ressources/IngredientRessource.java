package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.dao.PizzaEntity;
import fr.ulille.iut.pizzaland.dto.IngredientDto;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.IngredientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/ingredients")
public class IngredientRessource {
    final static Logger logger = LoggerFactory.getLogger(IngredientRessource.class);

    @Context
    public UriInfo uriInfo;

    public IngredientRessource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IngredientDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<IngredientEntity> li = dataAccess.getAllIngredients();
        dataAccess.closeConnection(true);
        return li.stream().map(IngredientEntity::convertToDto).collect(Collectors.toList());
    }

    @POST
    public Response create(IngredientEntity ingredient) {
        DataAccess dataAccess = DataAccess.begin();
        if (ingredient.getNom() == null) {
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }
        try {
            long id = dataAccess.createIngredient(ingredient);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(ingredient).location(instanceURI).build(); //  .created(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).entity("Duplicated name").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNom(@PathParam("id") long id) {
        DataAccess dataAccess =DataAccess.begin();
		IngredientEntity ingredient = dataAccess.getIngredientById(id);
		if ( ingredient != null ) {
		    dataAccess.closeConnection(true);
		    return Response.ok(ingredient).build();
		} else {
            dataAccess.closeConnection(false);
		    return Response.status(Status.NOT_FOUND).entity("Ingredient not found").build();
	    }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, IngredientEntity ingredient) {
        DataAccess dataAccess = DataAccess.begin();
        IngredientEntity ingredientBDD = dataAccess.getIngredientById(id);
        if (ingredientBDD == null) {
            return Response.status(Status.NOT_FOUND).entity("Ingredient not found").build();
        } else {
            try {
                ingredientBDD.setNom(ingredient.getNom());
                dataAccess.updateIngredient(ingredientBDD);
                dataAccess.closeConnection(true);
                return Response.ok(ingredientBDD).build(); //  .created(instanceURI).build();
            } catch (Exception ex) {
                dataAccess.closeConnection(false);
                return Response.status(Status.CONFLICT).entity("Duplicated name").build();
            }
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") long id) {
        DataAccess dataAccess = DataAccess.begin();
		try {
			dataAccess.deleteIngredient(id);
			dataAccess.closeConnection(true);
            return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
		    dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Ingredient not found").build();
		}
    }
}
