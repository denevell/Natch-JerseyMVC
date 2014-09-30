package org.denevell.natch.jerseymvc.screens.post.movetothread;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_movetothread.mustache")
public class PostMoveToThreadView extends BaseView {
	public PostMoveToThreadView(HttpServletRequest request) {
    super(request);
  }
  public String username;
	public int postId;
	public boolean isAdmin;
	public String moveError;
	public int limit;
	public int start;
}