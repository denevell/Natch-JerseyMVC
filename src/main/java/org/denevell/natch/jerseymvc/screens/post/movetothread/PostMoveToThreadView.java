package org.denevell.natch.jerseymvc.screens.post.movetothread;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_movetothread.mustache")
public class PostMoveToThreadView {
	public String username;
	public int postId;
	public boolean isAdmin;
	public String moveError;
	public int limit;
	public int start;
}