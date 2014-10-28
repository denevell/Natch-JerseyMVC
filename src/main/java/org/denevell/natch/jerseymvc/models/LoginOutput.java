package org.denevell.natch.jerseymvc.models;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginOutput {
        private String authKey = "";
        private boolean admin;
        private String errorMessage;

        public String getAuthKey() {
                return this.authKey;
        }

        public void setAuthKey(String authKey) {
                this.authKey = authKey;
        }

        public boolean isAdmin() {
                return admin;
        }

        public void setAdmin(boolean admin) {
                this.admin = admin;
        }

    public String getErrorMessage() {
      return errorMessage;
    }

    public void setErrorMessage(String generalError) {
      this.errorMessage = generalError;
    }
}