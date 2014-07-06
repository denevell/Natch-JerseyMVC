package org.denevell.natch.jerseymvc.post;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.post.modules.PostModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("post")
public class PostPage {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	PostModule mPostModule = new PostModule();
   	boolean mError = false;

    @GET
    @Path("{postId}")
    @Template
    public Viewable index(
    		@PathParam("postId") int postId 
    		) throws Exception {
    	return createView(postId); 
	}

    @SuppressWarnings("serial")
	private Viewable createView(final int postId) throws Exception {
    	mPostModule.fetchPost(postId);
		return new Viewable("/post_index.mustache", 
				new HashMap<String, String>() {{
					put("post", mPostModule.template(mRequest));
					put("backUrl", new ThreadUrlGenerator().createThreadUrl(mPostModule.mPostOutput.getThreadId()));
				}});
	}

}