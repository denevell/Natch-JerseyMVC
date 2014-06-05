package org.denevell.natch.web.jerseymvc;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;

public class JerseyApplication extends ResourceConfig {
    public JerseyApplication() {
       register(MvcFeature.class);
       register(org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature.class);
       register(JacksonFeature.class);
       register(new DependencyBinder());
    }
}
