package org.denevell.natch.web.jerseymvc.onethread.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.onethread.io.ThreadOutput;

public class ThreadModule extends TemplateModule {
	
   	@SuppressWarnings("unused")
	public String template(
			final HttpServletRequest request,
			final int start,
			final int limit,
			final String threadId) throws IOException {
		final ThreadOutput threadsList = getThread(start, limit, threadId);
		return createTemplate("thread.mustache", 
			new Object() {
				ThreadOutput thread = threadsList;
        	});
	}
	
	public ThreadOutput getThread(int start, int limit, String threadId) {
        return sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("post").path("thread")
                .path(threadId)
                .path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadOutput.class); 	
	}
	

}
