package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostDeleteService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public String errorMessage;
	
	public boolean delete(final HttpServletRequest serv, final int postId) {
		return serv(new Serv.ResponseRunnable() { @Override public Response run() {
			return sService
				.target(ListenerManifestVars.getValue("rest_service"))
				.path("rest").path("post").path("del").path(String.valueOf(postId)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.delete();
			}})
		._403(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._405(new Runnable() { @Override public void run() {
			errorMessage = "Unable to delete";
			}})
		._500(new Runnable() { @Override public void run() {
			errorMessage = "Unable to delete";
			}})
		._401(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._exception(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm...";
			}}).go();
	}

}
