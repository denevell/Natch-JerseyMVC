package org.denevell.natch.jerseymvc.screens.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;

@Path("login")
public class LoginController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context public HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	public String username;
	public String password;
  public String redirect;

    @POST
    @Path("")
    @Template
    public Response indexPost(
    		@Context UriInfo uriInfo,
    		@FormParam("username") final String username,
    		@FormParam("password") final String password,
    		@QueryParam("redirect") final String redirect 
    		) throws Exception {
      
    this.redirect = redirect;
		this.username = username;
		this.password = password;
		return new LoginPresenter(this).onPost(mRequest, null);
	}
    
}