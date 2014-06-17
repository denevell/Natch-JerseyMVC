package org.denevell.natch.web.jerseymvc.onethread;

import java.net.URI;
import java.net.URISyntaxException;
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

import org.apache.http.client.utils.URIBuilder;
import org.denevell.natch.web.jerseymvc.onethread.modules.AddPostModule;
import org.denevell.natch.web.jerseymvc.onethread.modules.ThreadModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("thread")
public class ThreadPage {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	private ThreadModule mThreadModule = new ThreadModule();
	private AddPostModule mPostModule = new AddPostModule();

    @GET
    @Path("{threadId}")
    @Template
    public Viewable index(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {

    	return createView(start, limit, threadId); 
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
    	
    	boolean error = false;
    	error = !mPostModule.add(addPostActive, mRequest, content, threadId);
    	if(error) { 
    		return createView(start, limit, threadId);
    	} else if(mPostModule.mAddPost.getThread().getNumPosts()> start+limit) {
			mResponse.sendRedirect(createUriForNextPagination(start, limit).toString());
    		return null;
		} else { 
    		mResponse.sendRedirect(mUriInfo.getRequestUri().toString());
    		return null;
    	}
	}

	private URI createUriForNextPagination(int start, int limit)
			throws URISyntaxException {
		URI uri = new URIBuilder(mUriInfo.getRequestUri())
			.setParameter("start", String.valueOf(start+limit))
			.setParameter("limit", String.valueOf(limit))
			.build();
		return uri;
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
					put("thread", mThreadModule.template(mRequest, start, limit, threadId));
				}});

	}
}