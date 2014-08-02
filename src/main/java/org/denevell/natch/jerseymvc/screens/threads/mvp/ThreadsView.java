package org.denevell.natch.jerseymvc.screens.threads.mvp;

import java.util.ArrayList;
import java.util.List;

import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("/threads_index1.mustache")
public class ThreadsView {
	
	public boolean loggedIn;
	public String registerErrorMessage;
	public String loginErrorMessage;
	public String addThreadErrorMessage;
	public boolean resetPasswordError;
	public boolean resetPassword;
	public boolean isAdmin;
	public boolean showResetForm;
	public String next;
	public String prev;
	public String pages;
	public List<Thread> threads = new ArrayList<>();
	public static class Thread {
		public Thread(String subject, String author, String lastModifiedDate, String id, int iterate) {
			this.subject = subject;
			this.author = author;
			this.lastModifiedDate = lastModifiedDate;
			this.id = id;
			this.iterate = iterate;
		}
		public int iterate;
		public String id;
		public String subject;
		public String author;
		public String lastModifiedDate;
	}

}