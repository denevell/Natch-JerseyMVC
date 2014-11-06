package org.denevell.natch.jerseymvc.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.denevell.natch.jerseymvc.app.utils.Serv;
import org.denevell.natch.jerseymvc.app.utils.Serv.ResponseRunnable;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PwChangeService {
	
  private boolean mProcessed;
	private boolean mError;
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

	public boolean getProcessed() {
		return mProcessed;
	}
	
	public boolean getError() {
		return mError;
	}
	
	public boolean changePw(Object trueObject, 
			final HttpServletRequest request, 
			final String username, 
			final String password) {
		if(trueObject==null) return true;
		return Serv.serv(new ResponseRunnable() { @Override public Response run() {
			Object attribute = request.getSession(true).getAttribute("authkey");
			PasswordChangeInput entity = new PasswordChangeInput();
			entity.password = password;
      Response response = sService
				.target(ManifestVarsListener.getValue("user_service"))
				.path("user").path("password").path(username)
				.request()
				.header("AuthKey", attribute)
				.post(Entity.json(entity));
			sService
				.target(ManifestVarsListener.getValue("user_service"))
				.path("user").path("password_reset").path("remove").path(username)
				.request()
				.header("AuthKey", attribute)
				.delete();
			if(response.getStatus()<300) {
				mProcessed = true;
			}
			return response;
		}})
		._exception(new Runnable() { @Override public void run() {
			mError = true;
			}}).go();
	}

  public static class PasswordChangeInput {
    public String password;
  }
	

}
