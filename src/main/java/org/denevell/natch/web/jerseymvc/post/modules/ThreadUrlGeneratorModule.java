package org.denevell.natch.web.jerseymvc.post.modules;


public class ThreadUrlGeneratorModule {

	public String createThreadUrl(String threadId) {
		return "/thread/"+threadId;
	}


}
