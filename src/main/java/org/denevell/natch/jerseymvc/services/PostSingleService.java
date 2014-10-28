package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import org.denevell.natch.jerseymvc.models.PostOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostSingleService {
	
   	public PostOutput mPostOutput;
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	
	public PostOutput getPost() {
		return mPostOutput;
	}
	
	public boolean fetchPost(Object trueObject, final int id) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			mPostOutput = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("post").path("single")
                .path(String.valueOf(id))
                .request()
                .get(PostOutput.class); 	
			}})
		._exception(new Runnable() { @Override public void run() {
			mPostOutput.setErrorMessage("Whoops... erm...");
			}}).go();
	}
	

}
