package org.denevell.natch.web.jerseymvc.onethread;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class OneThreadPage {
	
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

    	mThreadModule.getThread(start, limit, threadId);
    	return createView(start, limit, mThreadModule.mThreadsList.getNumPosts(), threadId); 
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
    	int numPosts = mPostModule.mAddPost.getThread().getNumPosts();
		if(error) { 
    		return createView(start, limit, numPosts, threadId);
    	} else if(numPosts> start+limit) {
			mResponse.sendRedirect(createUriForNextPagination(start, limit, numPosts).toString());
    		return null;
		} else { 
    		mResponse.sendRedirect(mUriInfo.getRequestUri().toString());
    		return null;
    	}
	}

    @SuppressWarnings("serial")
	private Viewable createView(
			final int start, 
			final int limit,
			final int numPosts, 
			final String threadId
			) throws Exception {
    	
    	int pages = numPosts / limit;
    	pages++;
    	final StringBuffer numbers = new StringBuffer();
    	for (int i = 0; i < pages; i++) {
    		String s = createUriForPagination(i*(limit), limit).toString();
    		String startP = "<a id=\"page"+(i+1)+"\" href=\""+s+"\">";
    		String endP = "</a> | ";
    		numbers.append(startP + String.valueOf(i+1) + endP);
		}

		return new Viewable("/thread_index.mustache", 
				new HashMap<String, String>() {{
					put("addpost", mPostModule.template(mRequest));
					put("thread", mThreadModule.template(mRequest, start, limit, threadId));
					put("next", createUriForNextPagination(start, limit, numPosts).toString());
					put("prev", createUriForPrevPagination(start, limit).toString());
					put("pages", numbers.toString());
				}});
	}

	private URI createUriForNextPagination(int start, int limit, int numPosts) throws URISyntaxException {
		if(!(start+limit > numPosts)) {
			start += limit;
		}
		return createUriForPagination(start, limit);
	}

	private URI createUriForPrevPagination(int start, int limit) throws URISyntaxException {
		start -= limit;
		if(start<0) {
			start=0;
		}
		return createUriForPagination(start, limit);
	}

	private URI createUriForPagination(int start, int limit) throws URISyntaxException {
		URI uri = new URIBuilder(mUriInfo.getRequestUri())
			.setParameter("start", String.valueOf(start))
			.setParameter("limit", String.valueOf(limit))
			.build();
		return uri;

	}
}