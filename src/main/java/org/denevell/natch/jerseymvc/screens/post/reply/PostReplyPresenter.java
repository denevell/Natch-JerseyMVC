package org.denevell.natch.jerseymvc.screens.post.reply;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.PostAddService;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;

public class PostReplyPresenter extends SessionSavingViewPresenter<PostReplyView>  {
	
	private PostReplyController mController;
	private PostSingleService mService = new PostSingleService();
	
	public PostReplyPresenter(PostReplyController controller) throws Exception {
		super(PostReplyView.class);
		mController = controller;
	}

	@Override
	public PostReplyView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mService.fetchPost(new Object(), mController.postId);
		mView.start = mController.start;
		mView.limit = mController.limit;
		mView.id = mController.postId;
		mView.htmlContent = mService.getPost().getQuotedContent();
		mView.username = mService.getPost().getUsername();
		mView.threadId = mController.threadId;
		mView.getLastModifiedDateWithTime = mService.getPost().getLastModifiedDateWithTime();
		mView.loggedIn = SessionUtils.isLoggedIn(request);
    	Presenter.Utils.clearViewStateFromSession(request, PostReplyView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
    	PostAddService addPostModule = new PostAddService();
    	addPostModule.add(new Object(), request, 
    			mController.content, 
    			mController.threadId);
		if (addPostModule.getAddpost().getErrorMessage()==null) {
			return Response.seeOther(new URI(
					new ThreadUrlGenerator()
						.createThreadUrl(
								mController.threadId,
								mController.start, 
								mController.limit))).build();
		} else {
			mView.errorMessage = addPostModule.getAddpost().getErrorMessage();
			String url = request.getRequestURL() + "?" + request.getQueryString();
			return Response.seeOther(new URI(url)).build();
		}
	}

}
