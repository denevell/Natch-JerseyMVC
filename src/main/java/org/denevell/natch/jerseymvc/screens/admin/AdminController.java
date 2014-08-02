package org.denevell.natch.jerseymvc.screens.admin;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("admin")
public class AdminController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	private String changePwUsername;
	private String changePwNewPass;
	private String changePwActive;

    @GET
    @Template
    public Viewable index() throws Exception {
    	return createTemplate(new AdminPresenter(this).onGet(mRequest));
    }

    @POST
    @Template
    public Response post(
    		@FormParam("changepw_username") String changePwUsername,
    		@FormParam("changepw_password") String changePwNewPass,
    		@FormParam("changepw_active") String changePwActive
    		) throws Exception {
		this.changePwUsername = changePwUsername;
		this.changePwNewPass = changePwNewPass;
		this.changePwActive = changePwActive;
		return new AdminPresenter(this).onPost(mRequest);
    }
    
    public String getChangePwActive() {
		return changePwActive;
	}
    
    public String getChangePwUsername() {
		return changePwUsername;
	}
    
    public String getChangePwNewPass() {
		return changePwNewPass;
	}
}