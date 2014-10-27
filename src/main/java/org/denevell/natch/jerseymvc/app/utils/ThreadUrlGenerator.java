package org.denevell.natch.jerseymvc.app.utils;


public class ThreadUrlGenerator {

	public String createThreadUrl(String threadId) {
		return "/app/thread/"+threadId;
	}

	public String createThreadUrl(String threadId, int start, int limit) {
		return "/app/thread/"+threadId+"?start="+start+"&limit="+limit;
	}


}
