package org.denevell.natch.jerseymvc.thread.view.modules;

import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.thread.view.io.ThreadOutput;

public class ThreadModule extends TemplateModule {
	
   	public ThreadOutput mThreadsList;
   	
   	public ThreadModule() {
   		super("thread.mustache");
	}
   	
   	public ThreadOutput getThread() {
   		return mThreadsList;
   	}
   	
	public ThreadOutput fetchThread(int start, int limit, String threadId) {
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
