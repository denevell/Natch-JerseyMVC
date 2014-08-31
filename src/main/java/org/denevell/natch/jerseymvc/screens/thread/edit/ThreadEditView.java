package org.denevell.natch.jerseymvc.screens.thread.edit;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/thread_edit.mustache")
public class ThreadEditView {
  
  public boolean loggedIn;
  public boolean isAdmin;
  public String content;
  public String username;
  public String tags;
  public String subject;
}
