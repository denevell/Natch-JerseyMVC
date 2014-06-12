package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.io.ThreadsOutput;

public class ThreadsModule extends Module {
	
   	@SuppressWarnings("unused")
	public String template(
			final HttpServletRequest request,
			final int start,
			final int limit) throws IOException {
		final ThreadsOutput threadsList = getThreads(start, limit);
		return createTemplate("threads.mustache", 
			new Object() {
				ThreadsOutput threads = threadsList;
        	});
	}
	
	public static ThreadsOutput getThreads(int start, int limit) {
        return sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
	}
	

}
