package org.denevell.natch.jerseymvc.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostEditService {

	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
  public String errorMessage;

	public boolean fetch(Object trueObject, 
			final HttpServletRequest serv,
			final String content,
			final int id) {
		if (trueObject == null) return true;
		return Serv.serv(new Serv.ResponseRunnable() { @Override public Response run() {
		  PostEditInput entity = new PostEditInput();
		  entity.content = content;
			return sService
				.target(ListenerManifestVars.getValue("rest_service"))
				.path("rest").path("post").path("editpost").path(String.valueOf(id)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity));
		}})
		._403(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._401(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._400(new Runnable() { @Override public void run() {
			errorMessage = "Errrr";
			}})
		._exception(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm...";
			}}).go();
	}

  public static class PostEditInput {
    public String content;
  }

}
