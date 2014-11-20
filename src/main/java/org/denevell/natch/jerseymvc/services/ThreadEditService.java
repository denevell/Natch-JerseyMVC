package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.services.ThreadAddService.ThreadAddInput.StringWrapper;
import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Urls;

public class ThreadEditService {

  public String errorMessage;

	public void fetch(
			final HttpServletRequest serv,
			final String subject,
			final String content,
			final String tags, 
			final int id) {
		serv(new Serv.ResponseRunnable() { @Override public Response run() {
		  ThreadEditInput entity = new ThreadEditInput();
		  entity.content = content;
		  entity.subject = subject;
			List<String> ts = Arrays.asList(tags.split("[,\\s]+"));
			for (String string : ts) {
			  entity.tags.add(new StringWrapper(string));
      }
			return Serv.service 
				.target(ListenerManifestVars.getValue("rest_service"))
				.path("rest").path("thread").path("edit").path(String.valueOf(id)).request()
				.header("AuthKey", (String) serv.getSession(true).getAttribute("authkey"))
				.post(Entity.json(entity));
			}}, Void.class)
			.addStatusMap(Urls.threadEditErrorMessages()).go();
			
	}

  public static class ThreadEditInput {
    public String content;
    public String subject;
    public List<StringWrapper> tags = new ArrayList<>();
  }

}
