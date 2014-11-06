package org.denevell.natch.jerseymvc.app.utils;

import javax.servlet.http.HttpServletRequest;


public class ThreadUrlGenerator {

	public String createThreadUrl(HttpServletRequest req, String threadId) {
		return "/thread/"+threadId;
	}

	public String createThreadUrl(HttpServletRequest req, String threadId, int start, int limit) {
		return "/thread/"+threadId+"?start="+start+"&limit="+limit;
	}


}
