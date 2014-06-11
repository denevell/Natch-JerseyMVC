package org.denevell.natch.web.jerseymvc.io;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class RegisterInput {
	
	@NotBlank private String username; 
	@NotBlank private String password;
	@Email(regexp=".+@.+\\..+") private String recoveryEmail;
	
	public RegisterInput(@NotBlank String username, 
			@NotBlank String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public RegisterInput(@NotBlank String username, 
			@NotBlank String password, 
			@Email String recoveryEmail) {
		this.setUsername(username);
		this.setPassword(password);
		this.setRecoveryEmail(recoveryEmail);
	}
	
	public RegisterInput() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRecoveryEmail() {
		return recoveryEmail;
	}

	public void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}


}
