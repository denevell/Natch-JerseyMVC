package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class LoginLogoutService {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private LoginOutput mLogin = new LoginOutput();
	private static int ONE_YEAR = 60*60*24*365;
	public LoginOutput getLogin() {
		return mLogin;
	}
	
	public boolean login(
			final HttpServletRequest request, 
			final HttpServletResponse mResponse, 
			final String username, 
			final String password) {
		return serv(new Runnable() { @Override public void run() {
			LoginInput entity = new LoginInput();
			entity.username = username;
			entity.password = password;
      mLogin = sService 
				.target(ManifestVarsListener.getValue("user_service"))
				.path("user") .path("login")
				.request()
				.post(Entity.json(entity), LoginOutput.class);
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
		._403(new Runnable() { @Override public void run() {
			mLogin.errorMessage = Strings.getLogonError();
			}})
		._400(new Runnable() { @Override public void run() {
			mLogin.errorMessage = Strings.getBlankUsernameOrPassword();
			}})
		._exception(new Runnable() { @Override public void run() {
			mLogin.errorMessage = "Whoops... erm...";
			}}).go();
	}

	public boolean logout(final HttpServletRequest request, final HttpServletResponse resp) {
		return serv(new Runnable() { @Override public void run() {
			sService 
				.target(ManifestVarsListener.getValue("user_service"))
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

			}})
		._exception(new Runnable() { @Override public void run() {
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
			resp.addCookie(usernameCookie);	request.getSession(true).setAttribute("admin", null);
			}
		}).go();
	}

  public static class LoginInput {
    public String username;
    public String password;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LoginOutput {
    public String authKey = "";
    public boolean admin;
    public String errorMessage;
  }

}
