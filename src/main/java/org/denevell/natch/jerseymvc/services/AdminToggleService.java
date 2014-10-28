package org.denevell.natch.jerseymvc.services;

import org.denevell.natch.jerseymvc.models.SuccessOrError;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class AdminToggleService {
	
   	private SuccessOrError mSuccessOrError;
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

   	public SuccessOrError getResult() {
   		return mSuccessOrError;
   	}
	
	public void toggle(Object active, String authKey, String username) {
		if(active==null) return;
        mSuccessOrError = sService
                .target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
                .path("rest").path("user").path("admin").path("toggle").path(username)
                .request()
                .header("AuthKey", authKey)
                .post(null, SuccessOrError.class); 	
	}

}
