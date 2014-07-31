package org.denevell.natch.jerseymvc;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
	
	public static boolean isLoggedIn(HttpServletRequest request) {
    	Object name = request.getSession(true).getAttribute("name");
    	return name !=null && name instanceof String && ((String)name).length()>0;
	}

}
