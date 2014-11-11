package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlElement;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.services.ThreadAddService.ThreadAddInput.StringWrapper;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadAddService {

	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
  public String errorMessage;

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
			List<String> tagStrings = Arrays.asList(tags.split("[,\\s]+"));
			for (String string : tagStrings) {
			  entity.tags.add(new StringWrapper(string));
      }
			Object authKey = serv.getSession(true).getAttribute("authkey");
			if(authKey==null || authKey.toString().trim().length()==0) {
			  errorMessage = "You're not logged in";
			  return;
			}
      sService
				.target(ManifestVarsListener.getValue("rest_service"))
				.path("rest").path("thread").request()
				.header("AuthKey", (String) authKey)
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

  public static class ThreadAddInput {
    public String subject;
    public String content;
    @XmlElement(required = false)
    public String threadId;
    public List<StringWrapper> tags = new ArrayList<>();
    public static class StringWrapper {
      public StringWrapper(String s) { string = s; }
      public String string;
    }
  }

}
