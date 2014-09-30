package org.denevell.natch.jerseymvc.screens.register;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/register.mustache")
public class RegisterView extends BaseView {
	
	public RegisterView(HttpServletRequest request) {
    super(request);
  }
  public boolean hasauthkey;
	public String registerErrorMessage;
}