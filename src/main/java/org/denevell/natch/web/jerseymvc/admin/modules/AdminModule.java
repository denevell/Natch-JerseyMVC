package org.denevell.natch.web.jerseymvc.admin.modules;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.admin.io.UserListOutput;

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
