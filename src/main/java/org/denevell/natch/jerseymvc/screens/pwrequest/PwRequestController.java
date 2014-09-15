package org.denevell.natch.jerseymvc.screens.pwrequest;

import javax.servlet.http.HttpServletRequest;
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

@Path("pwrequest")
public class PwRequestController extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	public String resetPwActive;
	public String resetPwEmail;

    @GET
    @Template
    public Viewable index() throws Exception {
    	return createTemplate(
    			new PwRequestPresenter(this).onGet(mRequest)
    	);
	}

    @POST
    @Template
    public Response indexPost(
    		@Context UriInfo uriInfo,
    		@FormParam("resetpw_active") final String resetPwActive,
    		@FormParam("resetpw_email") final String resetPwEmail
    		) throws Exception {
		this.resetPwActive = resetPwActive;
		this.resetPwEmail = resetPwEmail;
		return new PwRequestPresenter(this).onPost(mRequest);
	}
    
}