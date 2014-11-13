package org.denevell.natch.jerseymvc.services;

import static org.denevell.natch.jerseymvc.utils.Serv.serv;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseRunnable;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PwResetService {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private boolean mError = false;
	private boolean mProcessed = false;
	
	public boolean reset(HttpServletRequest mRequest, final String resetPwEmail) {
		return serv(new ResponseRunnable() { @Override public Response run() {
			Response response = sService 
				.target(ListenerManifestVars.getValue("user_service"))
				.path("user").path("password_reset").path(resetPwEmail)
				.request()
				.post(null);
			mProcessed = true;
			mError = false;
			return response;
		}})
		._exception(new Runnable() { @Override public void run() {
			mProcessed = false;
			mError = true;
			}}).go();
	}

	public boolean isError() {
		return mError;
	}

	public boolean isProcessed() {
		return mProcessed;
	}


}
