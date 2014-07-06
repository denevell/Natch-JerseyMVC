package org.denevell.natch.jerseymvc.resetpw.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.utils.Serv.ResponseRunnable;

public class ResetPwModule extends TemplateModule {
	
	private boolean mError = false;
	private boolean mProcessed;
	private boolean mShowForm;

   	public ResetPwModule() {
   		super("pwreset.mustache");
	}
	
	public boolean getShow_reset_form() {
		return mShowForm;
	}
	
	public boolean getError() {
		return mError;
	}
	
	public boolean getProcessed() {
		return mProcessed;
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
			showForm();
			}}).go();
	}

	public void showForm() {
		mShowForm = true;
	}

}
