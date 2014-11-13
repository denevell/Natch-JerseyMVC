package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.utils.Responses;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/login/*", 
    viewClass = Object.class, 
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
    req.getSession(true).setAttribute("loginErrorMessage", mLogin.getLogin().errorMessage);
    Responses.send303ToUnsafeRedirectString(req, resp, LoginServlet.redirect);
  }

}