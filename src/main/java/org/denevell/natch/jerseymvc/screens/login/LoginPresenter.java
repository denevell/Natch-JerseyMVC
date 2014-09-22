package org.denevell.natch.jerseymvc.screens.login;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class LoginPresenter extends SessionSavingViewPresenter<LoginView> {

  private LoginController mController;
  public LoginLogoutService mLogin = new LoginLogoutService();

  public LoginPresenter(LoginController controller) throws Exception {
    super(LoginView.class);
    mController = controller;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);

    mLogin.login(new Object(), request, mController.username, mController.password);
    mView.loginErrorMessage = mLogin.getLogin().getErrorMessage();
    // TODO: Save the state to the session
    // TODO: Find the url to redirect to from the query or post params
    String url = request.getRequestURL() + "?" + request.getQueryString();
    return Response.seeOther(new URI(url)).build();
  }

}
