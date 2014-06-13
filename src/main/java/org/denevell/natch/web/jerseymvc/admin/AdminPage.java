package org.denevell.natch.web.jerseymvc.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.denevell.natch.web.jerseymvc.admin.modules.AdminModule;
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

    @SuppressWarnings("serial")
	private Viewable createView() throws Exception {
		return new Viewable("/admin_index", 
				new HashMap<String, String>() {{
					put("users", mAdmin.template(mRequest));
				}});
	}
}