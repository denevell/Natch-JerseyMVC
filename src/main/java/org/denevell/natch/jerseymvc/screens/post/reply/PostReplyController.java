package org.denevell.natch.jerseymvc.screens.post.reply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("post/reply")
public class PostReplyController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	public int postId;
	public int start;
	public int limit;
	public String threadId;
	public String content;

    @GET
    @Path("{post}")
    @Template
    public Viewable index(
    		@QueryParam("thread") String threadId,
    		@QueryParam("start") int start,
    		@QueryParam("limit") int limit,
    		@PathParam("post") int postId) throws Exception {
    	this.postId = postId;
    	this.start = start;
    	this.limit = limit;
    	this.threadId = threadId;
		return createTemplate(new PostReplyPresenter(this).onGet(mRequest));
	}

    @POST
    @Path("{post}")
    @Template
    public Response indexPost(
    		@PathParam("post") int postId,
    		@QueryParam("start") int start,
    		@QueryParam("limit") int limit,
    		@FormParam("thread") String threadId,
    		@FormParam("content") String content) throws Exception {
    	this.start = start;
    	this.limit = limit;
    	this.postId = postId;
    	this.threadId = threadId;
    	this.content = content;
    	return new PostReplyPresenter(this).onPost(mRequest);
    }
    
}