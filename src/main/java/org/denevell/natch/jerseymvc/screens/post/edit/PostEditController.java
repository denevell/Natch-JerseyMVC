package org.denevell.natch.jerseymvc.screens.post.edit;

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

@Path("post/edit")
public class PostEditController extends TemplateController {
  
  @Context HttpServletRequest mRequest;
  @Context UriInfo mUriInfo;
  public int postEditId;
  public String content;
  public String threadId;
  public int limit;
  public int start;

    @GET
    @Path("{post_edit}")
    @Template
    public Viewable index(
        @QueryParam("start") int start,
        @QueryParam("limit") int limit,
        @QueryParam("thread") String threadId,
        @PathParam("post_edit") int postEditId
        ) throws Exception {
      this.start = start;
      this.limit = limit;
      this.postEditId = postEditId;
      this.threadId = threadId;
      return createTemplate(
          new PostEditPresenter(this).onGet(mRequest)
      );
  }

    @POST
    @Path(value="{post_edit}")
    @Template
    public Response indexPost(
        @Context UriInfo uriInfo,
        @QueryParam("start") int start,
        @QueryParam("limit") int limit,
        @QueryParam("thread") String threadId,
        @PathParam("post_edit") int postEditId,
        @FormParam("content") final String content 
        ) throws Exception {
    this.start = start;
    this.limit = limit;
    this.postEditId = postEditId;
    this.threadId = threadId;
    this.content = content;
    return new PostEditPresenter(this).onPost(mRequest);
  }
    
}
