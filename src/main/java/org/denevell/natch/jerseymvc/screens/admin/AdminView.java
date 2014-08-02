package org.denevell.natch.jerseymvc.screens.admin;

import java.util.ArrayList;
import java.util.List;

import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("/admin_index.mustache")
public class AdminView {
	
	public boolean pwChangeProcessed;
	public boolean pwChangeError;
	public List<User> users = new ArrayList<>();
	
	public static class User {
		public User(String username, String recoveryEmail, boolean resetPasswordRequest) {
			this.username = username;
			this.recoveryEmail = recoveryEmail;
			this.resetPasswordRequest = resetPasswordRequest;
		}
		public String username;
		public String recoveryEmail;
		public boolean resetPasswordRequest;
		
	}

}