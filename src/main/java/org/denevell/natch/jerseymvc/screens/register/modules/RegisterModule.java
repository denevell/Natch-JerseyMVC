package org.denevell.natch.jerseymvc.screens.register.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.models.RegisterInput;
import org.denevell.natch.jerseymvc.app.models.RegisterOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.screens.login.modules.LoginLogoutModule;

@TemplateName("register.mustache")
public class RegisterModule extends TemplateModule {
	
	public RegisterOutput mRegister = new RegisterOutput();

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
