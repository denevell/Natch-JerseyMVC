package org.denevell.natch.jerseymvc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadAddInput.StringWrapper;

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

	public static class AddThreadFromPostResourceInput {
		public AddThreadFromPostResourceInput(long postId, String subject) {
      this.postId = postId;
      this.subject = subject;
    }
    public long postId;
		public String subject;
	}

  public static class ThreadEditInput {
    public ThreadEditInput(String content, String subject, String tags) {
      content = StringEscapeUtils.unescapeHtml4(content); 
      subject = StringEscapeUtils.unescapeHtml4(subject); 
      tags = StringEscapeUtils.unescapeHtml4(tags); 
      if(tags!=null) {
        List<String> ts = Arrays.asList(tags.split("[,\\s]+"));
        for (String string : ts) {
          this.tags.add(new StringWrapper(string));
        }
      }
      this.content = content;
      this.subject = subject;
    }
    public String content;
    public String subject;
    public List<StringWrapper> tags = new ArrayList<>();
  }

  public static class ThreadAddInput {
    
    public ThreadAddInput(String subject, String content, String threadId, String tags) {
      if(tags!=null) {
			List<String> tagStrings = Arrays.asList(tags.split("[,\\s]+"));
			for (String string : tagStrings) {
			  this.tags.add(new StringWrapper(string));
      }
      }
      this.subject = subject;
      this.content = content;
      this.threadId = threadId;
    }
    public String subject;
    public String content;
    public String threadId;
    public List<StringWrapper> tags = new ArrayList<>();
    public static class StringWrapper {
      public StringWrapper(String s) { string = s; }
      public String string;
    }
  }

  public static class PasswordChangeInput {
    public String password;
  }

  public static class LoginInput {
    public String username;
    public String password;
  }



}
