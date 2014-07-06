package org.denevell.natch.jerseymvc.onethread.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.threads.io.DeletePostOutput;

public class DeleteThreadModule extends TemplateModule {

	private DeletePostOutput mDeleteThread;
	
	public DeleteThreadModule() {
		super(null);
	}
	
	public boolean getSuccessful() {
		return mDeleteThread!=null && mDeleteThread.isSuccessful();
	}

	public boolean delete(Object trueObject, 
			final HttpServletRequest serv,
			final String id) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			mDeleteThread= sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("del").path(id).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.delete(DeletePostOutput.class);
			}})
		._403(new Runnable() { @Override public void run() {
			mDeleteThread.setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			mDeleteThread.setErrorMessage("You're not logged in");
			}})
		._exception(new Runnable() { @Override public void run() {
			mDeleteThread.setErrorMessage("Whoops... erm...");
			}}).go();
	}


}
