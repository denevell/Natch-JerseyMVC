package org.denevell.natch.jerseymvc.app.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.models.AddThreadInput;
import org.denevell.natch.jerseymvc.app.models.AddThreadOutput;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadAddService {

	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private AddThreadOutput mAddThread = new AddThreadOutput();

	public boolean add(Object trueObject, 
			final HttpServletRequest serv,
			final String subject, 
			final String content,
			final String tags) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			AddThreadInput entity = new AddThreadInput(subject, content);
			mAddThread = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("addthread").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity), AddThreadOutput.class);
			}})
		._403(new Runnable() { @Override public void run() {
			getAddThread().setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			getAddThread().setErrorMessage("You're not logged in");
			}})
		._400(new Runnable() { @Override public void run() {
			getAddThread().setErrorMessage(Strings.getPostFieldsCannotBeBlank());
			}})
		._exception(new Runnable() { @Override public void run() {
			getAddThread().setErrorMessage("Whoops... erm...");
			}}).go();
	}

	public AddThreadOutput getAddThread() {
		return mAddThread;
	}

}