package org.denevell.natch.web.jerseymvc.threads.modules;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.threads.io.ThreadsOutput;

public class ThreadsModule extends TemplateModule {
	
   	public ThreadsOutput mThreads;

   	public ThreadsModule() {
   		super("threads.mustache");
	}

	public ThreadsOutput getThreads() {
		return mThreads;
	}
	
	public int getNumThreads() {
		if(mThreads==null) return 0;
		return (int) mThreads.getNumOfThreads();
	}

	public ThreadsOutput fetchThreads(int start, int limit) {
        mThreads = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
        return mThreads;
	}
	

}
