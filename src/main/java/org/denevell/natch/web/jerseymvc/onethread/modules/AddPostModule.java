package org.denevell.natch.web.jerseymvc.onethread.modules;

import static org.denevell.natch.web.jerseymvc.Serv.serv;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.web.jerseymvc.Strings;
import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.onethread.io.AddThreadInput;
import org.denevell.natch.web.jerseymvc.onethread.io.AddThreadOutput;

public class AddPostModule extends TemplateModule {

	public AddThreadOutput mAddPost = new AddThreadOutput();

	@SuppressWarnings("unused")
	public String template(final HttpServletRequest request) throws IOException {
		return createTemplate("addpost.mustache", 
    		new Object() {
				boolean loggedin = request.getSession(true).getAttribute("loggedin")!=null;
				AddThreadOutput addpost = mAddPost;
    		});
	}

	public boolean add(Object trueObject, 
			final HttpServletRequest serv,
			final String content,
			final String threadId) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			AddThreadInput entity = new AddThreadInput("-", content);
			entity.setThreadId(threadId);
			mAddPost = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("add").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity), AddThreadOutput.class);
			}})
		._403(new Runnable() { @Override public void run() {
			mAddPost.setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			mAddPost.setErrorMessage("You're not logged in");
			}})
		._400(new Runnable() { @Override public void run() {
			mAddPost.setErrorMessage(Strings.getPostFieldsCannotBeBlank());
			}})
		._exception(new Runnable() { @Override public void run() {
			mAddPost.setErrorMessage("Whoops... erm...");
			}}).go();
	}


}
