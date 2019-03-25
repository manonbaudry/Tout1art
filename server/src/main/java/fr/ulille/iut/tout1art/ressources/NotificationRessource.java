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
import fr.ulille.iut.tout1art.dao.NotificationEntity;
import fr.ulille.iut.tout1art.dto.NotificationCreateDto;
import fr.ulille.iut.tout1art.dto.NotificationDto;

@Path("/notification")
public class NotificationRessource {
    private final static Logger logger = LoggerFactory.getLogger(ComRessource.class);

    @Context
    public UriInfo uriInfo;

    public NotificationRessource() {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(NotificationCreateDto notifDto) {
        DataAccess dataAccess = DataAccess.begin();

        // Construction (données primitives puis composition
        NotificationEntity notifEntity = NotificationEntity.convertFromNotificationCreateDto(notifDto);

        if (notifEntity == null) {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_ACCEPTABLE).entity("name not specified").build();
        }
        try {
            long id = dataAccess.createNotification(notifEntity);
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path("" + id).build();
            dataAccess.closeConnection(true);
            return Response.created(instanceURI).status(201).entity(NotificationEntity.convertToDto(notifEntity)).location(instanceURI).build();
        }
        catch ( Exception ex ) {
            dataAccess.closeConnection(false);
            return Response.status(Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NotificationDto> getAll() {
        DataAccess dataAccess = DataAccess.begin();
        List<NotificationEntity> la = dataAccess.getAllNotifications();
        dataAccess.closeConnection(false);
        return la.stream().map(NotificationEntity::convertToDto).collect(Collectors.toList());
    }
  /*
    @GET
    @Path("{concerne}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NotificationDto> getNotificationByConcerne(@PathParam("concerne") String concerne) {
        DataAccess dataAccess = DataAccess.begin();
        List<NotificationEntity> la = dataAccess.getNotifByConcerne(concerne);
        dataAccess.closeConnection(false);
        return la.stream().map(NotificationEntity::convertToDto).collect(Collectors.toList());
    }
*/
    /*
    @GET
    @Path("{idCom}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotifByConcerne(@PathParam("concerne") String concerne) {
        DataAccess dataAccess = DataAccess.begin();
        NotificationEntity notifEntity = dataAccess.getNotifByConcerne(concerne);
        if ( notifEntity != null ) {
            dataAccess.closeConnection(true);
            return Response.ok(notifEntity.convertToDto(notifEntity)).build();
        } else {
            dataAccess.closeConnection(false);
            return Response.status(Status.NOT_FOUND).entity("Com not found").build();
        }
    }*/

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
    
    
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, /*@QueryParam("statut") String statut*/ ComEntity commande) {
        DataAccess dataAccess = DataAccess.begin();
        ComEntity commandeBDD = dataAccess.getComById(id);
        if (commandeBDD == null) {
            return Response.status(Status.NOT_FOUND).entity("Produit not found").build();
        } else {
            try {
                commandeBDD.setStatut(commande.getStatut());
                dataAccess.updateCommande(commandeBDD);
                dataAccess.closeConnection(true);
                return Response.ok(commandeBDD).build(); //  .created(instanceURI).build();
            } catch (Exception ex) {
                dataAccess.closeConnection(false);
                return Response.status(Status.CONFLICT).entity("Duplicated name").build();
            }
        }
    }
}
