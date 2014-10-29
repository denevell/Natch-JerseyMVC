package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.jerseymvc.models.PostEditOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostEditService {

	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	public PostEditOutput mOutput = new PostEditOutput();

	public boolean fetch(Object trueObject, 
			final HttpServletRequest serv,
			final String content,
			final int id) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
		  PostEditInput entity = new PostEditInput();
		  entity.setContent(content);
			mOutput = sService
				.target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
				.path("rest").path("post").path("editpost").path(String.valueOf(id)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity), PostEditOutput.class);
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
  public class PostEditInput {

    //@NotEmpty
    //@NotBlank
    private String content;

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

  }

}
