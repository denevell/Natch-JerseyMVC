package org.denevell.natch.jerseymvc.utils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
	
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

}
