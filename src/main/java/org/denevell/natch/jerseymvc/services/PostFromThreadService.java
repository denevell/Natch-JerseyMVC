package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.jerseymvc.models.ThreadAddOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PostFromThreadService {
	
   	public ThreadAddOutput mThreadOutput = new ThreadAddOutput();
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	
	public ThreadAddOutput getThread() {
		return mThreadOutput;
	}
	
	public boolean fetchPost(final HttpServletRequest serv, final int postId, final String subject) {
		return serv(new Runnable() { @Override public void run() {
			AddThreadFromPostResourceInput input = new AddThreadFromPostResourceInput();
			input.setPostId(postId);
			input.setSubject(subject);
			mThreadOutput = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("thread").path("frompost")
                .request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
                .put(Entity.json(input), ThreadAddOutput.class); 	
			}})
		._400(new Runnable() { @Override public void run() {
			mThreadOutput.setErrorMessage("Whoops... erm... 400");
			}
		})
		._401(new Runnable() { @Override public void run() {
			mThreadOutput.setErrorMessage("Whoops... erm... 401");
			}
		})
		._403(new Runnable() { @Override public void run() {
			mThreadOutput.setErrorMessage("Whoops... erm... 403");
			}
		})
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
