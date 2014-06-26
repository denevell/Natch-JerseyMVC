package org.denevell.natch.web.jerseymvc.admin.io;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangePasswordInput {
	private String password;
	public ChangePasswordInput(String pass) {
		this.password = pass;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}