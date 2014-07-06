package org.denevell.natch.jerseymvc.onethread.modules;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.onethread.io.AddThreadInput;
import org.denevell.natch.jerseymvc.onethread.io.AddThreadOutput;

public class AddPostModule extends TemplateModule {

	public AddThreadOutput mAddPost = new AddThreadOutput();
	
	public AddPostModule() {
		super("addpost.mustache");
	}
	
	public boolean getLoggedin() {
		return mRequest.getSession(true).getAttribute("loggedin")!=null;
	}

	public AddThreadOutput getAddpost() {
		return mAddPost;
	}
	
	public int getNumPosts() {
		if(mAddPost==null || mAddPost.getThread()==null) {
			return 0;
		}
		return mAddPost.getThread().getNumPosts();
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
