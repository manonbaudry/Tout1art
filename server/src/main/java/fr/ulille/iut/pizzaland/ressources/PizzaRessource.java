package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.dao.IngredientEntity;
import fr.ulille.iut.pizzaland.dto.PizzaCreateDto;
import fr.ulille.iut.pizzaland.dto.PizzaShortDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import java.net.URI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;

@Path("/pizzas")
public class PizzaRessource {
    private final static Logger logger = LoggerFactory.getLogger(PizzaRessource.class);

    @Context
    public UriInfo uriInfo;

    public PizzaRessource() {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(PizzaCreateDto pizzaDto) {
        DataAccess dataAccess = DataAccess.begin();

        // Construction (données primitives puis composition
        PizzaEntity pizzaEntity = PizzaEntity.convertFromPizzaCreateDto(pizzaDto);

        if (pizzaEntity.getNom() == null) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }

        // Construit l'ensemble des ingredients à partir du tableau de long integers
        Set<IngredientEntity> composition = new HashSet<>();
        for (long ingredientId : pizzaDto.getIngredients()) {
            IngredientEntity ingredient = dataAccess.getIngredientById(ingredientId);
            composition.add(ingredient);
        }
        pizzaEntity.setIngredients(composition);
        try {
            long id = dataAccess.createPizza(pizzaEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(PizzaEntity.convertToDto(pizzaEntity)).location(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PizzaShortDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<PizzaEntity> lp = dataAccess.getAllPizzas();
        dataAccess.closeConnection(false);
        return lp.stream().map(PizzaEntity::convertToSimpleDto).collect(Collectors.toList());
    }

    @GET
    @Path("{idPizza}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdPizza(@PathParam("idPizza") long idPizza) {
        DataAccess dataAccess = DataAccess.begin();
        PizzaEntity p = dataAccess.getPizzaById(idPizza);
        if ( p != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(PizzaEntity.convertToDto(p)).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Pizza not found").build();
        }
    }

    @GET
    @Path("{idPizza}/ingredients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientsPizza(@PathParam("idPizza") long idPizza) {
        DataAccess dataAccess = DataAccess.begin();
        PizzaEntity p = dataAccess.getPizzaById(idPizza);
        if (p != null) {
            dataAccess.closeConnection(true);
            return Response.accepted().status(Status.OK).entity(p.getIngredients().stream().map(IngredientEntity::convertToDto).collect(Collectors.toList())).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Pizza not found").build();
        }
    }

    @DELETE
    @Path("{idPizza}")
    public Response delete(@PathParam("idPizza") long idPizza) {
        DataAccess dataAccess = DataAccess.begin();
        try {
            dataAccess.deletePizza(idPizza);
            dataAccess.closeConnection(true);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Pizza not found").build();
        }
    }
}
