package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Strings;
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
		return serv(new Serv.ResponseRunnable() { @Override public Response run() {
			PostAddInput entity = new PostAddInput();
			entity.content = content;
			entity.threadId = threadId;
			return sService
				.target(ListenerManifestVars.getValue("rest_service"))
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

  public static class PostAddInput {
    public String content;
    @XmlElement(required = false)
    public String threadId;
  }

}
