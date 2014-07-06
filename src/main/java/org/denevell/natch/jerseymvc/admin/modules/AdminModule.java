package org.denevell.natch.jerseymvc.admin.modules;

import org.denevell.natch.jerseymvc.admin.io.UserListOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;

public class AdminModule extends TemplateModule {
	
   	private UserListOutput mUsers;

   	public AdminModule() {
   		super("admin_users.mustache");
	}
   	
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