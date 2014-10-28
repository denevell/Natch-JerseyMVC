package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.MainPageUrlGenerator;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.screens.Register.RegisterView;
import org.denevell.natch.jerseymvc.services.RegisterService;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/register", 
    viewClass = RegisterView.class, 
    template = "/register.mustache",
    params = {
      @Param(name = "username"),
      @Param(name = "password"),
      @Param(name = "recovery_email")})
public class Register {

  public RegisterService mRegister = new RegisterService();

  public RegisterView onGet(RegisterView view, HttpServletRequest req, HttpServletResponse resp) {
    return view;
  }

  public void onPost(RegisterView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mRegister.register(req, resp, 
        RegisterServlet.username,
        RegisterServlet.password, 
        RegisterServlet.recovery_email);
    view.registerErrorMessage = mRegister.mRegister.getErrorMessage();

    // Redirect back to the main page
    if (view.registerErrorMessage != null && view.registerErrorMessage.trim().length() != 0) {
      Responses.send303(req, resp);
    } else {
      String url = new MainPageUrlGenerator().build().toString();
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
