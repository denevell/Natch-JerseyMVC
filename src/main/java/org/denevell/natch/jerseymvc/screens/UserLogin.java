package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.services.ServiceInputs.UserLoginInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserLoginOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.UtilsSession;

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
public class UserLogin {

  public Void onGet(Object view, HttpServletRequest req, HttpServletResponse resp) {
    return null;
  }

  public void onPost(Object view, final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
    Services.userLogin(req, new UserLoginInput(UserLoginServlet.username, UserLoginServlet.password), new ResponseObject<UserLoginOutput>() { @Override
      public void returned(UserLoginOutput login) {
        UtilsSession.setUserAsLoggedInWithSessionAndCookies(req, resp, UserLoginServlet.username, login.authKey, login.admin);
        req.getSession(true).setAttribute("loginErrorMessage", login.errorMessage);
      }
    });
    Serv.send303ToRedirectString(req, resp, UserLoginServlet.redirect);
  }

}