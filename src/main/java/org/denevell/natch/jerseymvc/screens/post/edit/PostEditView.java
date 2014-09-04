package org.denevell.natch.jerseymvc.screens.post.edit;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_edit.mustache")
public class PostEditView {
  
  public boolean loggedIn;
  public boolean isAdmin;
  public String content;
  public String error;
  public String thread;
  public int postId;
  public int start;
  public int limit;
}
