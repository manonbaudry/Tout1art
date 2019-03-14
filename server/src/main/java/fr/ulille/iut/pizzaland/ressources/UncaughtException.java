package fr.ulille.iut.pizzaland.ressources;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

@Provider
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable> {
    final static Logger logger = LoggerFactory.getLogger(UncaughtException.class);

	private static final long serialVersionUID = 1L;

	@Override
	public Response toResponse(Throwable exception) throws NotFoundException{
		if (exception instanceof NotFoundException) {
			throw (NotFoundException) exception;
		}
		logger.error(MarkerFactory.getMarker("FATAL"), "Exception thrown", exception);
		return Response.status(500).entity("Exception thrown: " + exception.getStackTrace()[0] + "see log.").type("text/plain").build();
	}

}
