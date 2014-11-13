package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadDeleteService {

	public static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
  public String errorMessage;
	
	public boolean delete(final HttpServletRequest serv, final String id) {
		return serv(new Runnable() { @Override public void run() {
			sService
				.target(ListenerManifestVars.getValue("rest_service"))
				.path("rest").path("post").path("del").path(id).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.delete();
			}})
		._403(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._401(new Runnable() { @Override public void run() {
			errorMessage = "You're not logged in";
			}})
		._exception(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm...";
			}}).go();
	}

}
