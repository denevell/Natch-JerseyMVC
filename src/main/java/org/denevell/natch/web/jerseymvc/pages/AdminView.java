package org.denevell.natch.web.jerseymvc.pages;

import org.glassfish.jersey.server.mvc.Viewable;

public class AdminView {
	private String users;
	public AdminView users(String s) {
		this.users = s;
		return this;
	}

	@SuppressWarnings("unused")
	public Viewable create() {
		return new Viewable("/admin_index.mustache", 
			new Object() {
				String users() {
                        return users;
                }
			});
	}

}