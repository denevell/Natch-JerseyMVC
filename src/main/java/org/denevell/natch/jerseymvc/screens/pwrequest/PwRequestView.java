package org.denevell.natch.jerseymvc.screens.pwrequest;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/pwrequest.mustache")
public class PwRequestView {
	
	public boolean loggedIn;
	public String loginErrorMessage;
	public boolean resetPasswordError;
	public boolean resetPassword;
	public boolean showResetForm;
}