package fr.ulille.iut.pizzaland;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String GENERIC_URI = "http://0.0.0.0:%d/api/v1/";
    private static final String STATIC_URI = "http://0.0.0.0:%d/";
    private static final int DEFAULT_PORT = 8080;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @param port Port to be used. Needed to prevent race conditions during // tests
     * @return the started server
     */
    public static HttpServer startServer(int port) {
        // create a resource config that scans for JAX-RS resources and providers
        // in fr.ulille.iut.pizzaland package
        final ResourceConfig rc = new ResourceConfig().packages("fr.ulille.iut.pizzaland.ressources");
		rc.register(new CORSFilter());
        // Database initialisation (if any) to be settled here

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at GENERIC_URI
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(getURI(port, false)), rc);

        // Add a static file server (serving jar files)
        CLStaticHttpHandler staticHttpHandler = new CLStaticHttpHandler(Main.class.getClassLoader());
        staticHttpHandler.addDocRoot("static/");
        httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");

        return httpServer;
    }

    public static HttpServer startServer() {
        return startServer(DEFAULT_PORT);
    }

    /**
     * Main method.
     * @param args none
     * @throws IOException error during read
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", getURI()));
        System.in.read();
        server.shutdownNow();
    }

    // offer default values for port & static/nonstatic
    public static String getURI() {
        return getURI(DEFAULT_PORT, false);
    }

    public static String getURI(int port) {
        return getURI(DEFAULT_PORT, false);
    }

    public static String getURI(boolean staticUri) {
        return getURI(DEFAULT_PORT, staticUri);
    }

    // full parametrized version
    public static String getURI(int port, boolean staticUri) {
        // Compute URI from port
        //  Patch to allow to run different servers on // (for test purpose)
        if (staticUri) {
            return String.format(STATIC_URI, port);
        } else {
            return String.format(GENERIC_URI, port);
        }
    }
}

