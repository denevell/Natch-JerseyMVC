package org.denevell.natch.jerseymvc.screens.thread.delete;

import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/thread_delete_confirm.mustache")
public class ThreadDeleteConfirmView {
	
	public String errorMessage;
	public boolean loggedIn;
	public int id;
}