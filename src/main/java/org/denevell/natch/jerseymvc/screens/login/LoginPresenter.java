package org.denevell.natch.jerseymvc.screens.login;

import java.net.URI;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class LoginPresenter extends SessionSavingViewPresenter<Object> {

  private LoginController mController;
  public LoginLogoutService mLogin = new LoginLogoutService();

  public LoginPresenter(LoginController controller) throws Exception {
		super(new Object());
    mController = controller;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);
    mLogin.login(new Object(), request, mController.username, mController.password);
    HashMap<String, Object> value = new HashMap<String, Object>();
    request.getSession(true).setAttribute("temp_state", value);
    value.put(BaseView.arg_loginErrorMessage, mLogin.getLogin().getErrorMessage());
    return Response.seeOther(new URI(mController.redirect)).build();
  }

}