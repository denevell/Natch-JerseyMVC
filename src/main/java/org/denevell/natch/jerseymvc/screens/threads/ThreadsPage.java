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
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.screens.login.modules.LoginLogoutModule;
import org.denevell.natch.jerseymvc.screens.register.modules.RegisterModule;
import org.denevell.natch.jerseymvc.screens.resetpw.modules.ResetPwModule;
import org.denevell.natch.jerseymvc.screens.threads.modules.AddThreadModule;
import org.denevell.natch.jerseymvc.screens.threads.modules.ThreadsModule;
import org.denevell.natch.jerseymvc.screens.threads.modules.ThreadsPaginationModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
@TemplateName("/threads_index.mustache")
public class ThreadsPage extends TemplateController {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	@TemplateModuleInfo("register") 
	public RegisterModule mRegister = new RegisterModule();
	@TemplateModuleInfo("login") 
	public LoginLogoutModule mLogin = new LoginLogoutModule();
	@TemplateModuleInfo("addthread") 
	public AddThreadModule mAddThread = new AddThreadModule();
	@TemplateModuleInfo("pwreset") 
	public ResetPwModule mResetPwModule = new ResetPwModule();
	@TemplateModuleInfo(value="pagination", usedInGET=true) 
	public ThreadsPaginationModule mPaginationModule = new ThreadsPaginationModule();
	@TemplateModuleInfo(value="threads", usedInGET=true) 
	public ThreadsModule mThreadsModule = new ThreadsModule();

    @GET
    @Template
    public Viewable index(
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {
		mThreadsModule.fetchThreads(start, limit);
    	mPaginationModule.calculatePagination(mUriInfo.getRequestUri().toString(), start, limit, mThreadsModule.getNumThreads());
    	storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return viewableFromSession(mRequest);
	}

    @POST
    @Template
    public Response indexPost(
    		@Context UriInfo uriInfo,
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit,
    		@FormParam("username") final String username,
    		@FormParam("password") final String password,
    		@FormParam("recovery_email") final String recoveryEmail,
    		@FormParam("subject") final String subject,
    		@FormParam("content") final String content,
    		@FormParam("tags") final String tags,
    		@FormParam("addthread_active") final String addthreadActive,
    		@FormParam("login_active") final String loginActive,
    		@FormParam("register_active") final String registerActive,
    		@FormParam("logout_active") final String logoutActive,
    		@FormParam("resetpw_active") final String resetPwActive,
    		@FormParam("resetpw_email") final String resetPwEmail
    		) throws Exception {

    	mLogin.logout(logoutActive, mRequest);
    	mRegister.register(registerActive, mRequest, username, password, recoveryEmail);
    	mAddThread.add(addthreadActive, mRequest, subject, content, tags);
    	mResetPwModule.reset(resetPwActive, mRequest, resetPwEmail);
    	mLogin.login(loginActive, mRequest, username, password);
    	if(mLogin.errord()) {
    		mResetPwModule.showForm();
    	}
    	storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return Response.seeOther(mUriInfo.getRequestUri()).build();
	}
    
}