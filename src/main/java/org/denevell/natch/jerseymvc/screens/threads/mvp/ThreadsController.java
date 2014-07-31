package org.denevell.natch.jerseymvc.screens.threads.mvp;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsController extends TemplateController {
	
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

    @GET
    @Template
    public Viewable index(
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {
		this.start = start;
		this.limit = limit;
    	return createTemplate(
    			new ThreadsPresenter(this).onGet(mRequest)
    	);
	}

    @POST
    @Template
    public Response indexPost(
    		@Context UriInfo uriInfo,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit,
    		@FormParam("username") final String username,
    		@FormParam("password") final String password,
    		@FormParam("recovery_email") final String recoveryEmail,
    		@FormParam("subject") final String subject,
    		@FormParam("content") final String content,
    		@FormParam("tags") final String tags,
    		@FormParam("addthread_active") final String addthreadActive,
    		@FormParam("login_active") final String loginActive,
    		@FormParam("register_active") final String registerActive,
    		@FormParam("logout_active") final String logoutActive,
    		@FormParam("resetpw_active") final String resetPwActive,
    		@FormParam("resetpw_email") final String resetPwEmail
    		) throws Exception {

    	this.start = start;
		this.limit = limit;
		this.username = username;
		this.password = password;
		this.recoveryEmail = recoveryEmail;
		this.subject = subject;
		this.content = content;
		this.tags = tags;
		this.addthreadActive = addthreadActive;
		this.loginActive = loginActive;
		this.registerActive = registerActive;
		this.logoutActive = logoutActive;
		this.resetPwActive = resetPwActive;
		this.resetPwEmail = resetPwEmail;
		return new ThreadsPresenter(this).onPost(mRequest);
	}
    
}