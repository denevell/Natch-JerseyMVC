package org.denevell.natch.jerseymvc.screens.thread.delete.mvp;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.services.ThreadDeleteService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.MainPageUrlGenerator;

public class ThreadDeleteConfirmPresenter extends SessionSavingViewPresenter<ThreadDeleteConfirmationView>  {
	
	private ThreadDeleteConfirmationController mController;
	private ThreadDeleteService mModel = new ThreadDeleteService();
	
	public ThreadDeleteConfirmPresenter(ThreadDeleteConfirmationController controller) throws Exception {
		super(ThreadDeleteConfirmationView.class);
		mController = controller;
	}

	@Override
	public ThreadDeleteConfirmationView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		mView.id = Integer.valueOf(mController.getDeleteThreadId()); 
		mView.loggedIn = request.getSession(true).getAttribute("loggedin")!=null;
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		mModel.delete(request, mController.getDeleteThreadId());
		if (mModel.getDeleteThread().isSuccessful()) {
			return Response.seeOther(new MainPageUrlGenerator().build()).build();
		} else {
			mView.errorMessage = mModel.getDeleteThread().getErrorMessage();
			String url = request.getRequestURL() + "?" + request.getQueryString();
			return Response.seeOther(new URI(url)).build();
		}
	}

}
