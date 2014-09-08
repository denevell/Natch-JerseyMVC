package org.denevell.natch.jerseymvc.app.services;

import org.denevell.natch.jerseymvc.app.models.ThreadsOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadsService {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
   	public ThreadsOutput mThreads;

	public ThreadsOutput getThreads() {
		return mThreads;
	}
	
	public int getNumThreads() {
		if(mThreads==null) return 0;
		return (int) mThreads.getNumOfThreads();
	}

	public void fetchThreads(int start, int limit) {
	    fetchThreads(start, limit, null);
	}

  public void fetchThreads(int start, int limit, String tag) {
    if(tag==null || tag.length()==0) {
        mThreads = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
    } else {
        mThreads = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(tag).path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
      
    }
  }
	

}
