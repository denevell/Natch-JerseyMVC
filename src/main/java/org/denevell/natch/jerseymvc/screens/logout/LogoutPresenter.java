package org.denevell.natch.jerseymvc.screens.logout;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class LogoutPresenter extends SessionSavingViewPresenter<BaseView> {

  public LoginLogoutService mLogin = new LoginLogoutService();
  private LogoutController mController;

  public LogoutPresenter(LogoutController controller) throws Exception {
    super(BaseView.class);
    mController = controller;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);

    mLogin.logout(new Object(), request);

    Presenter.Utils.clearViewStateFromSession(request, BaseView.class);
    return Response.seeOther(new URI(mController.redirect)).build();
  }

}
