package org.denevell.natch.jerseymvc.screens.thread.delete;

import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("/thread/delete/confirm.mustache")
public class ThreadDeleteConfirmationView {
	
	public String errorMessage;
	public boolean loggedIn;
	public int id;
}