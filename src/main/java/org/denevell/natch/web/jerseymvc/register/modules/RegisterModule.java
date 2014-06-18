package org.denevell.natch.web.jerseymvc.register.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.login.modules.LoginLogoutModule;
import org.denevell.natch.web.jerseymvc.register.io.RegisterInput;
import org.denevell.natch.web.jerseymvc.register.io.RegisterOutput;

import static org.denevell.natch.web.jerseymvc.Serv.serv;

public class RegisterModule extends TemplateModule {
	
	public RegisterOutput mRegister = new RegisterOutput();

	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException {
		return createTemplate("register.mustache", 
    		new Object() {
				RegisterOutput register = mRegister;
				boolean loggedin = request.getSession(true).getAttribute("loggedin")!=null;
    		});
	}

	public boolean register(Object trueObject, 
			final HttpServletRequest serv,
			final String username, 
			final String password,
			final String recoveryEmail) {
		if (trueObject == null) return true;
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
				new LoginLogoutModule().login(new Object(), serv, username, password);
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


}
