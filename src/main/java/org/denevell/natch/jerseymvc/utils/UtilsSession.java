package org.denevell.natch.jerseymvc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UtilsSession {

	private static int ONE_YEAR = 60*60*24*365;
	
	public static boolean isLoggedIn(HttpServletRequest request) {
    	Object name = request.getSession(true).getAttribute("name");
    	return name !=null && name instanceof String && ((String)name).length()>0;
	}

	public static boolean getCorrectlyLoggedIn(HttpServletRequest request, String author) {
    Object name = request.getSession(true).getAttribute("name");
    boolean correctUser;
    if (name != null) {
      correctUser = name.equals(author);
    } else {
      correctUser = false;
    }
    Object admin = request.getSession(true).getAttribute("admin");
    return (admin != null && ((boolean) admin) == true) || correctUser;
  }

	public static boolean isAdmin(HttpServletRequest request) {
    	Object name = request.getSession(true).getAttribute("admin");
    	return name!=null && name instanceof Boolean && ((boolean)name)==true;
	}

	public static String getAuthKey(HttpServletRequest request) {
    	return (String)request.getSession(true).getAttribute("authkey");
	}

  public static String getOneShotLoginError(HttpServletRequest request) {
    Object attribute = request.getSession(true).getAttribute("loginErrorMessage");
    if(attribute!=null && attribute instanceof String) {
      request.getSession(true).removeAttribute("loginErrorMessage");
      return (String) attribute;
    } else {
      return null;
    }
  }

  public static void setUserAsLoggedInWithSessionAndCookies(
      HttpServletRequest request, 
      HttpServletResponse response, 
      String username,
      String authKey,
      boolean admin) {
    if (authKey != null && authKey.trim().length() > 0) {
      request.getSession(true).setAttribute("loggedin", true);
      request.getSession(true).setAttribute("authkey", authKey);
      request.getSession(true).setAttribute("name", username);

      Cookie authKeyCookie = new Cookie("authkey", authKey);
      authKeyCookie.setMaxAge(ONE_YEAR);
      response.addCookie(authKeyCookie);
      Cookie usernameCookie = new Cookie("name", username);
      usernameCookie.setMaxAge(ONE_YEAR);
      response.addCookie(usernameCookie);
    }
    if (admin) {
      request.getSession(true).setAttribute("admin", true);
      Cookie isAdmin = new Cookie("admin", "yeah");
      isAdmin.setMaxAge(ONE_YEAR);
      response.addCookie(isAdmin);
    }
  }

  public static void removeUserAsLoggedInWithSessionAndCookies(HttpServletRequest req, 
      HttpServletResponse resp) {
			req.getSession(true).setAttribute("loggedin", null);
			req.getSession(true).setAttribute("admin", null);
			req.getSession(true).setAttribute("name", null);
			req.getSession(true).setAttribute("authkey", null);

			Cookie authKeyCookie = new Cookie("authkey", "");
			authKeyCookie.setMaxAge(0);
			resp.addCookie(authKeyCookie);

			Cookie isAdmin = new Cookie("admin", "");
			isAdmin.setMaxAge(0);
			resp.addCookie(isAdmin);

			Cookie usernameCookie = new Cookie("name", "");
			usernameCookie.setMaxAge(0);
			resp.addCookie(usernameCookie);	
  }

}
