package org.denevell.natch.jerseymvc.screens.threads;

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

@Path("index")
public class ThreadsController extends TemplateController {

  @Context HttpServletRequest mRequest;
  @Context UriInfo mUriInfo;
  public int start;
  public int limit;
  public String subject;
  public String content;
  public String tags;
  public String tag;

  @GET
  @Template
  public Viewable index(
      @QueryParam("start") @DefaultValue("0") int start,
      @QueryParam("limit") @DefaultValue("10") int limit,
      @QueryParam("tag") String tag) throws Exception {
    this.start = start;
    this.limit = limit;
    this.tag = tag;
    return createTemplate(mRequest, new ThreadsPresenter(this).onGet(mRequest));
  }

  @POST
  @Template
  public Response indexPost(
      @QueryParam("start") @DefaultValue("0") int start,
      @QueryParam("limit") @DefaultValue("10") int limit,
      @FormParam("subject") String subject,
      @FormParam("content") String content,
      @FormParam("tags") String tags) throws Exception {
    this.start = start;
    this.limit = limit;
    this.subject = subject;
    this.content = content;
    this.tags = tags;
    return new ThreadsPresenter(this).onPost(mRequest, null);
  }

}