package org.denevell.natch.jerseymvc.screens.register;

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

@Path("index")
public class RegisterController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	public int start;
	public int limit;
	public String username;
	public String password;
	public String recoveryEmail;
	public String subject;
	public String content;
	public String tags;
	public String addthreadActive;
	public String loginActive;
	public String registerActive;
	public String logoutActive;
	public String resetPwActive;
	public String resetPwEmail;
  public String tag;

  @GET
  @Template
  public Viewable index() throws Exception {
    return createTemplate(new RegisterPresenter(this).onGet(mRequest));
  }

  @POST
  @Template
  public Response indexPost(@Context UriInfo uriInfo,
      @FormParam("username") final String username,
      @FormParam("password") final String password,
      @FormParam("recovery_email") final String recoveryEmail,
      @FormParam("register_active") final String registerActive) throws Exception {

    this.username = username;
    this.password = password;
    this.registerActive = registerActive;
    return new RegisterPresenter(this).onPost(mRequest);
  }
    
}