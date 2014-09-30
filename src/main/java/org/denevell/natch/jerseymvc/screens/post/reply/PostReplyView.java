package org.denevell.natch.jerseymvc.screens.post.reply;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_reply.mustache")
public class PostReplyView extends BaseView {
	public PostReplyView(HttpServletRequest request) {
    super(request);
  }
  public boolean loggedIn;
	public int id;
	public String replyError = null;
	public String htmlContent;
	public String username;
	public String getLastModifiedDateWithTime;
	public String threadId;
	public String errorMessage;
	public int limit;
	public int start;
}