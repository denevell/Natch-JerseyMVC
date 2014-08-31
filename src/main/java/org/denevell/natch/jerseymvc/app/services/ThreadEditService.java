package org.denevell.natch.jerseymvc.app.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.app.models.ThreadEditInput;
import org.denevell.natch.jerseymvc.app.models.ThreadEditOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadEditService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public ThreadEditOutput mOutput = new ThreadEditOutput();

	public ThreadEditOutput getOutput() {
		return mOutput;
	}
	
	public boolean fetch(Object trueObject, 
			final HttpServletRequest serv,
			final String subject,
			final String content) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
		  ThreadEditInput entity = new ThreadEditInput();
		  entity.setContent(content);
		  entity.setSubject(subject);
			mOutput = sService
				.target("http://localhost:8080/url/")
				.path("path").path("path").request()
				.header("Key", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity), ThreadEditOutput.class);
			}})
		._403(new Runnable() { @Override public void run() {
			mOutput.setErrorMessage("You're not logged in");
			}})
		._401(new Runnable() { @Override public void run() {
			mOutput.setErrorMessage("You're not logged in");
			}})
		._400(new Runnable() { @Override public void run() {
			mOutput.setErrorMessage("Errrr");
			}})
		._exception(new Runnable() { @Override public void run() {
			mOutput.setErrorMessage("Whoops... erm...");
			}}).go();
	}


}
