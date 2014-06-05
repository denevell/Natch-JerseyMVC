package org.denevell.natch.web.jerseymvc.threads;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.denevell.natch.web.jerseymvc.threads.io.ThreadsOutput;
import org.denevell.natch.web.jerseymvc.threads.io.LoginOutput;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsServ {

    @GET
    @Template
    @Path("/{start}/{limit}")
    public Viewable list(
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit) {
    	return view(
    			ThreadsDao.getThreads(start, limit),
    			null
    	);
	}

    @POST
    @Template
    @Path("/{start}/{limit}")
    public Viewable login(
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit,
    		@FormParam("username") String username,
    		@FormParam("password") String password) {
    	return view(
    			ThreadsDao.getThreads(start, limit),
    			ThreadsDao.login(username, password)
    	);
    }

	public static Viewable view(final ThreadsOutput threads, 
								final LoginOutput loggedIn) {
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
    			});
	}

}