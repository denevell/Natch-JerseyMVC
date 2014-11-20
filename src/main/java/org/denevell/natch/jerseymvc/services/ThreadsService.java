package org.denevell.natch.jerseymvc.services;

import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadsOutput;
import org.denevell.natch.jerseymvc.utils.ListenerManifestVars;
import org.denevell.natch.jerseymvc.utils.Serv;

public class ThreadsService {
	
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
        mThreads = Serv.service 
                .target(ListenerManifestVars.getValue("rest_service"))
                .path("rest").path("thread").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
    } else {
        mThreads = Serv.service 
                .target(ListenerManifestVars.getValue("rest_service"))
                .path("rest").path("thread").path("bytag").path(tag).path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
      
    }
  }

}
