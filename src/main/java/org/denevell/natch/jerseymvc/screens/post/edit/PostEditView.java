package org.denevell.natch.jerseymvc.screens.post.edit;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_edit.mustache")
public class PostEditView extends BaseView {
  
  public PostEditView(HttpServletRequest request) {
    super(request);
  }
  public boolean loggedIn;
  public boolean isAdmin;
  public String content;
  public String error;
  public String thread;
  public int postId;
  public int start;
  public int limit;
}
