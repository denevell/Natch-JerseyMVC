package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.models.PostDeleteOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadDeleteService {

	private PostDeleteOutput mDeleteThread = new PostDeleteOutput();
	public static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	
	public boolean delete(final HttpServletRequest serv, final String id) {
		return serv(new Runnable() { @Override public void run() {
			mDeleteThread = sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("post").path("del").path(id).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.delete(PostDeleteOutput.class);
			if(!mDeleteThread.isSuccessful()) {
			  getDeleteThread().setErrorMessage(mDeleteThread.getError());
			}
			}})
		._403(new Runnable() { @Override public void run() {
			getDeleteThread().setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			getDeleteThread().setErrorMessage("You're not logged in");
			}})
		._exception(new Runnable() { @Override public void run() {
			getDeleteThread().setErrorMessage("Whoops... erm...");
			}}).go();
	}

	public PostDeleteOutput getDeleteThread() {
		return mDeleteThread;
	}

}
