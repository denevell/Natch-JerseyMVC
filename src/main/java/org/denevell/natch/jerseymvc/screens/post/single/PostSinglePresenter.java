package org.denevell.natch.jerseymvc.screens.post.single;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;

public class PostSinglePresenter extends SessionSavingViewPresenter<PostSingleView>  {
	
	private PostSingleController mController;
	private PostSingleService mPostService = new PostSingleService();
	
	public PostSinglePresenter(PostSingleController controller) throws Exception {
		super(new PostSingleView(controller.mRequest));
		mController = controller;
	}

	@Override
	public PostSingleView onGet(HttpServletRequest request) {
		super.onGet(request);
		mPostService.fetchPost(new Object(), mController.getPostId());

		mView.htmlContent = mPostService.getPost().getHtmlContent();
		mView.username = mPostService.getPost().getUsername();
		mView.subject = mPostService.mPostOutput.getSubject();
		mView.backUrl = new ThreadUrlGenerator().createThreadUrl(mPostService.mPostOutput.getThreadId());
    	Presenter.Utils.clearViewStateFromSession(request, PostSingleView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		super.onPost(request, resp);
		return null;
	}

}
