package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.denevell.natch.web.jerseymvc.threads.io.RegisterInput;
import org.denevell.natch.web.jerseymvc.threads.io.RegisterOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class RegisterModule {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();

	@SuppressWarnings("unused")
	public static String template(
			final HttpServletRequest request,
			final Object registerActive,
			final String username,
			final String password) throws IOException {

		Writer writer = new StringWriter();
		
		final RegisterOutput register = register(registerActive, username, password);

    	sFactory
    		.compile("register.mustache")
    		.execute(writer, 
    				new Object() {
						RegisterOutput register() {
							return register;
						}
						boolean loggedin() {
                            return request.getSession(true).getAttribute("loggedin")!=null;
						}
    				});
		writer.flush();
		return writer.toString();
	}

	public static RegisterOutput register(
			Object trueObject, 
			String username, 
			String password) {
		if(trueObject==null) return null;
		RegisterOutput ret = new RegisterOutput();
		try {
			ret = sService 
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
