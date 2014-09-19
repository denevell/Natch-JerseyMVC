package org.denevell.natch.jerseymvc.screens.post.delete;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_delete_confirm.mustache")
public class PostDeleteConfirmView extends BaseView {
	
	public String errorMessage;
	public boolean loggedIn;
	public int id;
	public String parentThreadId;
	public int start;
	public int limit;
}