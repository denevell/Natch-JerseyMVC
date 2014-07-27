package org.denevell.natch.jerseymvc.screens.admin.modules;

import org.denevell.natch.jerseymvc.app.models.UserListOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("admin_users.mustache")
public class AdminModule extends TemplateModule {
	
   	private UserListOutput mUsers;

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
