package org.denevell.natch.jerseymvc.screens.register;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/register.mustache")
public class RegisterView extends BaseView {
	
	public boolean hasauthkey;
	public String registerErrorMessage;
}