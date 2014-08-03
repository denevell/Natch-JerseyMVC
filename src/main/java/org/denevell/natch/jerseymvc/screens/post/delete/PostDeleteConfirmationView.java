package org.denevell.natch.jerseymvc.screens.post.delete;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post/delete/confirm.mustache")
public class PostDeleteConfirmationView {
	
	public String errorMessage;
	public boolean loggedIn;
	public int id;
	public String parentThreadId;
}