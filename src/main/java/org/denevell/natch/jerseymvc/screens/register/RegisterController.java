package org.denevell.natch.jerseymvc.screens.register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@Path("register")
public class RegisterController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
  @Context public HttpServletResponse mResponse;
	public String username;
	public String password;
	public String recoveryEmail;

  @GET
  @Template
  public Viewable index() throws Exception {
    return createTemplate(
        mRequest, 
        new RegisterPresenter(this).onGet(mRequest));
  }

  @POST
  @Template
  public Response indexPost(@Context UriInfo uriInfo,
      @FormParam("username") final String username,
      @FormParam("password") final String password,
      @FormParam("recovery_email") final String recoveryEmail
      ) throws Exception {

    this.username = username;
    this.password = password;
    this.recoveryEmail = recoveryEmail;
    return new RegisterPresenter(this).onPost(mRequest, mResponse);
  }
    
}