package org.denevell.natch.jerseymvc.screens.thread.edit;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.services.ThreadEditService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class ThreadEditPresenter extends SessionSavingViewPresenter<ThreadEditView>  {
  
  private ThreadEditController mController;
  public ThreadEditService mThreadEditService = new ThreadEditService();
	private PostSingleService mPostService = new PostSingleService();
  
  public ThreadEditPresenter(ThreadEditController controller) throws Exception {
    super(ThreadEditView.class);
    mController = controller;
  }

  @Override
  public ThreadEditView onGet(HttpServletRequest request) throws Exception {
    super.onGet(request);

		mPostService.fetchPost(new Object(), mController.postEditId);
		mView.content = mPostService.getPost().getContent();
		mView.username = mPostService.getPost().getUsername();
		mView.subject = mPostService.getPost().getSubject();
		mView.tags = mPostService.getPost().getTagsString();
    
    // Logged in info
    mView.loggedIn = SessionUtils.isLoggedIn(request);
    mView.isAdmin = request.getSession(true).getAttribute("admin")!=null;

    Presenter.Utils.clearViewStateFromSEssion(request, ThreadEditView.class);
    return mView;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);
    
    mThreadEditService.fetch(new Object(), request, mController.subject, mController.content);
    // If no error, redirect to thread
    // If error, set some kind of error field
    String url = request.getRequestURL()+"?"+request.getQueryString(); 
    return Response.seeOther(new URI(url)).build();
  }


}
