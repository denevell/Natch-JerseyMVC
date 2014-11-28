package org.denevell.natch.jerseymvc.screens;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.UserAdmin.AdminView;
import org.denevell.natch.jerseymvc.services.ServiceInputs.UserPasswordChangeInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserListOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.ViewBase;

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
public class UserAdmin {

  protected ServiceOutputs.UserListOutput mUsers;

  public AdminView onGet(AdminView view, HttpServletRequest req, HttpServletResponse resp) {
    Services.users(req, new ResponseObject<UserListOutput>() { @Override
      public void returned(UserListOutput o) {
        mUsers = o;
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
    if(UserAdminServlet.admintoggle_active!=null) {
      Services.userAdminToggle(req, UserAdminServlet.admintoggle_username);
    }
    if(UserAdminServlet.changepw_active!=null) {
      view.pwChangeError =  Services.userPwChange(req, UserAdminServlet.changepw_username, 
          new UserPasswordChangeInput(UserAdminServlet.changepw_password))!=null;
      view.pwChangeError =  Services.userPwResetRequestRemove(req, UserAdminServlet.changepw_username)!=null;
      view.pwChangeProcessed = !view.pwChangeError;
    }
    Serv.send303(req, resp);
  }

  public static class AdminView extends ViewBase {
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
