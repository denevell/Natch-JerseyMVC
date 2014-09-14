package org.denevell.natch.jerseymvc.screens.register;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.services.PwResetService;
import org.denevell.natch.jerseymvc.app.services.RegisterService;
import org.denevell.natch.jerseymvc.app.services.ThreadAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.services.ThreadsService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.app.urls.MainPageUrlGenerator;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView;

public class RegisterPresenter extends SessionSavingViewPresenter<RegisterView>  {
	
	private RegisterController mController;
	public RegisterService mRegister = new RegisterService();
	public LoginLogoutService mLogin = new LoginLogoutService();
	public ThreadAddService mAddThread = new ThreadAddService();
	public PwResetService mResetPwModule = new PwResetService();
	public ThreadsPaginationService mPagination = new ThreadsPaginationService();
	public ThreadsService mThreadsService = new ThreadsService();
	
	public RegisterPresenter(RegisterController controller) throws Exception {
		super(RegisterView.class);
		mController = controller;
	}

	@Override
	public RegisterView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		
		// Logged in info
		boolean loggedIn = SessionUtils.isLoggedIn(request);
    mView.hasauthkey = loggedIn;
    	
		Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		
    	mRegister.register(mController.registerActive, request, mController.username, mController.password, mController.recoveryEmail);
    	mView.registerErrorMessage = mRegister.mRegister.getErrorMessage();

    	// Redirect back to the main page, statically.
    	if(mView.registerErrorMessage!=null && mView.registerErrorMessage.trim().length()!=0) {
    	  String url = request.getRequestURL()+"?"+request.getQueryString(); 
    	  return Response.seeOther(new URI(url)).build();
    	} else {
    	  String url = new MainPageUrlGenerator().build().toString();
    	  return Response.seeOther(new URI(url)).build();
    	}
	}

}
