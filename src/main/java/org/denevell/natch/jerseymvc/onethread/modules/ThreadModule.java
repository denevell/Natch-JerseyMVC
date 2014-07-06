package org.denevell.natch.jerseymvc.onethread.modules;

import org.denevell.natch.jerseymvc.TemplateModule;
import org.denevell.natch.jerseymvc.onethread.io.ThreadOutput;

public class ThreadModule extends TemplateModule {
	
   	public ThreadOutput mThreadsList;
   	
   	public ThreadModule() {
   		super("thread.mustache");
	}
   	
   	public ThreadOutput getThread() {
   		return mThreadsList;
   	}
   	
	public ThreadOutput getThread(int start, int limit, String threadId) {
        mThreadsList = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("post").path("thread")
                .path(threadId)
                .path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadOutput.class); 	
        return mThreadsList;
	}

}
