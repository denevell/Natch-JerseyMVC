package org.denevell.natch.jerseymvc.screens.threads;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.services.PwResetService;
import org.denevell.natch.jerseymvc.app.services.RegisterService;
import org.denevell.natch.jerseymvc.app.services.ThreadAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.services.ThreadsService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView;

public class ThreadsPresenter extends SessionSavingViewPresenter<ThreadsView>  {
	
	private ThreadsController mController;
	public RegisterService mRegister = new RegisterService();
	public LoginLogoutService mLogin = new LoginLogoutService();
	public ThreadAddService mAddThread = new ThreadAddService();
	public PwResetService mResetPwModule = new PwResetService();
	public ThreadsPaginationService mPagination = new ThreadsPaginationService();
	public ThreadsService mThreadsService = new ThreadsService();
	
	public ThreadsPresenter(ThreadsController controller) throws Exception {
		super(ThreadsView.class);
		mController = controller;
	}

	@Override
	public ThreadsView onGet(HttpServletRequest request) throws Exception {
		super.onGet(request);
		
		// Threads
		mThreadsService.fetchThreads(mController.start, mController.limit);
		for(int i = 0; i<mThreadsService.mThreads.getThreads().size(); i++) {
			ThreadOutput t = mThreadsService.mThreads.getThreads().get(i);
			mView.threads.add(new ThreadsView.Thread(t.getSubject(), t.getAuthor(), t.getLastModifiedDateWithTime(), t.getId(), i));
		}

		// Logged in info
		mView.loggedIn = SessionUtils.isLoggedIn(request);
		mView.isAdmin = request.getSession(true).getAttribute("admin")!=null;

		// Pagination
    	mPagination.calculatePagination(
    				request.getRequestURL()+"?"+request.getQueryString(), 
    				mController.start, 
    				mController.limit, 
    				mThreadsService.getNumThreads());
    	mView.pages = mPagination.getPages();
    	mView.next = mPagination.getNext().toString();
    	mView.prev = mPagination.getPrev().toString();
    	
    	Presenter.Utils.clearViewStateFromSEssion(request, ThreadView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		super.onPost(request);
		
		// Login / out
		mLogin.logout(mController.logoutActive, request);
    	mLogin.login(mController.loginActive, request, mController.username, mController.password);
    	mView.loginErrorMessage = mLogin.getLogin().getErrorMessage();

    	// Register
    	mRegister.register(mController.registerActive, request, mController.username, mController.password, mController.recoveryEmail);
    	mView.registerErrorMessage = mRegister.mRegister.getErrorMessage();

    	// Add thread
    	mAddThread.add(mController.addthreadActive, request, mController.subject, mController.content, mController.tags);
    	mView.addThreadErrorMessage = mAddThread.getAddThread().getErrorMessage();
    	
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
