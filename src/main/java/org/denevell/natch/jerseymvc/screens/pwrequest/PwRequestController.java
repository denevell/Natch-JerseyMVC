package org.denevell.natch.jerseymvc.screens.pwrequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("pwrequest_xx`")
public class PwRequestController extends TemplateController implements PwRequestVars {

  @Context HttpServletRequest mRequest;
  @Context HttpServletResponse mReponse;
  @Context UriInfo mUriInfo;
  public String resetPwEmail;

  @GET
  @Template
  public Viewable index() throws Exception {
    return createTemplate(mRequest, new PwRequestPresenter(this, mRequest).onGet(mRequest));
  }

  @POST
  @Template
  public Response indexPost(
      @Context UriInfo uriInfo,
      @FormParam("resetpw_email") String resetPwEmail) throws Exception {
    this.resetPwEmail = resetPwEmail;
    new PwRequestPresenter(this, mRequest).onPost(mRequest, mReponse);
    return null;
  }

  @Override
  public String getRestPwEmail() {
    return resetPwEmail;
  }

}