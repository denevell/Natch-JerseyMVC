package org.denevell.natch.jerseymvc.screens.post.reply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @GET
    @Path("{post}")
    @Template
    public Viewable index(
    		@PathParam("post") final String postId
    		) throws Exception {
    	return createTemplate(
    			new PostReplyPresenter(this).onGet(mRequest)
    			);
	}

    @POST
    @Template
    public Response indexPost() throws Exception {
    	return new PostReplyPresenter(this).onPost(mRequest);
    }
    
}