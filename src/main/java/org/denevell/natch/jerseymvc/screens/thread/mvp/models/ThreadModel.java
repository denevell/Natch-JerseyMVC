package org.denevell.natch.jerseymvc.screens.thread.mvp.models;

import org.denevell.natch.jerseymvc.Model;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadModel implements Model<ThreadOutput> {
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private String mThreadId;
	private int mLimit;
	private int mStart;
	private ThreadOutput mThreadsList;

	public ThreadModel(int start, int limit, String threadId) {
		this.mStart = start;
		this.mLimit = limit;
		this.mThreadId = threadId;
	}

	@Override
	public ThreadOutput model() {
		if(mThreadsList!=null) return mThreadsList;
        mThreadsList = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("post").path("thread")
                .path(mThreadId)
                .path(String.valueOf(mStart)).path(String.valueOf(mLimit))
                .request()
                .get(ThreadOutput.class); 	
        return mThreadsList;
	}

}