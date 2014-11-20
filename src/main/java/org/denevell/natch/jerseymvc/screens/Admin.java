package org.denevell.natch.jerseymvc.screens;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.Admin.AdminView;
import org.denevell.natch.jerseymvc.services.ServiceOutputs;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserOutput;
import org.denevell.natch.jerseymvc.services.PwChangeService;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;

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

  public PwChangeService mPwChange = new PwChangeService();
  protected ServiceOutputs.UserListOutput mUsers;

  public AdminView onGet(AdminView view, HttpServletRequest req, HttpServletResponse resp) {
    Services.users(req, new ResponseObject() { @Override
      public void returned(Object o) {
        mUsers = (ServiceOutputs.UserListOutput) o;
      }
    });
    for (UserOutput userOutput : mUsers.users) {
      view.users.add(new AdminView.User(
          userOutput.username, 
          userOutput.recoveryEmail, 
          userOutput.resetPasswordRequest, 
          userOutput.admin));
    }
    return view;
  }

  public void onPost(AdminView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    if(AdminServlet.admintoggle_active!=null) {
      Services.userAdminToggle(req, AdminServlet.admintoggle_username);
    }

    mPwChange.changePw(
        AdminServlet.changepw_active, 
        req,
        AdminServlet.changepw_username, 
        AdminServlet.changepw_password);
    view.pwChangeProcessed = mPwChange.getProcessed();
    view.pwChangeError = mPwChange.errorMessage!=null;

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
