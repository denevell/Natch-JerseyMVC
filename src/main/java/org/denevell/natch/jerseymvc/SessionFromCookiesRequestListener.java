package org.denevell.natch.jerseymvc;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebListener
public class SessionFromCookiesRequestListener implements ServletRequestListener {

	@Override public void requestDestroyed(ServletRequestEvent sre) {}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
		HttpSession session = servletRequest.getSession();
		if(session.getAttribute("authkey")==null || session.getAttribute("authkey").toString().isEmpty()) {
	 		Cookie[] cookies = servletRequest.getCookies();
	 		if(cookies==null) return;
			for(Cookie cookie : cookies){
				session.setAttribute("admin", false);
			    if("name".equals(cookie.getName())){
					session.setAttribute("name", cookie.getValue());
			    }
			    if("authkey".equals(cookie.getName())){
					session.setAttribute("authkey", cookie.getValue());
			    }
			    if("admin".equals(cookie.getName())){
					session.setAttribute("admin", true);
			    }
			}	
		}
		
	}

}
