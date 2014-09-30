package org.denevell.natch.jerseymvc.screens.post.single;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("post")
public class PostSingleController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	private int postId;

    @GET
    @Path("{postId}")
    @Template
    public Viewable index(@PathParam("postId") int postId ) throws Exception { 
    	this.postId = postId;
    	return createTemplate(mRequest, new PostSinglePresenter(this).onGet(mRequest)); 
    }
    
    public int getPostId() {
		return postId;
	}

}