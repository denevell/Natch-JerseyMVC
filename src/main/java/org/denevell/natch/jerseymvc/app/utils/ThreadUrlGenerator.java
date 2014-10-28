package org.denevell.natch.jerseymvc.app.utils;


public class ThreadUrlGenerator {

	public String createThreadUrl(String threadId) {
		return "/thread/"+threadId;
	}

	public String createThreadUrl(String threadId, int start, int limit) {
		return "/thread/"+threadId+"?start="+start+"&limit="+limit;
	}


}
