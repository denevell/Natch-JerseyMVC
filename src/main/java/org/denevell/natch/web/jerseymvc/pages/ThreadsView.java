package org.denevell.natch.web.jerseymvc.pages;

import org.glassfish.jersey.server.mvc.Viewable;

public class ThreadsView {
	private String threads;
	private String login;
	private String register;
	private String logout;
	public ThreadsView threads(String t) {
		this.threads = t;
		return this;
	}
	public ThreadsView loginLogout(String s) {
		this.login = s;
		return this;
	}
	public ThreadsView logout(String s) {
		this.logout = s;
		return this;
	}
	public ThreadsView register(String t) {
		this.register = t;
		return this;
	}

	@SuppressWarnings("unused")
	public Viewable create() {
		return new Viewable("/threads_index.mustache", 
			new Object() {
				String threads() {
                        return threads;
                }
				String login() {
                        return login;
                }
				String register() {
                        return register;
                }
				String logout() {
                        return logout;
                }
			});
	}

}