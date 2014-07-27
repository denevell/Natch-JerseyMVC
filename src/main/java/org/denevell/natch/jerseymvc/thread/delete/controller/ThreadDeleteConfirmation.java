package org.denevell.natch.jerseymvc.thread.delete.controller;

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
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.app.urls.MainPageUrlGenerator;
import org.denevell.natch.jerseymvc.thread.delete.modules.DeleteThreadModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("thread/delete")
@TemplateName("/thread/delete/confirm.mustache")
public class ThreadDeleteConfirmation extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context UriInfo mUriInfo;
	@TemplateModuleInfo(value="deletethread") 
	public DeleteThreadModule mDeleteThreadModule = new DeleteThreadModule();

    @GET
    @Template
    public Viewable index(@QueryParam("delete_thread_id") final String deleteThreadId) throws Exception {
    	storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	addToTemplateSession(mRequest, "id", deleteThreadId);
    	addToTemplateSession(mRequest, "loggedin", mRequest.getSession(true).getAttribute("loggedin")!=null);
    	return viewableFromSession(mRequest);
	}

    @POST
    @Template
    public Response indexPost(@FormParam("delete_thread_id") final String deleteThreadId) throws Exception {
    	mDeleteThreadModule.delete(mRequest, deleteThreadId);
   		if(mDeleteThreadModule.getSuccessful()) {
    		return Response.seeOther(new MainPageUrlGenerator().build()).build();
   		} else {
   			addToTemplateSession(mRequest, "errorMessage", mDeleteThreadModule.getDeletethread().getErrorMessage());
   			return Response.seeOther(mUriInfo.getRequestUri()).build();
   		}
    }
}