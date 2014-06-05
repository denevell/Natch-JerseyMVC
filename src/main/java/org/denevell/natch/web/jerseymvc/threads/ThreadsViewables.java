package org.denevell.natch.web.jerseymvc.threads;

import org.denevell.natch.web.jerseymvc.threads.io.ListThreadsReturn;
import org.denevell.natch.web.jerseymvc.threads.io.LoginReturn;
import org.glassfish.jersey.server.mvc.Viewable;

public class ThreadsViewables {
	
	public static Viewable threads(
			final ListThreadsReturn threads, 
			final LoginReturn loggedIn) {
    	return new Viewable(
    			"/threads.mustache", 
    			new Object() {
    				@SuppressWarnings("unused")
					ListThreadsReturn threads() {
                            return threads;
                    }
    				@SuppressWarnings("unused")
					LoginReturn login() {
                            return loggedIn;
                    }
    			});
	}

}
