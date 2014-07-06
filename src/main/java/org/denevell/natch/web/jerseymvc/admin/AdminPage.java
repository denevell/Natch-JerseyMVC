package org.denevell.natch.web.jerseymvc.admin;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.web.jerseymvc.admin.modules.AdminModule;
import org.denevell.natch.web.jerseymvc.admin.modules.PwChangeModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("admin")
public class AdminPage {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	@TemplateModuleInfo(value="users", overwrite=true) public AdminModule mAdmin = new AdminModule();
	@TemplateModuleInfo("pwchange") public PwChangeModule mPwChange = new PwChangeModule();

    @GET
    @Template
    public Viewable index() throws Exception {
    	mAdmin.getUsers((String)mRequest.getSession(true).getAttribute("authkey"));
    	TemplateModule.storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return new Viewable("/admin_index.mustache", 
    			TemplateModule.getThenRemoveSessionTemplateObject(mRequest));
    }

    @POST
    @Template
    public Response post(
    		@FormParam("changepw_username") String changePwUsername,
    		@FormParam("changepw_password") String chnagePwNewPass,
    		@FormParam("changepw_active") String changePwActive
    		) throws Exception {
    	mPwChange.changePw(changePwActive, mRequest, changePwUsername, chnagePwNewPass);
    	TemplateModule.storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return Response.seeOther(mUriInfo.getRequestUri()).build();
    }
}