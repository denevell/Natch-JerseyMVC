package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostDeleteService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public PostDeleteOutput mPostDelete = new PostDeleteOutput();
	
	public PostDeleteOutput getPostDelete() {
		return mPostDelete;
	}
	
	public boolean delete(final HttpServletRequest serv, final int postId) {
		return serv(new Runnable() { @Override public void run() {
			mPostDelete = sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("post").path("del").path(String.valueOf(postId)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.delete(PostDeleteOutput.class);
			if(mPostDelete.error!=null) {
			  mPostDelete.errorMessage = mPostDelete.error;
			}
			}})
		._403(new Runnable() { @Override public void run() {
			mPostDelete.errorMessage = "You're not logged in";
			}})
		._405(new Runnable() { @Override public void run() {
			mPostDelete.errorMessage = "Unable to delete";
			}})
		._500(new Runnable() { @Override public void run() {
			mPostDelete.errorMessage = "Unable to delete";
			}})
		._401(new Runnable() { @Override public void run() {
			mPostDelete.errorMessage = "You're not logged in";
			}})
		._exception(new Runnable() { @Override public void run() {
			mPostDelete.errorMessage = "Whoops... erm...";
			}}).go();
	}

	@JsonIgnoreProperties(ignoreUnknown=true)
  public static class PostDeleteOutput {
    public boolean successful;
    public String error = "";
    public String errorMessage;
  }

}
