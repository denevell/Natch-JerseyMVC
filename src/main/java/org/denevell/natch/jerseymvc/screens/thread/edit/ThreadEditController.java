package org.denevell.natch.jerseymvc.screens.thread.edit;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
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

@Path("thread/edit")
public class ThreadEditController extends TemplateController {
  
  @Context HttpServletRequest mRequest;
  @Context UriInfo mUriInfo;
  public int start;
  public int limit;
  public String somePostActive;
  public String somePostValue;
  public String threadEditId;

    @GET
    @Path(value="{thread_edit}")
    @Template
    public Viewable index(
        @QueryParam("thread_edit") String threadEditId,
        @QueryParam("start") @DefaultValue("0") int start,
        @QueryParam("limit") @DefaultValue("10") int limit
        ) throws Exception {
    this.threadEditId = threadEditId;
    this.start = start;
    this.limit = limit;
      return createTemplate(
          new ThreadEditPresenter(this).onGet(mRequest)
      );
  }

    @POST
    @Path(value="{thread_edit}")
    @Template
    public Response indexPost(
        @Context UriInfo uriInfo,
        @QueryParam("thread_edit") String threadEditId,
        @QueryParam("start") @DefaultValue("0") int start,
        @QueryParam("limit") @DefaultValue("10") int limit,
        @FormParam("somepost_active") final String somePostActive,
        @FormParam("somepost_value") final String somePostValue 
        ) throws Exception {

    this.threadEditId = threadEditId;
    this.start = start;
    this.limit = limit;
    this.somePostActive = somePostActive;
    this.somePostValue = somePostValue;
    return new ThreadEditPresenter(this).onPost(mRequest);
  }
    
}
