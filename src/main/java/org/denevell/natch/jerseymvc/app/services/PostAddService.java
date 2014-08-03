package org.denevell.natch.jerseymvc.app.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.models.ThreadAddInput;
import org.denevell.natch.jerseymvc.app.models.ThreadAddOutput;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostAddService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public ThreadAddOutput mAddPost = new ThreadAddOutput();
	
	public ThreadAddOutput getAddpost() {
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
			ThreadAddInput entity = new ThreadAddInput("-", content);
			entity.setThreadId(threadId);
			mAddPost = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("add").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity), ThreadAddOutput.class);
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
