package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.dao.IngredientEntity;
import fr.ulille.iut.pizzaland.dto.ArtisanCreateDto;
import fr.ulille.iut.pizzaland.dto.ArtisanDto;
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

import fr.ulille.iut.pizzaland.dao.ArtisanEntity;
import fr.ulille.iut.pizzaland.dao.DataAccess;
import fr.ulille.iut.pizzaland.dao.PizzaEntity;

@Path("/pizzas")
public class ArtisanRessource {
    private final static Logger logger = LoggerFactory.getLogger(ArtisanRessource.class);

    @Context
    public UriInfo uriInfo;

    public ArtisanRessource() {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ArtisanCreateDto artisanDto) {
        DataAccess dataAccess = DataAccess.begin();

        // Construction (données primitives puis composition
        ArtisanEntity artisanEntity = ArtisanEntity.convertFromArtisanCreateDto(artisanDto);

        if (artisanEntity.getNom() == null) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }

        try {
            long id = dataAccess.createArtisan(artisanEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(ArtisanEntity.convertToDto(artisanEntity)).location(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ArtisanDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<ArtisanEntity> la = dataAccess.getAllArtisan();
        dataAccess.closeConnection(false);
        return la.stream().map(ArtisanEntity::convertToDto).collect(Collectors.toList());
    }

    @GET
    @Path("{idArtisan}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdArtisan(@PathParam("idArtisan") long idArtisan) {
        DataAccess dataAccess = DataAccess.begin();
        ArtisanEntity artisanEntity = dataAccess.getArtisanById(idArtisan);
        if ( artisanEntity != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(ArtisanEntity.convertToDto(artisanEntity)).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Artisan not found").build();
        }
    }
/*
    @GET
    @Path("{idArtisan}/produits")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduitsArtisan(@PathParam("idArtisan") long idArtisan) {
        DataAccess dataAccess = DataAccess.begin();
        ArtisanEntity p = dataAccess.getArtisanById(idArtisan);
        if (p != null) {
            dataAccess.closeConnection(true);
            return Response.accepted().status(Status.OK).entity(p.getProduits().stream().map(IngredientEntity::convertToDto).collect(Collectors.toList())).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Pizza not found").build();
        }
    }*/

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
