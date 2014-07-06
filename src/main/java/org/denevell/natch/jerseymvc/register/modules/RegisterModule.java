package org.denevell.natch.jerseymvc.register.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.login.modules.LoginLogoutModule;
import org.denevell.natch.jerseymvc.register.io.RegisterInput;
import org.denevell.natch.jerseymvc.register.io.RegisterOutput;

public class RegisterModule extends TemplateModule {
	
	public RegisterOutput mRegister = new RegisterOutput();

	public RegisterModule() {
		super("register.mustache");
	}
	
	public RegisterOutput getRegister() {
		return mRegister;
	}

	public boolean getLoggedin() {
		return mRequest.getSession(true).getAttribute("loggedin")!=null;
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
