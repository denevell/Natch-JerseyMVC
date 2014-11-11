package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.services.ThreadAddService.ThreadAddInput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostAddService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
  public String errorMessage;
	
	public boolean add(Object trueObject, 
			final HttpServletRequest serv,
			final String content,
			final String threadId) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			ThreadAddInput entity = new ThreadAddInput();
			entity.subject = "-";
			entity.content = content;
			entity.threadId = threadId;
			sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("post").path("add").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity));
			}})
		._403(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._401(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._400(new Runnable() { @Override public void run() {
			errorMessage = Strings.getPostFieldsCannotBeBlank();
			}})
		._exception(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm...";
			}}).go();
	}


}
