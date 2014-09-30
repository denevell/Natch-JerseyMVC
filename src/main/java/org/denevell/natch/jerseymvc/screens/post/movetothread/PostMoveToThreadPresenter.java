package org.denevell.natch.jerseymvc.screens.post.movetothread;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.PostFromThreadService;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.ThreadUrlGenerator;

public class PostMoveToThreadPresenter extends SessionSavingViewPresenter<PostMoveToThreadView>  {
	
	private PostMoveToThreadController mController;
	private PostSingleService mPostSingleService = new PostSingleService();
	private PostFromThreadService mPostFromThreadService = new PostFromThreadService();
	
	public PostMoveToThreadPresenter(PostMoveToThreadController controller) throws Exception {
		super(new PostMoveToThreadView(controller.mRequest));
		mController = controller;
	}

	@Override
	public PostMoveToThreadView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mPostSingleService.fetchPost(new Object(), mController.postId);
		mView.username = mPostSingleService.getPost().getUsername();
		mView.postId = mController.postId;
		mView.isAdmin = SessionUtils.isAdmin(request);
		Presenter.Utils.clearViewStateFromSession(request, PostMoveToThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		if(mController.subject==null || mController.subject.trim().length()==0) {
			mView.moveError="Must supply a subject";
			String url = request.getRequestURL() + "?" + request.getQueryString();
			return Response.seeOther(new URI(url)).build();
		} else {
			mPostFromThreadService.fetchPost(
					new Object(), 
					request,
					mController.postId, 
					mController.subject);
			mView.moveError = mPostFromThreadService.mThreadOutput .getErrorMessage();
			if (mView.moveError == null || mView.moveError.trim().length() == 0) {
				ThreadUrlGenerator threadUrl = new ThreadUrlGenerator();
				String url = threadUrl.createThreadUrl(mPostFromThreadService.mThreadOutput
								.getThread().getId());
				return Response.seeOther(new URI(url)).build();
			} else {
				String url = request.getRequestURL() + "?" + request.getQueryString();
				return Response.seeOther(new URI(url)).build();
			}
		}
	}

}
