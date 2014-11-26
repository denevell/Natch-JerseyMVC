package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.UserPwRequest.PwRequestView;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.ViewBase;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/pwrequest/*", 
    viewClass = PwRequestView.class,
    template = "/pwrequest.mustache",
    params = { 
      @Param(name = "resetpw_email")})
public class UserPwRequest {

  public PwRequestView onGet(PwRequestView view, HttpServletRequest req, HttpServletResponse resp) {
    return view;
  }

  public void onPost(PwRequestView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    view.resetPasswordError = Services.userPwReset(req, UserPwRequestServlet.resetpw_email)!=null;
    Serv.send303(req, resp);
  }

  public static class PwRequestView extends ViewBase {
    public PwRequestView(HttpServletRequest request) {
      super(request);
    }
    public boolean resetPasswordError;
  }

}
