package org.denevell.natch.web.jerseymvc.threads.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.threads.io.ThreadsOutput;

public class ThreadsModule extends TemplateModule {
	
   	public ThreadsOutput mThreads;

	@SuppressWarnings("unused")
	public String template(
			final HttpServletRequest request) throws IOException {
		return createTemplate("threads.mustache", 
			new Object() {
				ThreadsOutput threads = mThreads;
        	});
	}
	
	public ThreadsOutput getThreads(int start, int limit) {
        mThreads = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
        return mThreads;
	}
	

}
