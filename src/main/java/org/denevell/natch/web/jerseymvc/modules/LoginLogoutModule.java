package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.apache.log4j.Logger;
import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.io.LoginInput;
import org.denevell.natch.web.jerseymvc.io.LoginOutput;

import static org.denevell.natch.web.jerseymvc.Serv.serv;

public class LoginLogoutModule extends TemplateModule {
	
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

	public void login(final Object trueObject, 
			final HttpServletRequest request, 
			final String username, 
			final String password) {
		if(trueObject==null) return;
		serv(new Runnable() { @Override public void run() {
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
			}}})
		._403(new Runnable() { @Override public void run() {
			mLogin.setErrorMessage(Strings.getLogonError());
			}})
		._400(new Runnable() { @Override public void run() {
			mLogin.setErrorMessage(Strings.getBlankUsernameOrPassword());
			}})
		._exception(new Runnable() { @Override public void run() {
			mLogin.setErrorMessage("Whoops... erm...");
			}});
	}

	public void logout( Object trueObject, final HttpServletRequest request) {
		if(trueObject==null) return;
		serv(new Runnable() { @Override public void run() {
			sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest") .path("user") .path("logout")
				.request()
				.delete();
			request.getSession(true).setAttribute("loggedin", null);
			request.getSession(true).setAttribute("admin", null);
			}})
		._exception(new Runnable() { @Override public void run() {
			Logger.getLogger(LoginLogoutModule.class).error("Whoops... erm...");
			request.getSession(true).setAttribute("loggedin", null);
			request.getSession(true).setAttribute("admin", null);
			}
		});
	}

}
