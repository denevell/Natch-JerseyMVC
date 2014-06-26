package org.denevell.natch.web.jerseymvc.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.denevell.natch.web.jerseymvc.admin.modules.AdminModule;
import org.denevell.natch.web.jerseymvc.admin.modules.PwChangeModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("admin")
public class AdminPage {
	
	@Context HttpServletRequest mRequest;
	private AdminModule mAdmin = new AdminModule();
	private PwChangeModule mPwChange = new PwChangeModule();

    @GET
    @Template
    public Viewable index() throws Exception {
    	return createView(); 
    }

    @POST
    @Template
    public Viewable post(
    		@FormParam("changepw_username") String changePwUsername,
    		@FormParam("changepw_password") String chnagePwNewPass,
    		@FormParam("changepw_active") String changePwActive
    		) throws Exception {
    		mPwChange.changePw(changePwActive, mRequest, changePwUsername, chnagePwNewPass);
    	return createView(); 
    }

    @SuppressWarnings("serial")
	private Viewable createView() throws Exception {
		return new Viewable("/admin_index", 
				new HashMap<String, String>() {{
					put("users", mAdmin.template(mRequest));
					put("pwchange", mPwChange.template(mRequest));
				}});
	}
}