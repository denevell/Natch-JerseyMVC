package org.denevell.natch.web.jerseymvc.threads;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.denevell.natch.web.jerseymvc.threads.io.LoginOutput;
import org.denevell.natch.web.jerseymvc.threads.io.RegisterOutput;
import org.denevell.natch.web.jerseymvc.threads.io.ThreadsOutput;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsServ {
	
	@Context HttpServletRequest mRequest;

    @GET
    @Path("{start}/{limit}")
    @Template
    public Viewable index(
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit,
    		@QueryParam("username") String username,
    		@QueryParam("password") String password 
    		) {
    	
    	return view(
    			ThreadsDao.getThreads(start, limit),
    			null,
    			null
    	);
	}

    @POST
    @Path("{start}/{limit}")
    @Template
    public Viewable indexPost(
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit,
    		@FormParam("username") String username,
    		@FormParam("password") String password,
    		@FormParam("login_active") String loginActive,
    		@FormParam("register_active") String registerActive
    		) {
    	
    	LoginOutput login = null;
    	RegisterOutput register = null;
		if(registerActive!=null) {
    		register = ThreadsDao.register(username, password);
    	} else if(loginActive!=null) {
    		login = ThreadsDao.login(username, password);
    	}
    	
    	return view(
    			ThreadsDao.getThreads(start, limit),
    			login,
    			register
    	);
	}

	public static Viewable view(final ThreadsOutput threads, 
								final LoginOutput loggedIn,
								final RegisterOutput register) {
    	return new Viewable(
    			"/threads.mustache", 
    			new Object() {
    				@SuppressWarnings("unused")
					ThreadsOutput threads() {
                            return threads;
                    }
    				@SuppressWarnings("unused")
					LoginOutput login() {
                            return loggedIn;
                    }
    				@SuppressWarnings("unused")
					RegisterOutput register() {
                            return register;
                    }
    			});
	}

}