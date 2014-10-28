package org.denevell.natch.jerseymvc.screens;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.services.LoginLogoutService;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/app/login", 
    viewClass = Void.class, 
    template = "",
    params = {
      @Param(name = "username"),
      @Param(name = "password"),
      @Param(name = "redirect")})
public class Login {

  public LoginLogoutService mLogin = new LoginLogoutService();

  public Void onGet(Object view, HttpServletRequest req, HttpServletResponse resp) {
    return null;
  }

  public void onPost(Object view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mLogin.login(req, resp, LoginServlet.username, LoginServlet.password);
    HashMap<String, Object> value = new HashMap<String, Object>();
    req.getSession(true).setAttribute("temp_state", value);
    value.put(BaseView.arg_loginErrorMessage, mLogin.getLogin().getErrorMessage());
    Responses.send303(resp, LoginServlet.redirect);
  }

}