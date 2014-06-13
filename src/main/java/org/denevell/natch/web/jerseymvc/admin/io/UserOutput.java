package org.denevell.natch.web.jerseymvc.admin.io;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserOutput {

    private String username;
	private boolean admin;
	private boolean resetPasswordRequest;
	private String recoveryEmail;
	
	public UserOutput() {
    }

	public UserOutput(String username, boolean isAdmin) {
        this.username = username;
        this.admin = isAdmin;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

	public boolean isResetPasswordRequest() {
		return resetPasswordRequest;
	}

	public void setResetPasswordRequest(boolean resetPasswordRequest) {
		this.resetPasswordRequest = resetPasswordRequest;
	}

	public String getRecoveryEmail() {
		return recoveryEmail;
	}

	public void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}
	

}