package org.denevell.natch.jerseymvc.screens.admin;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.models.UserOutput;
import org.denevell.natch.jerseymvc.app.services.AdminService;
import org.denevell.natch.jerseymvc.app.services.AdminToggleService;
import org.denevell.natch.jerseymvc.app.services.PwChangeService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.screens.admin.AdminView.User;

public class AdminPresenter extends SessionSavingViewPresenter<AdminView>  {
	
	private AdminController mController;
	public AdminService mAdmin = new AdminService();
	public PwChangeService mPwChange = new PwChangeService();
	public AdminToggleService mAdminToggleService = new AdminToggleService();
	
	public AdminPresenter(AdminController controller) throws Exception {
		super(new AdminView(controller.mRequest));
		mController = controller;
	}

	@Override
	public AdminView onGet(HttpServletRequest request) {
		super.onGet(request);
		
    	mAdmin.getUsers((String)request.getSession(true).getAttribute("authkey"));
    	List<UserOutput> users = mAdmin.getUsers().getUsers();
    	for (UserOutput userOutput : users) {
    		mView.users.add(new User(
    				userOutput.getUsername(), 
    				userOutput.getRecoveryEmail(), 
    				userOutput.isResetPasswordRequest(),
    				userOutput.isAdmin()));
		}
    	
    	Presenter.Utils.clearViewStateFromSession(request, AdminView.class);
		return mView;
	}

	@Override
	public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		super.onPost(request, resp);

		mAdminToggleService
			.toggle(mController.adminToggleActive, 
					SessionUtils.getAuthKey(request), 
					mController.adminToggleByUsername);

    	mPwChange.changePw(mController.getChangePwActive(), 
    			request, 
    			mController.getChangePwUsername(), 
    			mController.getChangePwNewPass());

    	mView.pwChangeProcessed = mPwChange.getProcessed();
    	mView.pwChangeError = mPwChange.getError();
    	String url = request.getRequestURL()+"?"+request.getQueryString(); 

    	return Response.seeOther(new URI(url)).build();
	}

}
