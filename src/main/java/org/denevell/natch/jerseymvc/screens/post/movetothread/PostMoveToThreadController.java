package org.denevell.natch.jerseymvc.screens.post.movetothread;

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

@Path("post/movetothread")
public class PostMoveToThreadController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	public int postId;
	public String threadId;
	public String content;
	public String subject;

    @GET
    @Path("{post}")
    @Template
    public Viewable index(
    		@QueryParam("thread") String threadId,
    		@PathParam("post") int postId) throws Exception {
    	this.postId = postId;
    	this.threadId = threadId;
		return createTemplate(mRequest, new PostMoveToThreadPresenter(this).onGet(mRequest));
	}

    @POST
    @Path("{post}")
    @Template
    public Response indexPost(
    		@PathParam("post") int postId,
    		@FormParam("thread") String threadId,
    		@FormParam("content") String content,
    		@FormParam("subject") String subject 
    		) throws Exception {
    	this.postId = postId;
    	this.threadId = threadId;
    	this.content = content;
    	this.subject = subject;
    	return new PostMoveToThreadPresenter(this).onPost(mRequest);
    }
    
}