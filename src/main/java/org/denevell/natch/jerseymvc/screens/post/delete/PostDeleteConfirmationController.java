package org.denevell.natch.jerseymvc.screens.post.delete;

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

@Path("post/delete")
public class PostDeleteConfirmationController extends TemplateController {

  @Context HttpServletRequest mRequest;
  @Context HttpServletResponse mResponse;
  @Context UriInfo mUriInfo;
  public String deletePostId;
  public String parentThreadId;
  public int start;
  public int limit;

  @GET
  @Path("{delete_post_id}")
  @Template
  public Viewable index(
      @PathParam("delete_post_id") String deletePostId,
      @QueryParam("thread") String parentThreadId,
      @QueryParam("start") int start, 
      @QueryParam("limit") int limit)
      throws Exception {
    this.deletePostId = deletePostId;
    this.parentThreadId = parentThreadId;
    this.start = start;
    this.limit = limit;
    return createTemplate(mRequest,
        new PostDeleteConfirmPresenter(this).onGet(mRequest));
  }

  @POST
  @Path("{delete_post_id}")
  @Template
  public Response indexPost(
      @FormParam("delete_post_id") String deleteThreadId,
      @QueryParam("thread") String parentThreadId,
      @QueryParam("start") int start, 
      @QueryParam("limit") int limit)
      throws Exception {
    this.deletePostId = deleteThreadId;
    this.parentThreadId = parentThreadId;
    this.start = start;
    this.limit = limit;
    return new PostDeleteConfirmPresenter(this).onPost(mRequest, null);
  }

}