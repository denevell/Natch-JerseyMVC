package org.denevell.natch.web.jerseymvc.threads;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.denevell.natch.web.jerseymvc.threads.io.ListThreadsReturn;
import org.denevell.natch.web.jerseymvc.threads.io.LoginInput;
import org.denevell.natch.web.jerseymvc.threads.io.LoginReturn;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadsDao {
	
	private static JerseyClient service = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	
	public static ListThreadsReturn getThreads(int start, int limit) {

        return service
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ListThreadsReturn.class); 	

	}
	
	public static LoginReturn login(String username, String password) {
		LoginReturn ret = new LoginReturn();
		try {
			ret = service 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("login")
				.request()
				.post(Entity.json(new LoginInput(username, password)),
						LoginReturn.class);
		} catch (WebApplicationException e) {
			if(e.getResponse().getStatus()==403) {
				ret.setErrorMessage("Username or password incorrect");
			} else {
				ret.setErrorMessage("Whoops... erm...");
			}
		} catch (Exception e) {
			ret.setErrorMessage("Whoops... erm...");
		}
		return ret;
	}

}
