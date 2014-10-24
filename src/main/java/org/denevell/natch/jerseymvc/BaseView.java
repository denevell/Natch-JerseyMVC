package org.denevell.natch.jerseymvc;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.app.template.TemplateInclude;
import org.denevell.natch.jerseymvc.app.template.TemplateIncludeObjects;
import org.denevell.natch.jerseymvc.app.utils.UriEncoding;

public class BaseView {
  
  public static String arg_loginErrorMessage = "loginErrorMessage";
  
  public BaseView(HttpServletRequest request) {
    String qs = "";
    if(request.getQueryString()!=null && request.getQueryString().trim().length()>0) {
      qs = "?" + request.getQueryString();
    }
    redirect_to = UriEncoding.encode(request.getRequestURL() + qs, null);
		loggedIn = SessionUtils.isLoggedIn(request);
    isAdmin = request.getSession(true).getAttribute("admin") != null;
  }
  
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
