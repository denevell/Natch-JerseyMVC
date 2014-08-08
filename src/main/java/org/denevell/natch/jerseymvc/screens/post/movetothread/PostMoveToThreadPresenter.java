package org.denevell.natch.jerseymvc.screens.post.movetothread;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.PostFromThreadService;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class PostMoveToThreadPresenter extends SessionSavingViewPresenter<PostMoveToThreadView>  {
	
	private PostMoveToThreadController mController;
	private PostSingleService mPostSingleService = new PostSingleService();
	private PostFromThreadService mPostFromThreadService = new PostFromThreadService();
	
	public PostMoveToThreadPresenter(PostMoveToThreadController controller) throws Exception {
		super(PostMoveToThreadView.class);
		mController = controller;
	}

	@Override
	public PostMoveToThreadView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mPostSingleService.fetchPost(new Object(), mController.postId);
		mView.username = mPostSingleService.getPost().getUsername();
		mView.postId = mController.postId;
		mView.isAdmin = SessionUtils.isAdmin(request);
		Presenter.Utils.clearViewStateFromSEssion(request, PostMoveToThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		mPostFromThreadService.fetchPost(new Object(), 
				request,
				mController.postId, 
				mController.subject);
		mView.moveError = mPostFromThreadService.mThreadOutput.getErrorMessage();
		return null;
	}

}
