package org.denevell.natch.jerseymvc.services;

import org.denevell.natch.jerseymvc.ManifestVarsListener;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ThreadService {
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private String mThreadId;
	private int mLimit;
	private int mStart;
	private ThreadOutput mThreadsList;

	public ThreadService(int start, int limit, String threadId) {
		this.mStart = start;
		this.mLimit = limit;
		this.mThreadId = threadId;
	}

	public ThreadOutput model() {
		if(mThreadsList!=null) return mThreadsList;
        mThreadsList = sService
                .target(ManifestVarsListener.getValue("rest_service"))
                .path("rest").path("post").path("thread")
                .path(mThreadId)
                .path(String.valueOf(mStart)).path(String.valueOf(mLimit))
                .request()
                .get(ThreadOutput.class); 	
        return mThreadsList;
	}

}