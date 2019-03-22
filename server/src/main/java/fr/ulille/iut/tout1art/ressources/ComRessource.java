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

import fr.ulille.iut.tout1art.dao.ComEntity;
import fr.ulille.iut.tout1art.dao.DataAccess;
import fr.ulille.iut.tout1art.dao.ProduitEntity;
import fr.ulille.iut.tout1art.dto.ComCreateDto;
import fr.ulille.iut.tout1art.dto.ComDto;
import fr.ulille.iut.tout1art.dto.ProduitDto;

@Path("/com")
public class ComRessource {
    private final static Logger logger = LoggerFactory.getLogger(ComRessource.class);

    @Context
    public UriInfo uriInfo;

    public ComRessource() {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ComCreateDto comDto) {
        DataAccess dataAccess = DataAccess.begin();

        // Construction (données primitives puis composition
        ComEntity comEntity = ComEntity.convertFromComCreateDto(comDto);

        if (comEntity == null) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }
        try {
            long id = dataAccess.createCom(comEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(ComEntity.convertToDto(comEntity)).location(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ComDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<ComEntity> la = dataAccess.getAllComs();
        dataAccess.closeConnection(false);
        return la.stream().map(ComEntity::convertToDto).collect(Collectors.toList());
    }
    
    @GET
    @Path("artisan/{idArtisan}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProduitDto> getProduitByArtisan(@PathParam("idArtisan") int idArtisan) {
        DataAccess dataAccess = DataAccess.begin();
        List<ProduitEntity> la = dataAccess.getProduitByArtisan(idArtisan);
        dataAccess.closeConnection(false);
        return la.stream().map(ProduitEntity::convertToDto).collect(Collectors.toList());
    }

    
    @GET
    @Path("{idCom}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdCom(@PathParam("idCom") long idCom) {
        DataAccess dataAccess = DataAccess.begin();
        ComEntity ComEntity = dataAccess.getComById(idCom);
        if ( ComEntity != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(ComEntity.convertToDto(ComEntity)).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Com not found").build();
        }
    }

    @DELETE
    @Path("{idCom}")
    public Response delete(@PathParam("idCom") long idCom) {
        DataAccess dataAccess = DataAccess.begin();
        try {
            dataAccess.deleteCom(idCom);
            dataAccess.closeConnection(true);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Com not found").build();
        }
    }
}
