package org.denevell.natch.web.jerseymvc.onethread.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.onethread.io.ThreadOutput;

public class ThreadModule extends TemplateModule {
	
   	public ThreadOutput mThreadsList;

	@SuppressWarnings("unused")
	public String template(
			final HttpServletRequest request,
			final int start,
			final int limit,
			final String threadId) throws IOException {
		mThreadsList = getThread(start, limit, threadId);
		return createTemplate("thread.mustache", 
			new Object() {
				ThreadOutput thread = mThreadsList;
        	});
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
