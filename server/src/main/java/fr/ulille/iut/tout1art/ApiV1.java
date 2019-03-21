package fr.ulille.iut.tout1art;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/v1/")
public class ApiV1 extends ResourceConfig {

    public ApiV1() {
        packages("fr.ulille.iut.tout1art.ressources");
        register(CORSFilter.class);

    }

}

