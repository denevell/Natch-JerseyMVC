package org.denevell.natch.jerseymvc.utils;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

public class ViewBase {
  
  public ViewBase(HttpServletRequest request) {
    try {
      redirect_to = UriEncoding.encode(Urls.getUrlWithQueryString(request), null);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
		loggedIn = UtilsSession.isLoggedIn(request);
    isAdmin = UtilsSession.isAdmin(request);
    contextPath = request.getContextPath();
    loginErrorMessage = UtilsSession.getOneShotLoginError(request);
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
