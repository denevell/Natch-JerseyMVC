package org.denevell.natch.web.jerseymvc.threads;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.denevell.natch.web.jerseymvc.threads.modules.AddThreadModule;
import org.denevell.natch.web.jerseymvc.threads.modules.ThreadsModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsPage {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	RegisterModule mRegister = new RegisterModule();
	LoginLogoutModule mLogin = new LoginLogoutModule();
	AddThreadModule mAddThread = new AddThreadModule();

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
    		@FormParam("subject") final String subject,
    		@FormParam("content") final String content,
    		@FormParam("tags") final String tags,
    		@FormParam("addthread_active") final String addthreadActive,
    		@FormParam("login_active") final String loginActive,
    		@FormParam("register_active") final String registerActive,
    		@FormParam("logout_active") final String logoutActive 
    		) throws Exception {
    	
    	boolean error = false;
    	error = !(mLogin.logout(logoutActive, mRequest));
    	error = !(mRegister.register(registerActive, mRequest, username, password, recoveryEmail));
    	error = !(mLogin.login(loginActive, mRequest, username, password));
    	error = !(mAddThread.add(addthreadActive, mRequest, subject, content, tags));
    	if(error) {
    		return createView(start, limit);
    	} else {
    		mResponse.sendRedirect(mRequest.getRequestURI());
    		return null;
    	}
	}
    
    @SuppressWarnings("serial")
	private Viewable createView(final int start, final int limit) throws Exception {
		return new Viewable("/threads_index.mustache", 
				new HashMap<String, String>() {{
					put("login", mLogin.template(mRequest));
					put("register", mRegister.template(mRequest));
					put("addthread", mAddThread.template(mRequest));
					put("threads", new ThreadsModule().template(mRequest, start, limit));
				}});

	}
}