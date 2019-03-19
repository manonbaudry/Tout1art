package fr.ulille.iut.pizzaland;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v1/")
public class ApiV1 extends ResourceConfig {

    public ApiV1() {
        packages("fr.ulille.iut.pizzaland.ressources");
        register(CORSFilter.class);

    }

}

