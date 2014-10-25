package org.denevell.natch.jerseymvc.screens.thread.delete;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.services.ThreadDeleteService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.MainPageUrlGenerator;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView;

public class ThreadDeleteConfirmPresenter extends SessionSavingViewPresenter<ThreadDeleteConfirmView>  {
	
	private ThreadDeleteConfirmationController mController;
	private ThreadDeleteService mModel = new ThreadDeleteService();
	
	public ThreadDeleteConfirmPresenter(ThreadDeleteConfirmationController controller) throws Exception {
		super(new ThreadDeleteConfirmView(controller.mRequest));
		mController = controller;
	}

	@Override
	public ThreadDeleteConfirmView onGet(HttpServletRequest request) {
		super.onGet(request);
		mView.id = Integer.valueOf(mController.getDeleteThreadId()); 
    Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		super.onPost(request, resp);
		mModel.delete(request, mController.getDeleteThreadId());
		if (mModel.getDeleteThread().isSuccessful()) {
			return Response.seeOther(new MainPageUrlGenerator().build()).build();
		} else {
			mView.errorMessage = mModel.getDeleteThread().getErrorMessage();
      String qs = "";
      if (request.getQueryString() != null && request.getQueryString().trim().length() > 0) {
        qs = "?" + request.getQueryString();
      }
			String url = request.getRequestURL() + qs;
			return Response.seeOther(new URI(url)).build();
		}
	}

}
