package org.denevell.natch.jerseymvc.screens.post.edit;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/thread_edit.mustache")
public class PostEditView {
  
  public boolean loggedIn;
  public boolean isAdmin;
  public String content;
  public String username;
  public String tags;
  public String subject;
  public String error;
  public String thread;
  public int postId;
}
