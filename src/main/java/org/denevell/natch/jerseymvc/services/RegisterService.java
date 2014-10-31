package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class RegisterService {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public RegisterOutput mRegister = new RegisterOutput();

	public RegisterOutput getRegister() {
		return mRegister;
	}

	public boolean register(
	    final HttpServletRequest serv,
			final HttpServletResponse resp,
			final String username, 
			final String password,
			final String recoveryEmail) {
		return serv(new Runnable() { @Override public void run() {
			RegisterInput entity;
			if (recoveryEmail != null && recoveryEmail.trim().length() > 0) {
				entity = new RegisterInput();
				entity.username = username;
				entity.password = password;
				entity.recoveryEmail = recoveryEmail;
			} else {
				entity = new RegisterInput();
				entity.username = username;
				entity.password = password;
			}
			mRegister = sService
				.target(ManifestVarsListener.getValue("user_service"))
				.path("rest").path("user").request()
				.put(Entity.json(entity), RegisterOutput.class);
			if (mRegister.error != null && mRegister.error.trim().length() > 0) {
				mRegister.errorMessage = mRegister.error; // Hack...
			} else {
				new LoginLogoutService().login(serv, resp, username, password);
			}
			}})
		._403(new Runnable() { @Override public void run() {
			mRegister.errorMessage = "Username or password incorrect";
			}})
		._400(new Runnable() { @Override public void run() {
			mRegister.errorMessage = Strings.getBlankUsernameOrPassword();
			}})
		._exception(new Runnable() { @Override public void run() {
			mRegister.errorMessage = "Whoops... erm...";
			}})
		.go();
	}

  public static class RegisterInput {
    public String username;
    public String password;
    public String recoveryEmail;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class RegisterOutput {
    public String error;
    public String errorMessage;
  }


}
