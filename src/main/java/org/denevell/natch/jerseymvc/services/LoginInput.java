package org.denevell.natch.jerseymvc.services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginInput {
        
        private String username;
        private String password;

        public LoginInput(String username, String password) {
                this.username = username;
                this.password = password;
        }
        
        public LoginInput() {
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
}