package org.denevell.natch.web.jerseymvc.threads;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
	@Context HttpServletResponse mResponse;
	@Context ServletContext mContext;
	@Context UriInfo mUriInfo;
   	boolean mFormError = false;
	RegisterModule mRegister = new RegisterModule();
	LoginLogoutModule mLogin = new LoginLogoutModule();
	AddThreadModule mAddThread = new AddThreadModule();
    ThreadsPaginationModule mPaginationModule = new ThreadsPaginationModule();
	ThreadsModule mThreadsModule = new ThreadsModule();
	ResetPwModule mResetPwModule = new ResetPwModule();

    @GET
    @Template
    public Viewable index(
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {

    	return createView(mUriInfo.getRequestUri().toString(), start, limit); 
	}

    @POST
    @Template
    public Viewable indexPost(
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
    	
    	mFormError |= !mLogin.logout(logoutActive, mRequest);
    	mFormError |= !mRegister.register(registerActive, mRequest, username, password, recoveryEmail);
    	if(mRegister.mRegister.getErrorMessage()!=null && !mRegister.mRegister.getErrorMessage().isEmpty()) {
    		mFormError = true; // Hack due to dodgy api
    	}
    	mFormError |= !mLogin.login(loginActive, mRequest, username, password);
    	mFormError |= !mAddThread.add(addthreadActive, mRequest, subject, content, tags);
    	mFormError |= !mResetPwModule.reset(resetPwActive, mRequest, resetPwEmail);
    	if(mFormError) {
    		return createView(mUriInfo.getRequestUri().toString(), start, limit);
    	} else {
    		mResponse.sendRedirect(mRequest.getRequestURI());
    		return null;
    	}
	}
    
    @SuppressWarnings("serial")
	private Viewable createView(
			final String requestUri,
			final int start, 
			final int limit) throws Exception {

		mThreadsModule.getThreads(start, limit);
    	final int numOfThreads = (int) mThreadsModule.mThreads.getNumOfThreads();

		return new Viewable("/threads_index.mustache", 
				new HashMap<String, String>() {{
					put("login", mLogin.template(mRequest));
					put("pwreset", mResetPwModule.template(mRequest));
					put("register", mRegister.template(mRequest));
					put("addthread", mAddThread.template(mRequest));
					put("threads", mThreadsModule.template(mRequest));
					put("next", mPaginationModule.createUriForNextPagination(requestUri, start, limit, numOfThreads).toString());
					put("prev", mPaginationModule.createUriForPrevPagination(requestUri, start, limit).toString());
					put("pages", mPaginationModule.createPagintionNumbers(requestUri, limit, numOfThreads));
				}});
	}
}