package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.threads.io.RegisterInput;
import org.denevell.natch.web.jerseymvc.threads.io.RegisterOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class RegisterModule {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
	private RegisterOutput mRegister;

	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException {

		Writer writer = new StringWriter();
		
    	sFactory
    		.compile("register.mustache")
    		.execute(writer, 
    				new Object() {
						RegisterOutput register() {
							return mRegister;
						}
						boolean loggedin() {
							return request.getSession(true).getAttribute("loggedin")!=null;
    					}
    				});
		writer.flush();
		return writer.toString();
	}

	public void register(
			Object trueObject, 
			HttpServletRequest serv,
			String username, 
			String password) {
		if(trueObject==null) return;
		try {
			RegisterOutput ret = sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") 
				.request()
				.put(Entity.json(new RegisterInput(username, password)),
						RegisterOutput.class);
			if(ret.getError()!=null && ret.getError().trim().length()>0) {
				ret.setErrorMessage(ret.getError()); // Hack...
			} else {
				new LoginLogoutModule().login(new Object(), serv, username, password);
			}
			mRegister = ret;
		} catch (WebApplicationException e) {
			RegisterOutput ret = new RegisterOutput();
			if(e.getResponse().getStatus()==403) {
				ret.setErrorMessage("Username or password incorrect");
			} else {
				ret.setErrorMessage(Strings.getBlankUsernameOrPassword());
			}
			mRegister = ret;
		} catch (Exception e) {
			RegisterOutput ret = new RegisterOutput();
			ret.setErrorMessage("Whoops... erm...");
			mRegister = ret;
		}
	}


}
