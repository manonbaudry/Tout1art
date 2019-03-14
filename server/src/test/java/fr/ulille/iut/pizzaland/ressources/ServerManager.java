package fr.ulille.iut.pizzaland.ressources;

import fr.ulille.iut.pizzaland.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.ConcurrentHashMap;

// In order to discard race conditions between two tests from the same class, we have to buid a new targer for each Thead
// That implies:
// a distinct port and a distinct server (each server has its own database)
// To be able to retrieve the right server within the test, we propose a data structure indexed by the current Thread itself

class ServerManager {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(ServerManager.class);
    private  final static int STARTING_PORT = 63000;
    private final static ServerManager singleton = new ServerManager(STARTING_PORT);

    private int nextPort;
    private ConcurrentHashMap<Thread, WebTarget> targets;
    private ConcurrentHashMap<Thread, HttpServer> servers;

    public static ServerManager getSingleton() {
        return singleton;
    }

    private ServerManager(int portStart) {
        nextPort = portStart;
        targets = new ConcurrentHashMap<>();
        servers = new ConcurrentHashMap<>();
    }

    public WebTarget getWebTarget() {
        return getWebTarget(false);
    }

    public WebTarget getWebTarget(boolean staticUri) {
        Thread currentThread = Thread.currentThread();
        WebTarget target;
        int currentPort;
        target = targets.get(currentThread);
        if (target == null) { // The server has not yet been created for the current thread.. Let's create it
            synchronized(this.getClass()) {  // Prevent race condition between tests (for port number)
                currentPort = nextPort++;
            }
            logger.debug("Starting server port : " + currentPort);
            HttpServer server = Main.startServer(currentPort);
            Client client = ClientBuilder.newClient();
            System.out.println(Main.getURI(currentPort, staticUri));
            target = client.target(Main.getURI(currentPort, staticUri));
            targets.put(currentThread, target);
            servers.put(currentThread, server);
            try {       // Wait for server to settle
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // At this point the server/webTarget is avalaible, so return it
        return target;
    }

    public void shutdown() {
        for (HttpServer server : servers.values()) {
            server.shutdownNow();
        }
    }
}

