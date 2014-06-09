package org.denevell.natch.web.jerseymvc.pages;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.web.jerseymvc.modules.LoginLogoutModule;
import org.denevell.natch.web.jerseymvc.modules.RegisterModule;
import org.denevell.natch.web.jerseymvc.modules.ThreadsModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsPage {
	
	@Context HttpServletRequest mRequest;

    @GET
    @Path("{start}/{limit}")
    @Template
    public Viewable index(
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit
    		) throws IOException {

    	return new ThreadsView()
    			.loginLogout(LoginLogoutModule.template(mRequest, null, null, null, null))
    			.threads(ThreadsModule.template(mRequest, start, limit))
    			.register(RegisterModule.template(mRequest, null, null, null))
    			.create();
	}

    @POST
    @Path("{start}/{limit}")
    @Template
    public Viewable indexPost(
    		@Context UriInfo uriInfo,
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit,
    		@FormParam("username") final String username,
    		@FormParam("password") final String password,
    		@FormParam("login_active") final String loginActive,
    		@FormParam("register_active") final String registerActive,
    		@FormParam("logout_active") final String logoutActive 
    		) throws IOException {
    	
    	return new ThreadsView()
    			.loginLogout(LoginLogoutModule.template(mRequest, loginActive, logoutActive, username, password))
    			.threads(ThreadsModule.template(mRequest, start, limit))
    			.register(RegisterModule.template(mRequest, registerActive, username, password))
    			.create();
	}
}