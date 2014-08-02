package org.denevell.natch.jerseymvc.screens.post.single;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;

public class PostSinglePresenter extends SessionSavingViewPresenter<PostSingleView>  {
	
	private PostSingleController mController;
	private PostSingleService mPostService = new PostSingleService();
	private PostSingleView mView = new PostSingleView();
	
	public PostSinglePresenter(PostSingleController controller) throws Exception {
		super(PostSingleView.class);
		mController = controller;
	}

	@Override
	public PostSingleView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mPostService.fetchPost(new Object(), mController.getPostId());
		mView.htmlContent = mPostService.getPost().getHtmlContent();
		mView.username = mPostService.getPost().getUsername();
		mView.backUrl = new ThreadUrlGenerator().createThreadUrl(mPostService.mPostOutput.getThreadId());
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		return null;
	}

}
