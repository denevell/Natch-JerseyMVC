package org.denevell.natch.jerseymvc.screens.thread.edit;

import javax.servlet.http.HttpServletRequest;
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

@Path("thread/edit")
public class ThreadEditController extends TemplateController {
  
  @Context HttpServletRequest mRequest;
  @Context UriInfo mUriInfo;
  public int postEditId;
  public String content;
  public String subject;
  public String threadId;
  public String tags;

    @GET
    @Path("{post_edit}")
    @Template
    public Viewable index(
        @QueryParam("thread") String threadId,
        @PathParam("post_edit") int postEditId
        ) throws Exception {
      this.postEditId = postEditId;
      this.threadId = threadId;
      return createTemplate(
          new ThreadEditPresenter(this).onGet(mRequest)
      );
  }

    @POST
    @Path(value="{post_edit}")
    @Template
    public Response indexPost(
        @Context UriInfo uriInfo,
        @QueryParam("thread") String threadId,
        @PathParam("post_edit") int postEditId,
        @FormParam("subject") final String subject,
        @FormParam("tags") final String tags,
        @FormParam("content") final String content 
        ) throws Exception {
    this.postEditId = postEditId;
    this.threadId = threadId;
    this.tags = tags;
    this.subject = subject;
    this.content = content;
    return new ThreadEditPresenter(this).onPost(mRequest);
  }
    
}
