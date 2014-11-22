package org.denevell.natch.jerseymvc.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.services.ServiceInputs.PasswordChangeInput;
import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseRunnable;
import org.denevell.natch.jerseymvc.utils.Urls;

public class PwChangeService {
	
  private boolean mProcessed;
  public String errorMessage;

	public boolean getProcessed() {
		return mProcessed;
	}
	
	public void changePw(Object trueObject, 
			final HttpServletRequest request, 
			final String username, 
			final String password) {
		if(trueObject==null) return;
		Serv.serv(new ResponseRunnable() { @Override public Response run() {
			Object attribute = request.getSession(true).getAttribute("authkey");
			PasswordChangeInput entity = new PasswordChangeInput();
			entity.password = password;
      Response response = Serv.service 
				.target(ListenerManifestVars.getValue("user_service"))
				.path("user").path("password").path(username)
				.request()
				.header("AuthKey", attribute)
				.post(Entity.json(entity));
			if(response.getStatus()<300) {
				mProcessed = true;
			}
			return response;
		}}, Void.class)
		.addStatusMap(Urls.pwChangeErrorMessages()).go();
			
	}

	

}
