package org.denevell.natch.jerseymvc.screens.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/admin.mustache")
public class AdminView extends BaseView {
	
	public AdminView(HttpServletRequest request) {
    super(request);
  }

  public boolean pwChangeProcessed;
	public boolean pwChangeError;
	public List<User> users = new ArrayList<>();
	
	public static class User {
		public User(String username, String recoveryEmail, 
				boolean resetPasswordRequest,
				boolean admin) {
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