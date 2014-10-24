package org.denevell.natch.jerseymvc.screens.thread.delete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("thread/delete")
public class ThreadDeleteConfirmationController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	private String deleteThreadId;

    @GET
    @Template
    public Viewable index(@QueryParam("delete_thread_id") String deleteThreadId) throws Exception {
    	this.deleteThreadId = deleteThreadId;
    	return createTemplate( mRequest, new ThreadDeleteConfirmPresenter(this).onGet(mRequest));
	}

    @POST
    @Template
    public Response indexPost(@FormParam("delete_post_id") String deleteThreadId) throws Exception {
    	this.deleteThreadId = deleteThreadId;
    	return new ThreadDeleteConfirmPresenter(this).onPost(mRequest);
    }
    
    // Getters

	public String getDeleteThreadId() {
		return deleteThreadId;
	}

}