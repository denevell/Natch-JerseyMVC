package org.denevell.natch.jerseymvc.screens.thread;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("thread")
public class ThreadController extends TemplateController{
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	public int start;
	public int limit;
	public String threadId;
	public String content;
	
    @GET
    @Path("{threadId}")
    @Template
    public Viewable index(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit) throws Exception {
    	this.start = start;
    	this.limit = limit;
    	this.threadId = threadId;

    	return createTemplate(
    			new ThreadPresenter(this).onGet(mRequest)
    	);
	}

    @POST
    @Path("{threadId}")
    @Template
    public Response indexPost(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit,
    		@FormParam("content") final String content) throws Exception {
    	this.threadId = threadId;
    	this.start = start;
    	this.limit = limit;
    	this.content = content;
    	
		return new ThreadPresenter(this).onPost(mRequest);
    }
    
}