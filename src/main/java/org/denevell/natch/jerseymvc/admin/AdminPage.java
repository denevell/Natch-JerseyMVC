package org.denevell.natch.jerseymvc.admin;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.jerseymvc.admin.modules.AdminModule;
import org.denevell.natch.jerseymvc.admin.modules.PwChangeModule;
import org.denevell.natch.jerseymvc.app.template.TemplateController;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@TemplateName("/admin_index.mustache")
@Path("admin")
public class AdminPage extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	@TemplateModuleInfo(value="users", usedInGET=true) public AdminModule mAdmin = new AdminModule();
	@TemplateModuleInfo("pwchange") public PwChangeModule mPwChange = new PwChangeModule();

    @GET
    @Template
    public Viewable index() throws Exception {
    	mAdmin.getUsers((String)mRequest.getSession(true).getAttribute("authkey"));
    	storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return viewableFromSession(mRequest);
    }

    @POST
    @Template
    public Response post(
    		@FormParam("changepw_username") String changePwUsername,
    		@FormParam("changepw_password") String chnagePwNewPass,
    		@FormParam("changepw_active") String changePwActive
    		) throws Exception {
    	mPwChange.changePw(changePwActive, mRequest, changePwUsername, chnagePwNewPass);
    	storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return Response.seeOther(mUriInfo.getRequestUri()).build();
    }
}