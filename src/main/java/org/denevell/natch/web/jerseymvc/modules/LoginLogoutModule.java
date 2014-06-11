package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.apache.log4j.Logger;
import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.io.LoginInput;
import org.denevell.natch.web.jerseymvc.io.LoginOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class LoginLogoutModule {
	
	private static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private LoginOutput mLogin;

	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException {

		Writer writer = new StringWriter();

   		sFactory
    		.compile("login.mustache")
    		.execute(writer, 
    				new Object() {
					LoginOutput login() {
							return mLogin;
                    }
					boolean loggedin() {
                            return request.getSession(true).getAttribute("loggedin")!=null;
                    }
					boolean isadmin() {
                            return request.getSession(true).getAttribute("admin")!=null;
                    }
    		});
		writer.flush();
		return writer.toString();
	}

	public void login(
			Object trueObject, 
			HttpServletRequest request, 
			String username, 
			String password) {
		if(trueObject==null) return;
		try {
			LoginOutput ret = sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("login")
				.request()
				.post(Entity.json(new LoginInput(username, password)),
						LoginOutput.class);
			if(ret.getAuthKey()!=null && ret.getAuthKey().trim().length()>0) {
				request.getSession(true).setAttribute("loggedin", true);
				request.getSession(true).setAttribute("authkey", ret.getAuthKey());
			}
			if(ret.isAdmin()) {
				request.getSession(true).setAttribute("admin", true);
			}
			mLogin = ret;
		} catch (WebApplicationException e) {
			LoginOutput ret = new LoginOutput();
			System.out.println(e.getResponse().getStatus());
			if(e.getResponse().getStatus()==403) {
				ret.setErrorMessage(Strings.getLogonError());
			} else if(e.getResponse().getStatus()==400) {
				ret.setErrorMessage(Strings.getBlankUsernameOrPassword());
			} else {
				ret.setErrorMessage("Whoops... erm...");
			}
			mLogin = ret;
		} catch (Exception e) {
			LoginOutput ret = new LoginOutput();
			ret.setErrorMessage("Whoops... erm...");
			mLogin = ret;
		}
	}

	public void logout(
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
			request.getSession(true).setAttribute("admin", null);
		} catch (Exception e) {
			Logger.getLogger(LoginLogoutModule.class).error("Whoops... erm...", e);
			request.getSession(true).setAttribute("loggedin", null);
		}
	}

}
