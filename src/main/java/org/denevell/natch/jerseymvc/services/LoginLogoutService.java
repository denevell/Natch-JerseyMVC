package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.denevell.natch.jerseymvc.services.ServiceInputs.LoginInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.LoginOutput;
import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.Urls;

public class LoginLogoutService {
	
	private LoginOutput mLogin = new LoginOutput();
	private static int ONE_YEAR = 60*60*24*365;
	public LoginOutput getLogin() {
		return mLogin;
	}
	
	public void login(
			final HttpServletRequest request, 
			final HttpServletResponse mResponse, 
			final String username, 
			final String password) {
		 serv(new Serv.ResponseRunnable() { @Override public Response run() {
			LoginInput entity = new LoginInput();
			entity.username = username;
			entity.password = password;
      return Serv.service  
				.target(ListenerManifestVars.getValue("user_service"))
				.path("user") .path("login")
				.request()
				.post(Entity.json(entity));
		}},
		LoginOutput.class)
		.returnType(new ResponseObject() {
      @Override
      public void returned(Object object) {
        mLogin = (LoginOutput) object;
			if(mLogin.authKey!=null && mLogin.authKey.trim().length()>0) {
				request.getSession(true).setAttribute("loggedin", true);
				request.getSession(true).setAttribute("authkey", mLogin.authKey);
				request.getSession(true).setAttribute("name", username);

				Cookie authKeyCookie = new Cookie("authkey", mLogin.authKey);
				authKeyCookie.setMaxAge(ONE_YEAR);
				mResponse.addCookie(authKeyCookie);
				Cookie usernameCookie = new Cookie("name", username);
				usernameCookie.setMaxAge(ONE_YEAR);
				mResponse.addCookie(usernameCookie);
			}
			if(mLogin.admin) {
				request.getSession(true).setAttribute("admin", true);
				Cookie isAdmin = new Cookie("admin", "yeah");
				isAdmin.setMaxAge(ONE_YEAR);
				mResponse.addCookie(isAdmin);
			}}})
			.addStatusMap(Urls.loginErrorMessages()).go();
	}

	public void logout(final HttpServletRequest request, final HttpServletResponse resp) {
		serv(new Serv.ResponseRunnable() { @Override public Response run() {
		  Response s = Serv.service  
				.target(ListenerManifestVars.getValue("user_service"))
				.path("user") .path("logout")
				.request()
				.delete();
			request.getSession(true).setAttribute("loggedin", null);
			request.getSession(true).setAttribute("admin", null);
			request.getSession(true).setAttribute("name", null);
			request.getSession(true).setAttribute("authkey", null);

			Cookie authKeyCookie = new Cookie("authkey", "");
			authKeyCookie.setMaxAge(0);
			resp.addCookie(authKeyCookie);
			Cookie isAdmin = new Cookie("admin", "");
			isAdmin.setMaxAge(0);
			resp.addCookie(isAdmin);
			Cookie usernameCookie = new Cookie("name", "");
			usernameCookie.setMaxAge(0);
			resp.addCookie(usernameCookie);
			return s;
		}}, Void.class)
		.exception(new Runnable() { @Override public void run() {
			Logger.getLogger(LoginLogoutService.class).error("Whoops... erm...");
			request.getSession(true).setAttribute("loggedin", null);
			request.getSession(true).setAttribute("authkey", null);
			request.getSession(true).setAttribute("name", null);

			Cookie authKeyCookie = new Cookie("authkey", "");
			authKeyCookie.setMaxAge(0);
			resp.addCookie(authKeyCookie);
			Cookie isAdmin = new Cookie("admin", "");
			isAdmin.setMaxAge(0);
			resp.addCookie(isAdmin);
			Cookie usernameCookie = new Cookie("name", "");
			usernameCookie.setMaxAge(0);
			resp.addCookie(usernameCookie);	
			request.getSession(true).setAttribute("admin", null);
			}}).go();
	}

}
