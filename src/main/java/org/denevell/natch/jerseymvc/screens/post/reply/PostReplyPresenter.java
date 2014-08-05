package org.denevell.natch.jerseymvc.screens.post.reply;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class PostReplyPresenter extends SessionSavingViewPresenter<PostReplyView>  {
	
	private PostReplyController mController;
	
	public PostReplyPresenter(PostReplyController controller) throws Exception {
		super(PostReplyView.class);
		mController = controller;
	}

	@Override
	public PostReplyView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		return null;
	}

}
