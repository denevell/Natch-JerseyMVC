package org.denevell.natch.jerseymvc.screens.post.movetothread;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class PostMoveToThreadPresenter extends SessionSavingViewPresenter<PostMoveToThreadView>  {
	
	private PostMoveToThreadController mController;
	private PostSingleService mService = new PostSingleService();
	
	public PostMoveToThreadPresenter(PostMoveToThreadController controller) throws Exception {
		super(PostMoveToThreadView.class);
		mController = controller;
	}

	@Override
	public PostMoveToThreadView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mService.fetchPost(new Object(), mController.postId);
		mView.username = mService.getPost().getUsername();
		mView.postId = mController.postId;
		mView.isAdmin = SessionUtils.isAdmin(request);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		return null;
	}

}
