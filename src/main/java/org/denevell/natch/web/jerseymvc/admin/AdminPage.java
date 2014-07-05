package org.denevell.natch.web.jerseymvc.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.web.jerseymvc.ViewableFromSession;
import org.denevell.natch.web.jerseymvc.admin.modules.AdminModule;
import org.denevell.natch.web.jerseymvc.admin.modules.PwChangeModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import static org.denevell.natch.web.jerseymvc.SessionSetter.setSession;
import static org.denevell.natch.web.jerseymvc.SessionSetter.sessionAlreadySet;

@Path("admin")
public class AdminPage {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	private AdminModule mAdmin = new AdminModule();
	private PwChangeModule mPwChange = new PwChangeModule();

    @GET
    @Template
    public Viewable index() throws Exception {
    	createTemplate();
		return new ViewableFromSession("/admin_index.mustache", mRequest.getSession());
    }

    @POST
    @Template
    public Response post(
    		@FormParam("changepw_username") String changePwUsername,
    		@FormParam("changepw_password") String chnagePwNewPass,
    		@FormParam("changepw_active") String changePwActive
    		) throws Exception {
    	mPwChange.changePw(changePwActive, mRequest, changePwUsername, chnagePwNewPass);
    	createTemplate();
    	return Response.seeOther(mUriInfo.getRequestUri()).build();
    }

	private void createTemplate() throws Exception {
    	if(sessionAlreadySet(mRequest)) return;
    	setSession(mRequest)
			.put("users", mAdmin.template(mRequest))
			.put("pwchange", mPwChange.template(mRequest))
			.build();
	}
}