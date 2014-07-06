package org.denevell.natch.web.jerseymvc.onethread;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.web.jerseymvc.onethread.modules.AddPostModule;
import org.denevell.natch.web.jerseymvc.onethread.modules.OneThreadPaginationModule;
import org.denevell.natch.web.jerseymvc.onethread.modules.ThreadModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("thread")
public class OneThreadPage {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	ThreadModule mThreadModule = new ThreadModule();
	AddPostModule mPostModule = new AddPostModule();
	OneThreadPaginationModule mPaginationModule = new OneThreadPaginationModule();
   	boolean mError = false;

    @GET
    @Path("{threadId}")
    @Template
    public Viewable index(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {

    	mThreadModule.getThread(start, limit, threadId);
    	return createView(mUriInfo.getRequestUri().toString(), start, limit, mThreadModule.mThreadsList.getNumPosts(), threadId); 
	}

    @POST
    @Path("{threadId}")
    @Template
    public Viewable indexPost(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit,
    		@FormParam("content") final String content,
    		@FormParam("addpost_active") final String addPostActive
    		) throws Exception {
    	
    	mError = !mPostModule.add(addPostActive, mRequest, content, threadId);
    	int numPosts = 0;
    	if(mPostModule.mAddPost.getThread()==null) {
    		numPosts = mThreadModule.getThread(start, limit, threadId).getNumPosts();
    	} else {
    		numPosts = mPostModule.mAddPost.getThread().getNumPosts();
    	}
		if(mError) { 
    		return createView(mUriInfo.getRequestUri().toString(), start, limit, numPosts, threadId);
    	} else if(numPosts> start+limit) {
			mResponse.sendRedirect(mPaginationModule.createUriForNextPagination(mUriInfo.getRequestUri().toString(), start, limit, numPosts));
    		return null;
		} else { 
    		mResponse.sendRedirect(mUriInfo.getRequestUri().toString());
    		return null;
    	}
	}

    @SuppressWarnings("serial")
	private Viewable createView(
			final String requestUri,
			final int start, 
			final int limit,
			final int numPosts, 
			final String threadId) throws Exception {
    	mThreadModule.getThread(start, limit, threadId);
		return new Viewable("/thread_index.mustache", 
				new HashMap<String, String>() {{
					put("addpost", mPostModule.template(mRequest));
					put("thread", mThreadModule.template(mRequest));
					put("next", mPaginationModule.createUriForNextPagination(requestUri, start, limit, numPosts).toString());
					put("prev", mPaginationModule.createUriForPrevPagination(requestUri, start, limit).toString());
					put("pages", mPaginationModule.createPagintionNumbers(requestUri, limit, numPosts));
				}});
	}

}