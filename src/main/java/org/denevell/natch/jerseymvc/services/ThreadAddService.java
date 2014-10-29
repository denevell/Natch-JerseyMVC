package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlElement;

import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.jerseymvc.models.ThreadAddOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadAddService {

	private static JerseyClient sService = JerseyClientBuilder.createClient()
	    .register(JacksonFeature.class);
	private ThreadAddOutput mAddThread = new ThreadAddOutput();

	public boolean add(Object trueObject, 
			final HttpServletRequest serv,
			final String subject, 
			final String content,
			final String tags) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			ThreadAddInput entity = new ThreadAddInput(subject, content);
		  //Set<ConstraintViolation<ThreadAddInput>> validations = Validation.buildDefaultValidatorFactory().getValidator().validate(entity);
			entity.setTags(tags);
			mAddThread = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("addthread").request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.put(Entity.json(entity), ThreadAddOutput.class);
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

	public ThreadAddOutput getAddThread() {
		return mAddThread;
	}

  public static class ThreadAddInput {

    //@NotEmpty
    //@NotBlank
    private String subject;
    //@NotEmpty
    //@NotBlank
    private String content;
    private String threadId;
    private List<String> tags = new ArrayList<String>();

    public ThreadAddInput() {
    }

    public ThreadAddInput(String subject, String content) {
      this.subject = subject;
      this.content = content;
    }

    public ThreadAddInput(String subject, String content, List<String> tags) {
      this.subject = subject;
      this.content = content;
      this.tags = tags;
    }

    public ThreadAddInput(String subject, String content, String threadId) {
      this.subject = subject;
      this.content = content;
      this.threadId = threadId;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getThreadId() {
      return threadId;
    }

    @XmlElement(required = false)
    public void setThreadId(String thread) {
      this.threadId = thread;
    }

    public List<String> getTags() {
      return tags;
    }

    @XmlElement(name = "tags")
    public void setTags(List<String> tags) {
      this.tags = tags;
    }

    public void setTags(String tags) {
      this.tags = Arrays.asList(tags.split(","));
    }

  }

}
