package org.denevell.natch.web.jerseymvc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class SessionSetter {

	private HttpServletRequest mRequest; 
	private HashMap<String, String> mHm = new HashMap<String, String>();

    public static SessionSetter setSession(HttpServletRequest request) {
    	return new SessionSetter(request);
    }

    public static boolean sessionAlreadySet(HttpServletRequest request) {
    	return request.getSession()!=null && request.getSession().getAttribute("to")!=null;
    }
    
	@SuppressWarnings("unchecked")
	public SessionSetter(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute("to");
		if(attribute!=null) {
			mHm = (HashMap<String, String>) attribute;
		}
		mRequest = request;
	}

	public SessionSetter put(String name, String template) {
		put(name, template, false);
		return this;
	}

	public SessionSetter put(String name, String template, boolean overwrite) {
		if(overwrite || !mHm.containsKey(name)) {
			mHm.put(name, template);
		}
		return this;
	}

	public void build() {
		mRequest.getSession().setAttribute("to", mHm);
	}

}
