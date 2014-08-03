package org.denevell.natch.jerseymvc.app.services;

import org.denevell.natch.jerseymvc.app.models.UserListOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateName;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

@TemplateName("admin_users.mustache")
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
