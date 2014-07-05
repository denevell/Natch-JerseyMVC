package org.denevell.natch.web.jerseymvc.resetpw.modules;

import static org.denevell.natch.web.jerseymvc.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.web.jerseymvc.Serv.ResponseRunnable;
import org.denevell.natch.web.jerseymvc.TemplateModule;

public class ResetPwModule extends TemplateModule {
	
	private boolean mError = false;
	protected boolean mProcessed;

	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) {
		return createTemplate("pwreset.mustache", 
			new Object() {
				boolean error = mError;
				boolean processed = mProcessed;
    		}
		);
	}

	public boolean reset(String trueObject, HttpServletRequest mRequest, final String resetPwEmail) {
		if(trueObject==null) return true;
		return serv(new ResponseRunnable() { @Override public Response run() {
			Response response = sService 
				.target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
				.path("rest").path("user").path("password_reset").path(resetPwEmail)
				.request()
				.post(null);
			mProcessed = true;
			mError = false;
			return response;
		}})
		._exception(new Runnable() { @Override public void run() {
			mError = true;
			}}).go();
	}
	
	public boolean hasError() {
		return mProcessed && mError; 
	}

}
