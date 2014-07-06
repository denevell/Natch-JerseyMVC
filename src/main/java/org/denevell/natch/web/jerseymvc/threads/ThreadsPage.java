package org.denevell.natch.web.jerseymvc.threads;

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

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.web.jerseymvc.login.modules.LoginLogoutModule;
import org.denevell.natch.web.jerseymvc.register.modules.RegisterModule;
import org.denevell.natch.web.jerseymvc.resetpw.modules.ResetPwModule;
import org.denevell.natch.web.jerseymvc.threads.modules.AddThreadModule;
import org.denevell.natch.web.jerseymvc.threads.modules.ThreadsModule;
import org.denevell.natch.web.jerseymvc.threads.modules.ThreadsPaginationModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsPage {
	
	@Context HttpServletRequest mRequest;
	@Context UriInfo mUriInfo;
	@TemplateModuleInfo("register") public RegisterModule mRegister = new RegisterModule();
	@TemplateModuleInfo("login") public LoginLogoutModule mLogin = new LoginLogoutModule();
	@TemplateModuleInfo("addthread") public AddThreadModule mAddThread = new AddThreadModule();
	@TemplateModuleInfo("pwreset") public ResetPwModule mResetPwModule = new ResetPwModule();
	@TemplateModuleInfo(value="pagination", overwrite=true) 
	public ThreadsPaginationModule mPaginationModule = new ThreadsPaginationModule();
	@TemplateModuleInfo(value="threads", overwrite=true) 
	public ThreadsModule mThreadsModule = new ThreadsModule();

    @GET
    @Template
    public Viewable index(
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {
		mThreadsModule.fetchThreads(start, limit);
    	mPaginationModule.calculatePagination(mUriInfo.getRequestUri().toString(), start, limit, mThreadsModule.getNumThreads());
    	TemplateModule.storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return new Viewable("/threads_index.mustache", 
    			TemplateModule.getThenRemoveSessionTemplateObject(mRequest));
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
    	TemplateModule.storeSessionTemplateObjectFromTemplateModules(mRequest, this);
    	return Response.seeOther(mUriInfo.getRequestUri()).build();
	}
    
}