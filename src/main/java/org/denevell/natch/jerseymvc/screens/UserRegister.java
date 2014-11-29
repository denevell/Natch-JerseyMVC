package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.UserRegister.RegisterView;
import org.denevell.natch.jerseymvc.services.ServiceInputs.UserLoginInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.UserRegisterInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserLoginOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserRegisterOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.Urls;
import org.denevell.natch.jerseymvc.utils.UtilsSession;
import org.denevell.natch.jerseymvc.utils.ViewBase;

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
public class UserRegister {

  public RegisterView onGet(RegisterView view, final HttpServletRequest req, final HttpServletResponse resp) {
    return view;
  }

  public void onPost(final RegisterView view, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
    view.registerErrorMessage = Services.userRegister(req, 
            new UserRegisterInput(UserRegisterServlet.username,
                UserRegisterServlet.password,
                UserRegisterServlet.recovery_email), 
            new ResponseObject<UserRegisterOutput>() { @Override
              public void returned(UserRegisterOutput register) {
                if (register.error != null && register.error.trim().length() > 0) {
                  view.registerErrorMessageInRequest = register.error; // Hack...
                } else {
                  Services.userLogin(req, new UserLoginInput(UserRegisterServlet.username, UserRegisterServlet.password), new ResponseObject<UserLoginOutput>() { @Override
                    public void returned(UserLoginOutput login) {
                      UtilsSession.setUserAsLoggedInWithSessionAndCookies(req, resp, UserRegisterServlet.username, login.authKey, login.admin);
                      req.getSession(true).setAttribute("loginErrorMessage", login.errorMessage);
                    }});
                }
              }
            });
    if ((view.registerErrorMessage == null || 
        view.registerErrorMessage.trim().length() == 0) &&
        (view.registerErrorMessageInRequest == null || 
        view.registerErrorMessageInRequest.trim().length() == 0)) {
      String url = Urls.mainPage(req).toString();
      Serv.send303(resp, url);
    } else {
      Serv.send303(req, resp);
    }
  }

  public static class RegisterView extends ViewBase {
    public RegisterView(HttpServletRequest request) {
      super(request);
    }
    public String registerErrorMessageInRequest;
    public String registerErrorMessage;
  }

}
