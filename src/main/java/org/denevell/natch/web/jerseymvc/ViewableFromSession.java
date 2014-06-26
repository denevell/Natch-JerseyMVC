package org.denevell.natch.web.jerseymvc;

import javax.servlet.http.HttpSession;

import org.glassfish.jersey.server.mvc.Viewable;

public class ViewableFromSession extends Viewable {

	public ViewableFromSession(String templateName, Object model) throws IllegalArgumentException {
		super(templateName, model);
	}

	public ViewableFromSession(String templateName, HttpSession session) throws IllegalArgumentException {
		super(templateName, session.getAttribute("to"));
		session.removeAttribute("to");
	}

}
