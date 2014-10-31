package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.app.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.services.ThreadAddService.ThreadAddOutput;
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
			input.postId = postId;
			input.subject = subject;
			mThreadOutput = sService
                .target(ManifestVarsListener.getValue("rest_service"))
                .path("rest").path("thread").path("frompost")
                .request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
                .put(Entity.json(input), ThreadAddOutput.class); 	
			}})
		._400(new Runnable() { @Override public void run() {
			mThreadOutput.errorMessage = "Whoops... erm... 400";
			}
		})
		._401(new Runnable() { @Override public void run() {
			mThreadOutput.errorMessage = "Whoops... erm... 401";
			}
		})
		._403(new Runnable() { @Override public void run() {
			mThreadOutput.errorMessage = "Whoops... erm... 403";
			}
		})
		._exception(new Runnable() { @Override public void run() {
			mThreadOutput.errorMessage = "Whoops... erm...";
			}}).go();
	}
	
	public static class AddThreadFromPostResourceInput {
		public long postId;
		public String subject;
	}

}
