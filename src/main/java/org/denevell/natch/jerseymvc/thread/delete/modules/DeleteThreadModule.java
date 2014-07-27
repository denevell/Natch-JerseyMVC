package org.denevell.natch.jerseymvc.thread.delete.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.app.models.DeletePostOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("")
public class DeleteThreadModule extends TemplateModule {

	private DeletePostOutput mDeleteThread = new DeletePostOutput();
	
	public DeletePostOutput getDeletethread() {
		return mDeleteThread;
	}
	
	public boolean getSuccessful() {
		return mDeleteThread!=null && mDeleteThread.isSuccessful();
	}

	public boolean delete(final HttpServletRequest serv, final String id) {
		return serv(new Runnable() { @Override public void run() {
			mDeleteThread = sService
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
