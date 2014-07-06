package org.denevell.natch.web.jerseymvc.admin.modules;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.web.jerseymvc.Serv;
import org.denevell.natch.web.jerseymvc.Serv.ResponseRunnable;
import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.admin.io.ChangePasswordInput;

public class PwChangeModule extends TemplateModule {
	
   	private boolean mProcessed;
	private boolean mError;

	public PwChangeModule() {
		super("admin_changepw.mustache");
	}
	
	public boolean getProcessed() {
		return mProcessed;
	}
	
	public boolean getError() {
		return mError;
	}
	
	public boolean changePw(Object trueObject, 
			final HttpServletRequest request, 
			final String username, 
			final String password) {
		if(trueObject==null) return true;
		return Serv.serv(new ResponseRunnable() { @Override public Response run() {
			Object attribute = request.getSession(true).getAttribute("authkey");
			Response response = sService
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest").path("user").path("password").path(username)
				.request()
				.header("AuthKey", attribute)
				.post(Entity.json(new ChangePasswordInput(password)));
			sService
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest").path("user").path("password_reset").path("remove").path(username)
				.request()
				.header("AuthKey", attribute)
				.delete();
			if(response.getStatus()<300) {
				mProcessed = true;
			}
			return response;
		}})
		._exception(new Runnable() { @Override public void run() {
			mError = true;
			}}).go();
	}
	

}
