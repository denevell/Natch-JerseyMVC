package org.denevell.natch.jerseymvc.thread.view;

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
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.thread.view.modules.AddPostModule;
import org.denevell.natch.jerseymvc.thread.view.modules.ThreadModule;
import org.denevell.natch.jerseymvc.threads.modules.ThreadsPaginationModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@TemplateName("/thread_index.mustache")
@Path("thread")
public class ThreadView extends TemplateController{
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	@TemplateModuleInfo(value="addpost") 
	public AddPostModule mPostModule = new AddPostModule();
	@TemplateModuleInfo(value="thread", usedInGET=true) 
	public ThreadModule mThreadModule = new ThreadModule();
	@TemplateModuleInfo(value="pagination", usedInGET=true) 
	public ThreadsPaginationModule mPaginationModule = new ThreadsPaginationModule();

    @GET
    @Path("{threadId}")
    @Template
    public Viewable index(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {
    	mThreadModule.fetchThread(start, limit, threadId);
    	mPaginationModule.calculatePagination(mUriInfo.getRequestUri().toString(), start, limit, mThreadModule.getThread().getNumPosts());
    	storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return viewableFromSession(mRequest);
	}

    @POST
    @Path("{threadId}")
    @Template
    public Response indexPost(
    		@PathParam("threadId") String threadId,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit,
    		@FormParam("content") final String content,
    		@FormParam("addpost_active") final String addPostActive
    		) throws Exception {
    	mPostModule.add(addPostActive, mRequest, content, threadId);
    	mPaginationModule.calculatePagination(mUriInfo.getRequestUri().toString(), start, limit, mPostModule.getNumPosts());
   		storeSessionTemplateObjectFromTemplateModules(mRequest, this);
   		if(mPostModule.getNumPosts() > start+limit) { 
    		return Response.seeOther(mPaginationModule.getNext()).build();
    	} else {
    		return Response.seeOther(mUriInfo.getRequestUri()).build();
    	}
    }
}