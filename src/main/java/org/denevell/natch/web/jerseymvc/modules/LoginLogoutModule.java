package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;

import org.apache.log4j.Logger;
import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.io.LoginInput;
import org.denevell.natch.web.jerseymvc.io.LoginOutput;

public class LoginLogoutModule extends Module {
	
	private LoginOutput mLogin = new LoginOutput();
	
	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException {
		return createTemplate("login.mustache", 
			new Object() {
				LoginOutput login = mLogin;
				boolean loggedin = request.getSession(true).getAttribute("loggedin")!=null;
				boolean isadmin = request.getSession(true).getAttribute("admin")!=null;
    		}
		);
	}

	public void login(
			Object trueObject, 
			HttpServletRequest request, 
			String username, 
			String password) {
		if(trueObject==null) return;
		try {
			mLogin = sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("login")
				.request()
				.post(Entity.json(new LoginInput(username, password)), LoginOutput.class);
			if(mLogin.getAuthKey()!=null && mLogin.getAuthKey().trim().length()>0) {
				request.getSession(true).setAttribute("loggedin", true);
				request.getSession(true).setAttribute("authkey", mLogin.getAuthKey());
			}
			if(mLogin.isAdmin()) {
				request.getSession(true).setAttribute("admin", true);
			}
		} catch (WebApplicationException e) {
			if(e.getResponse().getStatus()==403) {
				mLogin.setErrorMessage(Strings.getLogonError());
			} else if(e.getResponse().getStatus()==400) {
				mLogin.setErrorMessage(Strings.getBlankUsernameOrPassword());
			} else {
				mLogin.setErrorMessage("Whoops... erm...");
			}
		} catch (Exception e) {
			mLogin.setErrorMessage("Whoops... erm...");
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
