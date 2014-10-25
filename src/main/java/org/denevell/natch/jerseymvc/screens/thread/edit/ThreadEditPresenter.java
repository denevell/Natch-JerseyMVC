package org.denevell.natch.jerseymvc.screens.thread.edit;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.models.ThreadEditOutput;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.services.ThreadEditService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;

public class ThreadEditPresenter extends SessionSavingViewPresenter<ThreadEditView>  {
  
  private ThreadEditController mController;
  public ThreadEditService mThreadEditService = new ThreadEditService();
	private PostSingleService mPostService = new PostSingleService();
  
  public ThreadEditPresenter(ThreadEditController controller) throws Exception {
		super(new ThreadEditView(controller.mRequest));
    mController = controller;
  }

  @Override
  public ThreadEditView onGet(HttpServletRequest request) {
    super.onGet(request);

		mPostService.fetchPost(new Object(), mController.postEditId);
		mView.content = mPostService.getPost().getContent();
		mView.username = mPostService.getPost().getUsername();
		mView.subject = mPostService.getPost().getSubject();
		mView.tags = mPostService.getPost().getTagsString();
		mView.thread= mController.threadId;
		mView.postId = mController.postEditId;
    
    // Logged in info
    mView.loggedIn = SessionUtils.isLoggedIn(request);
    mView.isAdmin = request.getSession(true).getAttribute("admin")!=null;

    Presenter.Utils.clearViewStateFromSession(request, ThreadEditView.class);
    return mView;
  }

  @Override
  public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
    super.onPost(request, resp);
    
    mThreadEditService.fetch(new Object(), request, 
        mController.subject, 
        mController.content,
        mController.tags,
        mController.postEditId);
    ThreadEditOutput output = mThreadEditService.getOutput();
    if(output.getErrorMessage()==null || output.getErrorMessage().trim().length()==0) {
      String url = new ThreadUrlGenerator().createThreadUrl(mController.threadId);
      return Response.seeOther(new URI(url)).build();
    } else {
      mView.error = output.getErrorMessage();
      String url = request.getRequestURL()+"?"+request.getQueryString(); 
      return Response.seeOther(new URI(url)).build();
    }
  }


}
