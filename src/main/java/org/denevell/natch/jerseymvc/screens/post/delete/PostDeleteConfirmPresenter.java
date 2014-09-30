package org.denevell.natch.jerseymvc.screens.post.delete;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.PostDeleteService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView;

public class PostDeleteConfirmPresenter extends SessionSavingViewPresenter<PostDeleteConfirmView>  {
	
	private PostDeleteConfirmationController mController;
	private PostDeleteService mModel = new PostDeleteService();
	
	public PostDeleteConfirmPresenter(PostDeleteConfirmationController controller) throws Exception {
		super(new PostDeleteConfirmView(controller.mRequest));
		mController = controller;
	}

	@Override
	public PostDeleteConfirmView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mView.id = Integer.valueOf(mController.getDeletePostId()); 
		mView.loggedIn = SessionUtils.isLoggedIn(request);
		mView.parentThreadId = mController.getParentThreadId();
		mView.start = mController.getStart();
		mView.limit = mController.getLimit();
    	Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		mModel.delete(new Object(), request, mController.getDeletePostId());
		if (mModel.mPostDelete.isSuccessful()) {
			return Response.seeOther(new URI(
					new ThreadUrlGenerator()
						.createThreadUrl(
								mController.getParentThreadId(),
								mController.getStart(), 
								mController.getLimit()))).build();
		} else {
			mView.errorMessage = mModel.mPostDelete.getErrorMessage();
			String url = request.getRequestURL() + "?" + request.getQueryString();
			return Response.seeOther(new URI(url)).build();
		}
	}

}
