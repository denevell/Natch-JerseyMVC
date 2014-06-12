package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.io.UserList;

public class AdminModule extends TemplateModule {
	
   	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException { 
		final UserList usersList = getUsers((String) request.getSession(true).getAttribute("authkey"));
        return createTemplate("admin_users.mustache", 
        	new Object() {
            	UserList users = usersList;
            }
        );
	}
	
	private UserList getUsers(String authKey) {
        return sService
                .target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
                .path("rest").path("user").path("list")
                .request()
                .header("AuthKey", authKey)
                .get(UserList.class); 	
	}
	

}
