package org.denevell.natch.jerseymvc;

import org.denevell.natch.jerseymvc.app.template.TemplateInclude;
import org.denevell.natch.jerseymvc.app.template.TemplateIncludeObjects;

public class BaseView {
  @TemplateInclude(file="/common_header_elements.mustache", name="common_header_elements")
	public String common_header_elements;

  @TemplateInclude(file="/nav_bar.mustache", name="nav_bar")
  @TemplateIncludeObjects(objects={"redirect_to", "isAdmin", "loggedIn", "loginErrorMessage", "loggedInCorrectly"})
	public String nav_bar;

	public String redirect_to;
	public boolean isAdmin;
	public boolean loggedIn;
	public String loginErrorMessage;
	public boolean loggedInCorrectly;
}
