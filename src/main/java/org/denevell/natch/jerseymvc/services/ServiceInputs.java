package org.denevell.natch.jerseymvc.services;

public class ServiceInputs {

  public static class PostAddInput {
    public PostAddInput(String content, String threadId) {
      this.content = content;
      this.threadId = threadId;
    }
    public String content;
    public String threadId;
  }
  
  public static class PostEditInput {
    public PostEditInput(String content) {
      this.content = content;
    }
    public String content;
  }

  public static class RegisterInput {
    public RegisterInput(String username, String password, String recoveryEmail) {
      this.username = username;
      this.password = password;
      this.recoveryEmail = recoveryEmail;
    }
    public String username;
    public String password;
    public String recoveryEmail;
  }

}
