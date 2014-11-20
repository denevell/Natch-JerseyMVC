package org.denevell.natch.jerseymvc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.services.ThreadAddService.ThreadAddInput.StringWrapper;
import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseRunnable;
import org.denevell.natch.jerseymvc.utils.Urls;

public class ThreadAddService {

  public String errorMessage = "";

	public void add(Object trueObject, 
			final HttpServletRequest serv,
			final String subject, 
			final String content,
			final String tags) {
		if (trueObject == null) return;
		errorMessage = Serv.serv(new ResponseRunnable() { @Override public Response run() {
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
			  return Response.status(401).build();
			}
      return Serv.service 
				.target(ListenerManifestVars.getValue("rest_service"))
				.path("rest").path("thread").request()
				.header("AuthKey", (String) authKey)
				.put(Entity.json(entity));
			}}, Void.class)
			.addStatusMap(Urls.threadAddErrorMessages()).go();
	}

  public static class ThreadAddInput {
    public String subject;
    public String content;
    public String threadId;
    public List<StringWrapper> tags = new ArrayList<>();
    public static class StringWrapper {
      public StringWrapper(String s) { string = s; }
      public String string;
    }
  }

}
