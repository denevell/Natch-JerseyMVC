package org.denevell.natch.web.jerseymvc.pages;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.denevell.natch.web.jerseymvc.modules.AdminModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("admin")
public class AdminPage {
	
	@Context HttpServletRequest mRequest;
	private AdminModule mAdmin = new AdminModule();

    @GET
    @Template
    public Viewable index() throws Exception {
    	return createView(); 
    }

    private Viewable createView() throws Exception {
    	return new AdminView()
    			.users(mAdmin.template(mRequest))
    			.create();
	}
}