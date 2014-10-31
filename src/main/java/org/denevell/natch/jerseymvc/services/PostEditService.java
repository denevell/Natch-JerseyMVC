package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostEditService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public PostEditOutput mOutput = new PostEditOutput();

	public boolean fetch(Object trueObject, 
			final HttpServletRequest serv,
			final String content,
			final int id) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
		  PostEditInput entity = new PostEditInput();
		  entity.content = content;
			mOutput = sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("post").path("editpost").path(String.valueOf(id)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity), PostEditOutput.class);
			if(mOutput.error!=null) {
			  mOutput.errorMessage = mOutput.error;
			}
			}})
		._403(new Runnable() { @Override public void run() {
			mOutput.errorMessage = "You're not logged in";
			}})
		._401(new Runnable() { @Override public void run() {
			mOutput.errorMessage = "You're not logged in";
			}})
		._400(new Runnable() { @Override public void run() {
			mOutput.errorMessage = "Errrr";
			}})
		._exception(new Runnable() { @Override public void run() {
			mOutput.errorMessage = "Whoops... erm...";
			}}).go();
	}

  public static class PostEditInput {
    public String content;
  }

  public static class PostEditOutput {
    public boolean successful;
    public String error = "";
    public String errorMessage;
  }

}
