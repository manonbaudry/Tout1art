package fr.ulille.iut.tout1art.ressources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import java.net.URI;

import java.util.List;
import java.util.stream.Collectors;

import fr.ulille.iut.tout1art.dao.CommandeEntity;
import fr.ulille.iut.tout1art.dao.DataAccess;
import fr.ulille.iut.tout1art.dao.IngredientEntity;
import fr.ulille.iut.tout1art.dao.ProduitEntity;
import fr.ulille.iut.tout1art.dto.ProduitCreateDto;
import fr.ulille.iut.tout1art.dto.ProduitDto;

@Path("/produit")
public class ProduitRessource {
    private final static Logger logger = LoggerFactory.getLogger(ProduitRessource.class);

    @Context
    public UriInfo uriInfo;

    public ProduitRessource() {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ProduitCreateDto produitDto) {
        DataAccess dataAccess = DataAccess.begin();

        // Construction (données primitives puis composition
        ProduitEntity produitEntity = ProduitEntity.convertFromProduitCreateDto(produitDto);

        if (produitEntity.getNom() == null) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }
        try {
            long id = dataAccess.createProduit(produitEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(ProduitEntity.convertToDto(produitEntity)).location(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).build();
        }
    }
  
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, ProduitEntity produit) {
        DataAccess dataAccess = DataAccess.begin();
        ProduitEntity produitBDD = dataAccess.getProduitById(id);
        if (produitBDD == null) {
            return Response.status(Status.NOT_FOUND).entity("Produit not found").build();
        } else {
            try {
                produitBDD.setCommande(1);
                dataAccess.updateProduit(produitBDD);
                dataAccess.closeConnection(true);
                return Response.ok(produitBDD).build(); //  .created(instanceURI).build();
            } catch (Exception ex) {
                dataAccess.closeConnection(false);
                return Response.status(Status.CONFLICT).entity("Duplicated name").build();
            }
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProduitDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<ProduitEntity> lp = dataAccess.getAllProduits();
        dataAccess.closeConnection(false);
        return lp.stream().map(ProduitEntity::convertToDto).collect(Collectors.toList());
    }

    @GET
    @Path("{idProduit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdProduit(@PathParam("idProduit") long idProduit) {
        DataAccess dataAccess = DataAccess.begin();
        ProduitEntity p = dataAccess.getProduitById(idProduit);
        if ( p != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(ProduitEntity.convertToDto(p)).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Produit not found").build();
        }
    }

   
//    @GET
//    @Path("{idProduit}/ingredients")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getIngredientsProduit(@PathParam("idProduit") long idProduit) {
//        DataAccess dataAccess = DataAccess.begin();
//        ProduitEntity p = dataAccess.getProduitById(idProduit);
//        if (p != null) {
//            dataAccess.closeConnection(true);
//            return Response.accepted().status(Status.OK).entity(p.getIngredients().stream().map(IngredientEntity::convertToDto).collect(Collectors.toList())).build();
//        } else {
//            dataAccess.closeConnection(false);
//            return Response.status(Status.NOT_FOUND).entity("Produit not found").build();
//        }
//    }

    @DELETE
    @Path("{idProduit}")
    public Response delete(@PathParam("idProduit") long idProduit) {
        DataAccess dataAccess = DataAccess.begin();
        try {
            dataAccess.deleteProduit(idProduit);
            dataAccess.closeConnection(true);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Produit not found").build();
        }
    }
}
