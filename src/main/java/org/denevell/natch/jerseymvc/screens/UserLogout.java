package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.UtilsSession;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/logout/*", 
    viewClass = Void.class,
    template = "",
    params = {
      @Param(name = "redirect")})
public class UserLogout {

  public Void onGet(Object view, HttpServletRequest req, HttpServletResponse resp) {
    return null;
  }

  public void onPost(Object view, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
    Services.userLogout(req, new Runnable() { @Override
      public void run() {
		    UtilsSession.removeUserAsLoggedInWithSessionAndCookies(req, resp);
      }
    });
    Serv.send303ToRedirectString(req, resp, UserLogoutServlet.redirect);
  }

}