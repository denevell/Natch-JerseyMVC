package org.denevell.natch.jerseymvc.services;

import org.denevell.natch.jerseymvc.models.UserListOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class AdminService {
	
   	private UserListOutput mUsers;
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

   	public UserListOutput getUsers() {
   		return mUsers;
   	}
	
	public void getUsers(String authKey) {
        mUsers = sService
                .target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
                .path("rest").path("user").path("list")
                .request()
                .header("AuthKey", authKey)
                .get(UserListOutput.class); 	
	}
	

}
