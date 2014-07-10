package org.denevell.natch.jerseymvc.thread.view.modules;

import javax.servlet.http.HttpServletRequest;

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
   	
   	@Override
   	protected void modifyTemplateDataBeforeRender(HttpServletRequest request) {
   		super.modifyTemplateDataBeforeRender(request);
    	Object name = request.getSession(true).getAttribute("name");
    	boolean correctUser;
		if(name!=null) {
    		correctUser = name.equals(mThreadsList.getAuthor());
    	} else {
    		correctUser = false;
    	}
    	Object admin = request.getSession(true).getAttribute("admin");
		mThreadsList.setLoggedinCorrectly( (admin!=null && ((boolean)admin)==true) || correctUser);
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
