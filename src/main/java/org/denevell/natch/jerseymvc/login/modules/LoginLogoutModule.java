package org.denevell.natch.jerseymvc.login.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.apache.log4j.Logger;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.login.io.LoginInput;
import org.denevell.natch.jerseymvc.login.io.LoginOutput;

public class LoginLogoutModule extends TemplateModule {
	
	private LoginOutput mLogin = new LoginOutput();
	
	public LoginLogoutModule() {
		super("login.mustache");
	}
	
	public LoginOutput getLogin() {
		return mLogin;
	}
	
	public boolean getLoggedin() {
		return mRequest.getSession(true).getAttribute("loggedin")!=null;
	}
	
	public boolean getIsadmin() {
		return mRequest.getSession(true).getAttribute("admin")!=null;
	}

	public boolean errord() {
		return mLogin.getErrorMessage()!=null && mLogin.getErrorMessage().trim().length()>0;
	}
	
	public boolean login(final Object trueObject, 
			final HttpServletRequest request, 
			final String username, 
			final String password) {
		if(trueObject==null) return true;
		return serv(new Runnable() { @Override public void run() {
			mLogin = sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("login")
				.request()
				.post(Entity.json(new LoginInput(username, password)), LoginOutput.class);
			if(mLogin.getAuthKey()!=null && mLogin.getAuthKey().trim().length()>0) {
				request.getSession(true).setAttribute("loggedin", true);
				request.getSession(true).setAttribute("authkey", mLogin.getAuthKey());
				request.getSession(true).setAttribute("name", username);
			}
			if(mLogin.isAdmin()) {
				request.getSession(true).setAttribute("admin", true);
			}}})
		._403(new Runnable() { @Override public void run() {
			mLogin.setErrorMessage(Strings.getLogonError());
			}})
		._400(new Runnable() { @Override public void run() {
			mLogin.setErrorMessage(Strings.getBlankUsernameOrPassword());
			}})
		._exception(new Runnable() { @Override public void run() {
			mLogin.setErrorMessage("Whoops... erm...");
			}}).go();
	}

	public boolean logout( Object trueObject, final HttpServletRequest request) {
		if(trueObject==null) return true;
		return serv(new Runnable() { @Override public void run() {
			sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("logout")
				.request()
				.delete();
			request.getSession(true).setAttribute("loggedin", null);
			request.getSession(true).setAttribute("admin", null);
			request.getSession(true).setAttribute("name", null);
			request.getSession(true).setAttribute("authkey", null);
			}})
		._exception(new Runnable() { @Override public void run() {
			Logger.getLogger(LoginLogoutModule.class).error("Whoops... erm...");
			request.getSession(true).setAttribute("loggedin", null);
			request.getSession(true).setAttribute("authkey", null);
			request.getSession(true).setAttribute("name", null);
			request.getSession(true).setAttribute("admin", null);
			}
		}).go();
	}

}
