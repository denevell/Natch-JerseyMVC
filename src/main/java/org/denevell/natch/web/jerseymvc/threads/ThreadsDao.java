package org.denevell.natch.web.jerseymvc.threads;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.threads.io.RegisterInput;
import org.denevell.natch.web.jerseymvc.threads.io.RegisterOutput;
import org.denevell.natch.web.jerseymvc.threads.io.ThreadsOutput;
import org.denevell.natch.web.jerseymvc.threads.io.LoginInput;
import org.denevell.natch.web.jerseymvc.threads.io.LoginOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadsDao {
	
	private static JerseyClient service = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	
	public static ThreadsOutput getThreads(int start, int limit) {
        return service
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
	}
	
	public static LoginOutput login(String username, String password) {
		LoginOutput ret = new LoginOutput();
		try {
			ret = service 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("login")
				.request()
				.post(Entity.json(new LoginInput(username, password)),
						LoginOutput.class);
		} catch (WebApplicationException e) {
			System.out.println(e.getResponse().getStatus());
			if(e.getResponse().getStatus()==403) {
				ret.setErrorMessage("Username or password incorrect");
			} else if(e.getResponse().getStatus()==400) {
				ret.setErrorMessage(Strings.getBlankUsernameOrPassword());
			} else {
				ret.setErrorMessage("Whoops... erm...");
			}
		} catch (Exception e) {
			ret.setErrorMessage("Whoops... erm...");
		}
		return ret;
	}

	public static RegisterOutput register(String username, String password) {
		RegisterOutput ret = new RegisterOutput();
		try {
			ret = service 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") 
				.request()
				.put(Entity.json(new RegisterInput(username, password)),
						RegisterOutput.class);
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
