package org.denevell.natch.jerseymvc.screens.post.edit;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.models.PostEditOutput;
import org.denevell.natch.jerseymvc.app.services.PostEditService;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;

public class PostEditPresenter extends SessionSavingViewPresenter<PostEditView>  {
  
  private PostEditController mController;
  public PostEditService mPostEditService = new PostEditService();
	private PostSingleService mPostService = new PostSingleService();
  
  public PostEditPresenter(PostEditController controller) throws Exception {
    super(PostEditView.class);
    mController = controller;
  }

  @Override
  public PostEditView onGet(HttpServletRequest request) throws Exception {
    super.onGet(request);

		mPostService.fetchPost(new Object(), mController.postEditId);
		mView.content = mPostService.getPost().getContent();
		mView.thread= mController.threadId;
		mView.postId = mController.postEditId;
    
    // Logged in info
    mView.loggedIn = SessionUtils.isLoggedIn(request);
    mView.isAdmin = request.getSession(true).getAttribute("admin")!=null;

    Presenter.Utils.clearViewStateFromSession(request, PostEditView.class);
    return mView;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);
    
    mPostEditService.fetch(new Object(), request, 
        mController.content, 
        mController.postEditId);
    PostEditOutput output = mPostEditService.getOutput();
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
