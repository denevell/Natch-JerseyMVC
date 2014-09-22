package org.denevell.natch.jerseymvc.screens.login;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;

@Path("login")
public class LoginController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	public String username;
	public String password;

    @POST
    @Template
    public Response indexPost(
    		@Context UriInfo uriInfo,
    		@FormParam("username") final String username,
    		@FormParam("password") final String password
    		) throws Exception {

		this.username = username;
		this.password = password;
		return new LoginPresenter(this).onPost(mRequest);
	}
    
}