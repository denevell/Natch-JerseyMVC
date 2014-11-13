package org.denevell.natch.jerseymvc.utils;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

public class BaseView {
  
  public BaseView(HttpServletRequest request) {
    try {
      redirect_to = UriEncoding.encode(Urls.getUrlWithQueryString(request), null);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
		loggedIn = SessionUtils.isLoggedIn(request);
    isAdmin = SessionUtils.isAdmin(request);
    contextPath = request.getContextPath();
    loginErrorMessage = SessionUtils.getOneShotLoginError(request);
  }
  
	public String common_header_elements;
	public String nav_bar;

	public String redirect_to;
	public boolean isAdmin;
	public boolean loggedIn;
  public String contextPath;
	public String loginErrorMessage;
	public boolean loggedInCorrectly;
}
