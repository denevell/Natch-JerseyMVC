package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadFromPostService {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
  public String errorMessage;
  public String threadId;
	
	public boolean fetchPost(final HttpServletRequest serv, final int postId, final String subject) {
		return serv(new Serv.ResponseRunnable() { @Override public Response run() {
			AddThreadFromPostResourceInput input = new AddThreadFromPostResourceInput();
			input.postId = postId;
			input.subject = subject;
			return sService
                .target(ListenerManifestVars.getValue("rest_service"))
                .path("rest").path("thread").path("frompost")
                .request()
                .header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
                .put(Entity.json(input)); 	
			}}, HashMap.class)
		._400(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm... 400";
			}
		})
		._401(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm... 401";
			}
		})
		._403(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm... 403";
			}
		})
		.returnType(new ResponseObject() { @SuppressWarnings({ "unchecked" }) @Override public void returned(Object o) {
		  threadId = ((HashMap<String, String>)o).get("threadId");
      }
    })
		._exception(new Runnable() { @Override public void run() {
			errorMessage = "Whoops... erm...";
			}}).go();
	}
	
	public static class AddThreadFromPostResourceInput {
		public long postId;
		public String subject;
	}

}
