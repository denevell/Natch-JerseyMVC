package org.denevell.natch.jerseymvc.services;

import java.util.ArrayList;
import java.util.List;

import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
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
		return (int) mThreads.numOfThreads;
	}

	public void fetchThreads(int start, int limit) {
	    fetchThreads(start, limit, null);
	}

  public void fetchThreads(int start, int limit, String tag) {
    if(tag==null || tag.length()==0) {
        mThreads = sService
                .target(ListenerManifestVars.getValue("rest_service"))
                .path("rest").path("thread").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
    } else {
        mThreads = sService
                .target(ListenerManifestVars.getValue("rest_service"))
                .path("rest").path("thread").path("bytag").path(tag).path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
      
    }
  }

  public static class ThreadsOutput {
    public long numOfThreads;
    public List<ThreadOutput> threads = new ArrayList<ThreadOutput>();
    int i = 0;
    int iterate() {
      return i++;
    }
}
	

}
