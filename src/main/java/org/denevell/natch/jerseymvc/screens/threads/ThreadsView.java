package org.denevell.natch.jerseymvc.screens.threads;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/threads.mustache")
public class ThreadsView extends BaseView {
	
	public ThreadsView(HttpServletRequest request) {
    super(request);
  }

  public String addThreadErrorMessage;
	public String next;
	public String prev;
	public String pages;
	public ArrayList<Thread> threads = new ArrayList<Thread>();
	public static class Thread {
		public Thread(String subject, String author, String lastModifiedDate, String id, int iterate) {
			this.subject = subject;
			this.author = author;
			this.lastModifiedDate = lastModifiedDate;
			this.id = id;
			this.iterate = iterate;
		}
		public int iterate;
		public List<Integer> numPages;
		public String id;
		public String subject;
		public String author;
		public String lastModifiedDate;
    public List<String> tags;
	}

}