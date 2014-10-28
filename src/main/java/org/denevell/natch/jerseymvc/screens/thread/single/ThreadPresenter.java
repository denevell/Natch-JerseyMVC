package org.denevell.natch.jerseymvc.screens.thread.single;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.models.PostOutput;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.services.PostAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.utils.Urls;
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

    mModel = new ThreadService(mController.start, mController.limit, mController.threadId).model();
    mView.loggedInCorrectly = getCorrectlyLoggedIn(request, mModel.getAuthor());
    mView.rootPostId = (int) mModel.getRootPostId();
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
			if((i!=0 || mController.start>1) && e.isAdmin) {
			  e.hasMoveToThreadText= true;
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
  public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
    super.onPost(request, resp);

    PostAddService addPostModule = new PostAddService();
    addPostModule.add(new Object(), request, mController.content, mController.threadId);

    mView.addPostError = addPostModule.getAddpost().getErrorMessage();
    int numPosts = addPostModule.getNumPosts();
    ThreadsPaginationService pagination = getPagination(request, numPosts);
    if (numPosts > mController.start + mController.limit) {
      return Response.seeOther(pagination.getNext()).build();
    } else {
      //Responses.send303(request, resp); 
      return Response.seeOther(new URI(Urls.getUrlWithQueryString(request))).build();
    }
  }

	private ThreadsPaginationService getPagination(HttpServletRequest request, int numPosts) throws URISyntaxException {
		ThreadsPaginationService pagination = new ThreadsPaginationService();
		pagination.calculatePagination(
		    Urls.getUrlWithQueryString(request),
				mController.start, 
				mController.limit, 
				numPosts);
		return pagination;
	}
	
  private boolean getCorrectlyLoggedIn(HttpServletRequest request, String author) {
    Object name = request.getSession(true).getAttribute("name");
    boolean correctUser;
    if (name != null) {
      correctUser = name.equals(author);
    } else {
      correctUser = false;
    }
    Object admin = request.getSession(true).getAttribute("admin");
    return (admin != null && ((boolean) admin) == true) || correctUser;
  }

}
