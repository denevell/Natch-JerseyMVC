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
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("thread")
public class ThreadPage {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	private ThreadModule mThreadModule = new ThreadModule();
	private AddPostModule mPostModule = new AddPostModule();

    @GET
    @Path("{threadId}")
    @Template
    public Viewable index(
    		@PathParam("threadId") String threadId,
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit
    		) throws Exception {

    	return createView(start, limit, threadId); 
	}

    @POST
    @Path("{threadId}")
    @Template
    public Viewable indexPost(
    		@PathParam("threadId") String threadId,
    		@PathParam("start") @DefaultValue("0") int start,
    		@PathParam("limit") @DefaultValue("10") int limit,
    		@FormParam("content") final String content,
    		@FormParam("addpost_active") final String addPostActive
    		) throws Exception {
    	
    	mPostModule.add(addPostActive, mRequest, content, threadId);
    	return createView(start, limit, threadId);
	}

    @SuppressWarnings("serial")
	private Viewable createView(
			final int start, 
			final int limit,
			final String threadId
			) throws Exception {
		return new Viewable("/thread_index.mustache", 
				new HashMap<String, String>() {{
					put("addpost", mPostModule.template(mRequest));
					put("thread", mThreadModule.template(mRequest, 0, 10, threadId));
				}});

	}
}