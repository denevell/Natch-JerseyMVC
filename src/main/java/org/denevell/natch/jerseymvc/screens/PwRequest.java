package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.PwRequest.PwRequestView;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/pwrequest/*", 
    viewClass = PwRequestView.class,
    template = "/pwrequest.mustache",
    params = { 
      @Param(name = "resetpw_email")})
public class PwRequest {

  public PwRequestView onGet(PwRequestView view, HttpServletRequest req, HttpServletResponse resp) {
    return view;
  }

  public void onPost(PwRequestView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    view.resetPasswordError = Services.userPwReset(req, PwRequestServlet.resetpw_email)!=null;
    Responses.send303(req, resp);
  }

  public static class PwRequestView extends BaseView {
    public PwRequestView(HttpServletRequest request) {
      super(request);
    }
    public boolean resetPasswordError;
  }

}
