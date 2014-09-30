package org.denevell.natch.jerseymvc.screens.login;

import java.net.URI;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class LoginPresenter extends SessionSavingViewPresenter<LoginView> {

  private LoginController mController;
  public LoginLogoutService mLogin = new LoginLogoutService();

  public LoginPresenter(LoginController controller) throws Exception {
		super(new LoginView(controller.mRequest));
    mController = controller;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);

    mLogin.login(new Object(), request, mController.username, mController.password);
    mView.loginErrorMessage = mLogin.getLogin().getErrorMessage();
    HashMap<String, Object> value = new HashMap<String, Object>();
    request.getSession(true).setAttribute("temp_state", value);
    value.put("loginErrorMessage", mView.loginErrorMessage);
    return Response.seeOther(new URI(mController.redirect)).build();
  }

}
