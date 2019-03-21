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

import fr.ulille.iut.tout1art.dao.ClientEntity;
import fr.ulille.iut.tout1art.dao.DataAccess;
import fr.ulille.iut.tout1art.dto.ClientCreateDto;
import fr.ulille.iut.tout1art.dto.ClientDto;

@Path("/client")
public class ClientRessource {
    private final static Logger logger = LoggerFactory.getLogger(ArtisanRessource.class);

    @Context
    public UriInfo uriInfo;

    public ClientRessource() {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ClientCreateDto clientDto) {
        DataAccess dataAccess = DataAccess.begin();

        // Construction (données primitives puis composition
        ClientEntity clientEntity = ClientEntity.convertFromClientCreateDto(clientDto);

        if (clientEntity.getNom() == null) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }

        try {
            long id = dataAccess.createClient(clientEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(ClientEntity.convertToDto(clientEntity)).location(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<ClientEntity> la = dataAccess.getAllClients();
        dataAccess.closeConnection(false);
        return la.stream().map(ClientEntity::convertToDto).collect(Collectors.toList());
    }

    
    @GET
    @Path("{idClient}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByIdClient(@PathParam("idClient") long idClient) {
        DataAccess dataAccess = DataAccess.begin();
        ClientEntity clientEntity = dataAccess.getClientById(idClient);
        if ( clientEntity != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(ClientEntity.convertToDto(clientEntity)).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Client not found").build();
        }
    }

    @DELETE
    @Path("{idClient}")
    public Response delete(@PathParam("idClient") long idClient) {
        DataAccess dataAccess = DataAccess.begin();
        try {
            dataAccess.deleteClient(idClient);
            dataAccess.closeConnection(true);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Client not found").build();
        }
    }
}
