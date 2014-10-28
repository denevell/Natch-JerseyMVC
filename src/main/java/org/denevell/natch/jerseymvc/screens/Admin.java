package org.denevell.natch.jerseymvc.screens;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.app.utils.SessionUtils;
import org.denevell.natch.jerseymvc.models.UserOutput;
import org.denevell.natch.jerseymvc.screens.Admin.AdminView;
import org.denevell.natch.jerseymvc.services.AdminService;
import org.denevell.natch.jerseymvc.services.AdminToggleService;
import org.denevell.natch.jerseymvc.services.PwChangeService;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/admin/*", 
    viewClass = AdminView.class, 
    template = "/admin.mustache", 
    params = { 
      @Param(name = "changepw_username"),
      @Param(name = "changepw_password"),
      @Param(name = "changepw_active"),
      @Param(name = "admintoggle_active"),
      @Param(name = "admintoggle_username")})
public class Admin {

  public AdminService mAdmin = new AdminService();
  public PwChangeService mPwChange = new PwChangeService();
  public AdminToggleService mAdminToggleService = new AdminToggleService();

  public AdminView onGet(AdminView view, HttpServletRequest req, HttpServletResponse resp) {
    mAdmin.getUsers((String) req.getSession(true).getAttribute("authkey"));
    List<UserOutput> users = mAdmin.getUsers().getUsers();
    for (UserOutput userOutput : users) {
      view.users.add(new AdminView.User(
          userOutput.getUsername(), 
          userOutput.getRecoveryEmail(), 
          userOutput.isResetPasswordRequest(), 
          userOutput.isAdmin()));
    }
    return view;
  }

  public void onPost(AdminView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mAdminToggleService.toggle(
        AdminServlet.admintoggle_active,
        SessionUtils.getAuthKey(req), 
        AdminServlet.admintoggle_username);

    mPwChange.changePw(
        AdminServlet.changepw_active, 
        req,
        AdminServlet.changepw_username, 
        AdminServlet.changepw_password);
    view.pwChangeProcessed = mPwChange.getProcessed();
    view.pwChangeError = mPwChange.getError();

    Responses.send303(req, resp);
  }

  public static class AdminView extends BaseView {
    public AdminView(HttpServletRequest request) {
      super(request);
    }
    public boolean pwChangeProcessed;
    public boolean pwChangeError;
    public List<User> users = new ArrayList<>();
    public static class User {
      public User(String username, String recoveryEmail,
          boolean resetPasswordRequest, boolean admin) {
        this.username = username;
        this.recoveryEmail = recoveryEmail;
        this.resetPasswordRequest = resetPasswordRequest;
        this.admin = admin;
      }
      public boolean admin;
      public String username;
      public String recoveryEmail;
      public boolean resetPasswordRequest;
    }
  }

}
