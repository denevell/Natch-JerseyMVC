package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.apache.log4j.Logger;
import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.threads.io.LoginInput;
import org.denevell.natch.web.jerseymvc.threads.io.LoginOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class LoginLogoutModule {
	
	private static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

	public static String template(
			final HttpServletRequest request,
			final Object loginActive,
			final Object logoutActive,
			final String username,
			final String password) throws IOException {

		Writer writer = new StringWriter();
		
		logout(logoutActive, request);
		final LoginOutput login = login(loginActive, request, username, password);

   		sFactory
    		.compile("login.mustache")
    		.execute(writer, 
    				new Object() {
    				@SuppressWarnings("unused")
					LoginOutput login() {
							return login;
                    }
    				@SuppressWarnings("unused")
					boolean loggedin() {
                            return request.getSession(true).getAttribute("loggedin")!=null;
                    }
    		});
		writer.flush();
		return writer.toString();
	}

	public static LoginOutput login(
			Object trueObject, 
			HttpServletRequest request, 
			String username, 
			String password) {
		if(trueObject==null) return null;
		LoginOutput ret = new LoginOutput();
		try {
			ret = sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("login")
				.request()
				.post(Entity.json(new LoginInput(username, password)),
						LoginOutput.class);
			if(ret.getAuthKey()!=null && ret.getAuthKey().trim().length()>0) {
				request.getSession(true).setAttribute("loggedin", true);
			}
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

	public static void logout(
			Object trueObject, 
			HttpServletRequest request
			) {
		if(trueObject==null) return;
		try {
			sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("logout")
				.request()
				.delete();
			request.getSession(true).setAttribute("loggedin", null);
		} catch (Exception e) {
			Logger.getLogger(LoginLogoutModule.class).error("Whoops... erm...", e);
			request.getSession(true).setAttribute("loggedin", null);
		}
	}

}
