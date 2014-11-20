package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Urls;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;

public class ThreadFromPostService {
	
  public String threadId;
  public String errorMessage;
	
	public void fetchPost(final HttpServletRequest serv, final int postId, final String subject) {
		serv(new Serv.ResponseRunnable() { @Override public Response run() {
			AddThreadFromPostResourceInput input = new AddThreadFromPostResourceInput();
			input.postId = postId;
			input.subject = subject;
			return Serv.service 
                .target(ListenerManifestVars.getValue("rest_service"))
                .path("rest").path("thread").path("frompost")
                .request()
                .header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
                .put(Entity.json(input)); 	
			}}, HashMap.class)
		.addStatusMap(Urls.threadFromPostErrorMessages())
		.returnType(new ResponseObject() { @SuppressWarnings({ "unchecked" }) @Override public void returned(Object o) {
		  threadId = ((HashMap<String, String>)o).get("threadId");
      }
    });
	}
	
	public static class AddThreadFromPostResourceInput {
		public long postId;
		public String subject;
	}

}
