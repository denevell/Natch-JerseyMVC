package org.denevell.natch.jerseymvc.screens.pwrequest;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/pwrequest.mustache")
public class PwRequestView extends BaseView {
	
	public PwRequestView(HttpServletRequest request) {
    super(request);
  }
  public boolean loggedIn;
	public String loginErrorMessage;
	public boolean resetPasswordError;
	public boolean resetPassword;
	public boolean showResetForm;
}