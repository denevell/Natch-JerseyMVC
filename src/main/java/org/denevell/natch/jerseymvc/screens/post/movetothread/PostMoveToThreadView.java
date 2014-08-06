package org.denevell.natch.jerseymvc.screens.post.movetothread;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_movetothread.mustache")
public class PostMoveToThreadView {
	public boolean loggedIn;
	public String moveError;
	public String content;
	public String username;
	public String postId;
	public String threadId;
	public int limit;
	public int start;
}