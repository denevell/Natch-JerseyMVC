package org.denevell.natch.web.jerseymvc.admin.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.admin.io.UserListOutput;

public class AdminModule extends TemplateModule {
	
   	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException { 
		final UserListOutput usersList = getUsers((String) request.getSession(true).getAttribute("authkey"));
        return createTemplate("admin_users.mustache", 
        	new Object() {
            	UserListOutput users = usersList;
            }
        );
	}
	
	private UserListOutput getUsers(String authKey) {
        return sService
                .target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
                .path("rest").path("user").path("list")
                .request()
                .header("AuthKey", authKey)
                .get(UserListOutput.class); 	
	}
	

}
