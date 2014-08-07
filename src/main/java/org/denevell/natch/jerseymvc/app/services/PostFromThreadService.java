package org.denevell.natch.jerseymvc.app.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.jerseymvc.app.models.ThreadAddOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostFromThreadService {
	
   	public ThreadAddOutput mThreadOutput;
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	
	public ThreadAddOutput getThread() {
		return mThreadOutput;
	}
	
	public boolean fetchPost(Object trueObject, final int postId, final String subject) {
		if (trueObject == null) return true;
		return serv(new Runnable() { @Override public void run() {
			AddThreadFromPostResourceInput input = new AddThreadFromPostResourceInput();
			input.setPostId(postId);
			input.setSubject(subject);
			mThreadOutput = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("thread").path("frompost")
                .request()
                .put(Entity.json(input), ThreadAddOutput.class); 	
			}})
		._exception(new Runnable() { @Override public void run() {
			mThreadOutput.setErrorMessage("Whoops... erm...");
			}}).go();
	}
	
	@XmlRootElement
	public class AddThreadFromPostResourceInput {

		public AddThreadFromPostResourceInput() { }

		private long postId;
		private String subject;

		public long getPostId() {
			return postId;
		}

		public void setPostId(long postId) {
			this.postId = postId;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}
	}

}
