package org.denevell.natch.jerseymvc;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.app.utils.SessionUtils;
import org.denevell.natch.jerseymvc.app.utils.UriEncoding;
import org.denevell.natch.jerseymvc.app.utils.Urls;

public class BaseView {
  
  public BaseView(HttpServletRequest request) {
    try {
      redirect_to = UriEncoding.encode(Urls.getUrlWithQueryString(request), null);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
		loggedIn = SessionUtils.isLoggedIn(request);
    isAdmin = SessionUtils.isAdmin(request);
    loginErrorMessage = SessionUtils.getOneShotLoginError(request);
  }
  
	public String common_header_elements;
	public String nav_bar;

	public String redirect_to;
	public boolean isAdmin;
	public boolean loggedIn;
	public String loginErrorMessage;
	public boolean loggedInCorrectly;
}
