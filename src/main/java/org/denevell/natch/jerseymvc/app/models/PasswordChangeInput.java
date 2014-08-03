package org.denevell.natch.jerseymvc.app.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PasswordChangeInput {
	private String password;
	public PasswordChangeInput(String pass) {
		this.password = pass;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}