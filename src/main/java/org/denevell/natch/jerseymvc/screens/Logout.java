package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.services.LoginLogoutService;
import org.denevell.natch.jerseymvc.utils.Responses;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/logout/*", 
    viewClass = Void.class,
    template = "",
    params = {
      @Param(name = "redirect")})
public class Logout {

  public LoginLogoutService mLogin = new LoginLogoutService();

  public Void onGet(Object view, HttpServletRequest req, HttpServletResponse resp) {
    return null;
  }

  public void onPost(Object view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mLogin.logout(req, resp);
    Responses.send303ToUnsafeRedirectString(req, resp, LogoutServlet.redirect);
  }

}