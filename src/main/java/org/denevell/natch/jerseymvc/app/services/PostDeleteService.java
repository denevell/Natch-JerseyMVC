package org.denevell.natch.jerseymvc.app.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.app.models.PostDeleteOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostDeleteService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public PostDeleteOutput mPostDelete = new PostDeleteOutput();
	
	public PostDeleteOutput getPostDelete() {
		return mPostDelete;
	}
	
	public boolean delete(Object trueObject, 
			final HttpServletRequest serv,
			final String postId) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			mPostDelete = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("del").path(postId).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.delete(PostDeleteOutput.class);
			}})
		._403(new Runnable() { @Override public void run() {
			mPostDelete.setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			mPostDelete.setErrorMessage("You're not logged in");
			}})
		._exception(new Runnable() { @Override public void run() {
			mPostDelete.setErrorMessage("Whoops... erm...");
			}}).go();
	}


}