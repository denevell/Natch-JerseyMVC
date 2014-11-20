package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.Register.RegisterView;
import org.denevell.natch.jerseymvc.services.ServiceInputs.RegisterInput;
import org.denevell.natch.jerseymvc.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.RegisterOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/register/*", 
    viewClass = RegisterView.class, 
    template = "/register.mustache",
    params = {
      @Param(name = "username"),
      @Param(name = "password"),
      @Param(name = "recovery_email")})
public class Register {

  public RegisterView onGet(RegisterView view, final HttpServletRequest req, final HttpServletResponse resp) {
    return view;
  }

  public void onPost(final RegisterView view, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
    Services.userRegister(req, 
            new RegisterInput(RegisterServlet.username,
                RegisterServlet.password,
                RegisterServlet.recovery_email), 
            new ResponseObject() { @Override
              public void returned(Object o) {
                RegisterOutput mRegister = (RegisterOutput) o;
                if (mRegister.error != null && mRegister.error.trim().length() > 0) {
                  view.registerErrorMessage = mRegister.error; // Hack...
                } else {
                  new LoginLogoutService().login(req, resp, RegisterServlet.username, RegisterServlet.password);
                }
              }
            });
    if (view.registerErrorMessage != null && view.registerErrorMessage.trim().length() != 0) {
      Responses.send303(req, resp);
    } else {
      String url = UrlGenerators.mainPage(req).toString();
      Responses.send303(resp, url);
    }
  }

  public static class RegisterView extends BaseView {
    public RegisterView(HttpServletRequest request) {
      super(request);
    }
    public String registerErrorMessage;
  }

}
