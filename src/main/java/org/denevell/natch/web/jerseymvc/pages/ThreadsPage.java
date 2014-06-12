package org.denevell.natch.web.jerseymvc.pages;

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
	RegisterModule mRegister = new RegisterModule();
	LoginLogoutModule mLogin = new LoginLogoutModule();

    @GET
    @Path("{start}/{limit}")
    @Template
    public Viewable index(
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit
    		) throws Exception {

    	return createView(start, limit); 
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
    		@FormParam("recovery_email") final String recoveryEmail,
    		@FormParam("login_active") final String loginActive,
    		@FormParam("register_active") final String registerActive,
    		@FormParam("logout_active") final String logoutActive 
    		) throws Exception {
    	
    	mLogin.logout(logoutActive, mRequest);
    	mRegister.register(registerActive, mRequest, username, password, recoveryEmail);
    	mLogin.login(loginActive, mRequest, username, password);

    	return createView(start, limit);
	}
    
    private Viewable createView(int start, int limit) throws Exception {
    	return new ThreadsView()
    			.register(mRegister.template(mRequest))
    			.loginLogout(mLogin.template(mRequest))
    			.threads(new ThreadsModule().template(mRequest, start, limit))
    			.create();

	}
}