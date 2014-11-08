package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadEditService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public ThreadEditOutput mOutput = new ThreadEditOutput();

	public ThreadEditOutput getOutput() {
		return mOutput;
	}
	
	public boolean fetch(
			final HttpServletRequest serv,
			final String subject,
			final String content,
			final String tags, 
			final int id) {
		return serv(new Runnable() { @Override public void run() {
		  ThreadEditInput entity = new ThreadEditInput();
		  entity.content = content;
		  entity.subject = subject;
			entity.tags = Arrays.asList(tags.split("[,\\s]+"));
			mOutput = sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("post").path("editthread").path(String.valueOf(id)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity), ThreadEditOutput.class);
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
			mOutput.errorMessage = "Please check the fields and try again.";
			}})
		._exception(new Runnable() { @Override public void run() {
			mOutput.errorMessage = "Whoops... erm...";
			}}).go();
	}

  public static class ThreadEditInput {
    public String content;
    public String subject;
    public List<String> tags;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ThreadEditOutput {
    public boolean successful;
    public String error = "";
    public String errorMessage;
  }


}
