package org.denevell.natch.web.jerseymvc.threads;

import java.net.URI;
import java.net.URISyntaxException;
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

import org.apache.http.client.utils.URIBuilder;
import org.denevell.natch.web.jerseymvc.login.modules.LoginLogoutModule;
import org.denevell.natch.web.jerseymvc.register.modules.RegisterModule;
import org.denevell.natch.web.jerseymvc.threads.modules.AddThreadModule;
import org.denevell.natch.web.jerseymvc.threads.modules.ThreadsModule;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("index")
public class ThreadsPage {
	
	@Context HttpServletRequest mRequest;
	@Context HttpServletResponse mResponse;
	@Context ServletContext mContext;
	@Context UriInfo mUriInfo;
	RegisterModule mRegister = new RegisterModule();
	LoginLogoutModule mLogin = new LoginLogoutModule();
	AddThreadModule mAddThread = new AddThreadModule();

    @GET
    @Template
    public Viewable index(
    		@QueryParam("start") @DefaultValue("0") int start,
    		@QueryParam("limit") @DefaultValue("10") int limit
    		) throws Exception {

    	return createView(start, limit); 
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
    		@FormParam("logout_active") final String logoutActive 
    		) throws Exception {
    	
    	boolean error = false;
    	error |= !mLogin.logout(logoutActive, mRequest);
    	error |= !mRegister.register(registerActive, mRequest, username, password, recoveryEmail);
    	if(mRegister.mRegister.getErrorMessage()!=null && !mRegister.mRegister.getErrorMessage().isEmpty()) {
    		error = true; // Hack due to dodgy api
    	}
    	error |= !mLogin.login(loginActive, mRequest, username, password);
    	error |= !mAddThread.add(addthreadActive, mRequest, subject, content, tags);
    	if(error) {
    		return createView(start, limit);
    	} else {
    		mResponse.sendRedirect(mRequest.getRequestURI());
    		return null;
    	}
	}
    
    @SuppressWarnings("serial")
	private Viewable createView(
			final int start, 
			final int limit) throws Exception {

		final ThreadsModule threadsModule = new ThreadsModule();
		threadsModule.getThreads(start, limit);

    	final int numOfThreads = (int) threadsModule.mThreads.getNumOfThreads();
		float pagesFloat = (numOfThreads/ limit);
		int pages = (int) pagesFloat;
		if(pagesFloat!=0) {
			pages++;
		}
    	final StringBuffer numbers = new StringBuffer();
    	for (int i = 0; i < pages; i++) {
    		String s = createUriForPagination(i*(limit), limit).toString();
    		String startP = "<a id=\"page"+(i+1)+"\" href=\""+s+"\">";
    		String endP = "</a> | ";
    		numbers.append(startP + String.valueOf(i+1) + endP);
		}

		return new Viewable("/threads_index.mustache", 
				new HashMap<String, String>() {{
					put("login", mLogin.template(mRequest));
					put("register", mRegister.template(mRequest));
					put("addthread", mAddThread.template(mRequest));
					put("threads", threadsModule.template(mRequest));
					put("next", createUriForNextPagination(start, limit, numOfThreads).toString());
					put("prev", createUriForPrevPagination(start, limit).toString());
					put("pages", numbers.toString());
				}});
	}

	private URI createUriForNextPagination(int start, int limit, int numPosts) throws URISyntaxException {
		if(!(start+limit > numPosts)) {
			start += limit;
		}
		return createUriForPagination(start, limit);
	}

	private URI createUriForPrevPagination(int start, int limit) throws URISyntaxException {
		start -= limit;
		if(start<0) {
			start=0;
		}
		return createUriForPagination(start, limit);
	}

	private URI createUriForPagination(int start, int limit) throws URISyntaxException {
		URI uri = new URIBuilder(mUriInfo.getRequestUri())
			.setParameter("start", String.valueOf(start))
			.setParameter("limit", String.valueOf(limit))
			.build();
		return uri;
	}
}