package org.denevell.natch.jerseymvc.screens.thread.single;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.models.PostOutput;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.services.PostAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView.Post;

public class ThreadPresenter extends SessionSavingViewPresenter<ThreadView>  {
	
	private ThreadOutput mModel;
	private ThreadController mController;
	
	public ThreadPresenter(ThreadController controller) throws Exception {
		super(new ThreadView(controller.mRequest));
		mController = controller;
	}

  @Override
  public ThreadView onGet(HttpServletRequest request) throws Exception {
    super.onGet(request);

    // Model call
    mModel = new ThreadService(mController.start, mController.limit, mController.threadId).model();

    // Get logged in user
    mView.loggedInCorrectly = getCorrectlyLoggedIn(request);
    mView.loggedIn = SessionUtils.isLoggedIn(request);

    // Root post id
    mView.rootPostId = (int) mModel.getRootPostId();

    // Get subject
    mView.subject = mModel.getSubject();
    mView.tags = mModel.getTags();

		// Set posts in template
		int postsSize = mModel.getPosts().size();
		for (int i = 0; i < postsSize; i++) {
			PostOutput p = mModel.getPosts().get(i);
			Post e = new Post(p.getUsername(), p.getHtmlContent(), (int)p.getId(), i, p.getLastModifiedDateWithTime());
			e.parentThreadId = mModel.getId();
			e.editedByAdmin = p.isAdminEdited();
			  
			// Logged in info
			e.loggedInCorrectly = mView.loggedInCorrectly;
			e.isAdmin = SessionUtils.isAdmin(request);

			// Pagination
			e.returnToThreadFromDeletePostLimitParam = mController.limit; 
			e.returnToThreadFromReplyStartParam = mController.start;
			e.returnToThreadFromEditStartParam = mController.start;
			e.returnToThreadFromDeletePostStartParam = mController.start;
			if(postsSize==1 && mModel.getNumPosts() > postsSize) {
				e.returnToThreadFromDeletePostStartParam -= 10;
			}
			
			// Can edit thread via this post
			if(i==0 && mController.start==0 && mView.loggedInCorrectly) {
			  e.hasEditThreadText = true;
			}
			if((i!=0 || mController.start>1) && mView.loggedInCorrectly) {
			  e.hasEditPostText = true;
			}

			mView.posts.add(e); 
		}
    	
    	// Pagination
		int numPosts = mModel.getNumPosts();
		ThreadsPaginationService pagination = getPagination(request, numPosts);
		mView.next = pagination.getNext().toString();
		mView.prev = pagination.getPrev().toString();
		mView.pages = pagination.getPages().toString();
		
    	Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
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
		ThreadsPaginationService pagination = getPagination(request, numPosts);
		String url = request.getRequestURL()+"?"+request.getQueryString();
    	if(numPosts > mController.start+mController.limit) { 
    		return Response.seeOther(pagination.getNext()).build();
    	} else {
    		return Response.seeOther(new URI(url)).build();
    	}
	}

	private ThreadsPaginationService getPagination(HttpServletRequest request, int numPosts) throws URISyntaxException {
		ThreadsPaginationService pagination = new ThreadsPaginationService();
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
