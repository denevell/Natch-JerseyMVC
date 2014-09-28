package org.denevell.natch.jerseymvc.screens.thread.single;

import java.util.ArrayList;
import java.util.List;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/thread_single.mustache")
public class ThreadView extends BaseView {
	
	public String addPostError;
	public int rootPostId;
	public String subject;
  public List<String> tags;
	public List<Post> posts = new ArrayList<>();
	public String next;
	public String prev;
	public String pages;

	public static class Post {
		public int iterate;
		public String htmlContent;
		public String username;
		public String lastModifiedDate;
		public boolean isAdmin;
		public int id;
		public boolean loggedInCorrectly;
		public boolean editedByAdmin;
		public String parentThreadId;
		public int returnToThreadFromDeletePostStartParam;
		public int returnToThreadFromDeletePostLimitParam;
		public int returnToThreadFromReplyStartParam;
    public int returnToThreadFromEditStartParam;
		public boolean hasEditThreadText;
    public boolean hasEditPostText;

		public Post(String username, String htmlContent, int id, int iterate, String lastModifiedDate) {
			this.username = username;
			this.htmlContent = htmlContent;
			this.id = id;
			this.iterate = iterate;
			this.lastModifiedDate = lastModifiedDate;
		}
	}

}