package org.denevell.natch.jerseymvc.screens.post.delete;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
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
	public PostDeleteConfirmView onGet(HttpServletRequest request) {
		super.onGet(request);
		mView.id = Integer.valueOf(mController.deletePostId); 
		mView.parentThreadId = mController.parentThreadId;
		mView.start = mController.start;
		mView.limit = mController.limit;
    Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		super.onPost(request, resp);
		mView.id = Integer.valueOf(mController.deletePostId); 
		mModel.delete(new Object(), request, mController.deletePostId);
		if (mModel.mPostDelete.isSuccessful()) {
			return Response.seeOther(new URI(
					new ThreadUrlGenerator()
						.createThreadUrl(
								mController.parentThreadId,
								mController.start, 
								mController.limit))).build();
		} else {
			mView.errorMessage = mModel.mPostDelete.getErrorMessage();
			String url = request.getRequestURL() + "?" + request.getQueryString();
			return Response.seeOther(new URI(url)).build();
		}
	}

}
