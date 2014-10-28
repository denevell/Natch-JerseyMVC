package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.models.RegisterOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

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
				entity = new RegisterInput(username, password, recoveryEmail);
			} else {
				entity = new RegisterInput(username, password);
			}
			mRegister = sService
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest").path("user").request()
				.put(Entity.json(entity), RegisterOutput.class);
			if (mRegister.getError() != null && mRegister.getError().trim().length() > 0) {
				mRegister.setErrorMessage(mRegister.getError()); // Hack...
			} else {
				new LoginLogoutService().login(serv, resp, username, password);
			}
			}})
		._403(new Runnable() { @Override public void run() {
			mRegister.setErrorMessage("Username or password incorrect");
			}})
		._400(new Runnable() { @Override public void run() {
			mRegister.setErrorMessage(Strings.getBlankUsernameOrPassword());
			}})
		._exception(new Runnable() { @Override public void run() {
			mRegister.setErrorMessage("Whoops... erm...");
			}})
		.go();
	}

  @XmlRootElement
  public class RegisterInput {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email(regexp = ".+@.+\\..+")
    private String recoveryEmail;

    public RegisterInput(@NotBlank String username, @NotBlank String password) {
      this.setUsername(username);
      this.setPassword(password);
    }

    public RegisterInput(@NotBlank String username, @NotBlank String password,
        @Email String recoveryEmail) {
      this.setUsername(username);
      this.setPassword(password);
      this.setRecoveryEmail(recoveryEmail);
    }

    public RegisterInput() {
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getRecoveryEmail() {
      return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
      this.recoveryEmail = recoveryEmail;
    }

  }


}
