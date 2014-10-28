package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.jerseymvc.models.ThreadEditOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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
		  entity.setContent(content);
		  entity.setSubject(subject);
		  entity.setTags(Arrays.asList(tags.split(",")));
			mOutput = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("editthread").path(String.valueOf(id)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity), ThreadEditOutput.class);
			if(mOutput.getError()!=null) {
			  mOutput.setErrorMessage(mOutput.getError());
			}
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

  @XmlRootElement
  public class ThreadEditInput {

    @NotEmpty
    @NotBlank
    private String content;
    @NotEmpty
    @NotBlank
    private String subject;
    private List<String> tags;

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public List<String> getTags() {
      return tags;
    }

    public void setTags(List<String> tags) {
      this.tags = tags;
    }

  }


}
