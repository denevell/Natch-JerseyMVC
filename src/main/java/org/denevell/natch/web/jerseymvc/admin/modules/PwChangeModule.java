package org.denevell.natch.web.jerseymvc.admin.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.web.jerseymvc.Serv;
import org.denevell.natch.web.jerseymvc.Serv.ResponseRunnable;
import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.admin.io.ChangePasswordInput;

public class PwChangeModule extends TemplateModule {
	
   	protected boolean mProcessed;
	protected boolean mError;

	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException { 
        return createTemplate("admin_changepw.mustache", 
        	new Object() {
        		boolean processed = mProcessed;
        		boolean error = mError;
            }
        );
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
			return response;
		}})
		._exception(new Runnable() { @Override public void run() {
			mError = true;
			}}).go();
	}
	

}
