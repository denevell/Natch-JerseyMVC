package org.denevell.natch.jerseymvc.screens.pwrequest;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.services.PwResetService;
import org.denevell.natch.jerseymvc.app.services.RegisterService;
import org.denevell.natch.jerseymvc.app.services.ThreadAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.services.ThreadsService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView;

public class PwRequestPresenter extends SessionSavingViewPresenter<PwRequestView>  {
	
	private PwRequestController mController;
	public RegisterService mRegister = new RegisterService();
	public LoginLogoutService mLogin = new LoginLogoutService();
	public ThreadAddService mAddThread = new ThreadAddService();
	public PwResetService mResetPwModule = new PwResetService();
	public ThreadsPaginationService mPagination = new ThreadsPaginationService();
	public ThreadsService mThreadsService = new ThreadsService();
	
	public PwRequestPresenter(PwRequestController controller) throws Exception {
		super(new PwRequestView(controller.mRequest));
		mController = controller;
	}

	@Override
	public PwRequestView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
    Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
    	// Reset pw
    	mResetPwModule.reset(mController.resetPwActive, request, mController.resetPwEmail);
    	mView.resetPasswordError = mResetPwModule.isError();
    	mView.resetPassword = mResetPwModule.isProcessed();
    	if(mLogin.getLogin().getErrorMessage()!=null || mResetPwModule.isError()) {
    		mView.showResetForm = true;
    	}

    	String url = request.getRequestURL()+"?"+request.getQueryString(); 
    	return Response.seeOther(new URI(url)).build();
	}

}
