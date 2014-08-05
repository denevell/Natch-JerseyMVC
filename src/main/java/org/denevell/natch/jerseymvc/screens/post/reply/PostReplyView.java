package org.denevell.natch.jerseymvc.screens.post.reply;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_reply.mustache")
public class PostReplyView {
	public boolean loggedIn = true;
	public String replyError = null;
}