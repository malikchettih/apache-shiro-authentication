package com.targa.dev.shirotutorial.security.boudary;

import com.targa.dev.shirotutorial.security.boudary.AuthenticationResource;
import com.targa.dev.shirotutorial.security.boudary.UserResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class SecurityApplication extends ResourceConfig {
    
    public SecurityApplication(){
        
        register(AuthenticationResource.class);
        register(UserResource.class);
        
        packages("com.targa.dev.shirotutorial");
    }
}
