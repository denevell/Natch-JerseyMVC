package org.denevell.natch.jerseymvc.screens.thread.mvp;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.models.PostOutput;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.services.PostAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.screens.thread.mvp.ThreadView.Post;
import org.denevell.natch.jerseymvc.screens.threads.modules.ThreadsPaginationModule;

public class ThreadPresenter extends SessionSavingViewPresenter<ThreadView>  {
	
	private ThreadOutput mModel;
	private ThreadController mController;
	
	public ThreadPresenter(ThreadController controller) throws Exception {
		super(ThreadView.class);
		mController = controller;
	}

	@Override
	public ThreadView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		
		// Model call
		mModel = new ThreadService(mController.start, mController.limit, mController.threadId).model();
		
		// Get logged in user
    	mView.loggedInCorrectly = getCorrectlyLoggedIn(request);
		
		// Root post id
    	mView.rootPostId = (int) mModel.getRootPostId();
		
		// Get subject
    	mView.subject = mModel.getSubject();

		// Set posts in template
		for (int i = 0; i < mModel.getPosts().size(); i++) {
			PostOutput p = mModel.getPosts().get(i);
			mView.posts.add(new Post(p.getUsername(), p.getHtmlContent(), (int)p.getId(), i)); 
		}
    	
    	// Pagination
		int numPosts = mModel.getNumPosts();
		ThreadsPaginationModule pagination = getPagination(request, numPosts);
		mView.next = pagination.getNext().toString();
		mView.prev = pagination.getPrev().toString();
		mView.pages = pagination.getPages().toString();
		
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);

		// Model call
    	PostAddService addPostModule = new PostAddService();
    	addPostModule.add(new Object(), request, 
    			mController.content, 
    			mController.threadId);
    	
    	// Error response
    	mView.addPostError = addPostModule.getAddpost().getErrorMessage();

    	// Pagination info for Response
		int numPosts = addPostModule.getNumPosts();
		ThreadsPaginationModule pagination = getPagination(request, numPosts);
		String url = request.getRequestURL()+"?"+request.getQueryString();
    	if(numPosts > mController.start+mController.limit) { 
    		return Response.seeOther(pagination.getNext()).build();
    	} else {
    		return Response.seeOther(new URI(url)).build();
    	}
	}

	private ThreadsPaginationModule getPagination(HttpServletRequest request, int numPosts) throws URISyntaxException {
		ThreadsPaginationModule pagination = new ThreadsPaginationModule();
		pagination.calculatePagination(
				request.getRequestURL()+"?"+request.getQueryString(), 
				mController.start, 
				mController.limit, 
				numPosts);
		return pagination;
	}
	
	private boolean getCorrectlyLoggedIn(HttpServletRequest request) {
    	Object name = request.getSession(true).getAttribute("name");
    	boolean correctUser;
		if(name!=null) {
    		correctUser = name.equals(mModel.getAuthor());
    	} else {
    		correctUser = false;
    	}
    	Object admin = request.getSession(true).getAttribute("admin");
    	return (admin!=null && ((boolean)admin)==true) || correctUser;
	}

}
