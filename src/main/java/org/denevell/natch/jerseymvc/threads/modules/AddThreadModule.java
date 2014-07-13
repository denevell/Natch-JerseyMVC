package org.denevell.natch.jerseymvc.threads.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.thread.view.io.AddThreadInput;
import org.denevell.natch.jerseymvc.thread.view.io.AddThreadOutput;

@TemplateName("addthread.mustache")
public class AddThreadModule extends TemplateModule {

	private AddThreadOutput mAddThread = new AddThreadOutput();
	
	public boolean getLoggedin() {
		return mRequest.getSession(true).getAttribute("loggedin")!=null;
	}
	
	public AddThreadOutput getAddthread() {
		return mAddThread;
	}

	public boolean add(Object trueObject, 
			final HttpServletRequest serv,
			final String subject, 
			final String content,
			final String tags) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			AddThreadInput entity = new AddThreadInput(subject, content);
			mAddThread = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("addthread").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity), AddThreadOutput.class);
			}})
		._403(new Runnable() { @Override public void run() {
			mAddThread.setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			mAddThread.setErrorMessage("You're not logged in");
			}})
		._400(new Runnable() { @Override public void run() {
			mAddThread.setErrorMessage(Strings.getPostFieldsCannotBeBlank());
			}})
		._exception(new Runnable() { @Override public void run() {
			mAddThread.setErrorMessage("Whoops... erm...");
			}}).go();
	}


}
