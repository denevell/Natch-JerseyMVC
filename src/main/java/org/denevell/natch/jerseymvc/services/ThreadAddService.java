package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadAddService {

	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public ThreadAddOutput mAddThread = new ThreadAddOutput();

	public boolean add(Object trueObject, 
			final HttpServletRequest serv,
			final String subject, 
			final String content,
			final String tags) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			ThreadAddInput entity = new ThreadAddInput();
			entity.subject = subject;
			entity.content = content;
		  //Set<ConstraintViolation<ThreadAddInput>> validations = Validation.buildDefaultValidatorFactory().getValidator().validate(entity);
			entity.tags = Arrays.asList(tags.split("[,\\s]+"));
			mAddThread = sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("post").path("addthread").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity), ThreadAddOutput.class);
			if(!mAddThread.successful) {
			  mAddThread.errorMessage = mAddThread.error;
			}
			}})
		._403(new Runnable() { @Override public void run() {
		  mAddThread.errorMessage = "You're not logged in";
			}})
		._401(new Runnable() { @Override public void run() {
		  mAddThread.errorMessage = "You're not logged in";
			}})
		._400(new Runnable() { @Override public void run() {
		  mAddThread.errorMessage = Strings.getPostFieldsCannotBeBlank();
			}})
		._exception(new Runnable() { @Override public void run() {
		  mAddThread.errorMessage = "Whoops... erm...";
			}}).go();
	}

  public static class ThreadAddInput {
    public String subject;
    public String content;
    @XmlElement(required = false)
    public String threadId;
    public List<String> tags = new ArrayList<String>();
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ThreadAddOutput extends SuccessOrError {
    public ThreadOutput thread;
    public String errorMessage;
  }

}
