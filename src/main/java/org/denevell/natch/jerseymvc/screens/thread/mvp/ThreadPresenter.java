package org.denevell.natch.jerseymvc.screens.thread.mvp;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.models.PostOutput;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.screens.thread.mvp.ThreadPresenter.ThreadTemplate;
import org.denevell.natch.jerseymvc.screens.thread.mvp.ThreadPresenter.ThreadTemplate.Post;

@TemplateName("thread.mustache")
public class ThreadPresenter implements Presenter<ThreadOutput, ThreadTemplate>{
	
	@Override
	public ThreadTemplate present(HttpServletRequest request, ThreadOutput model) {
		
		// Get logged in user
    	Object name = request.getSession(true).getAttribute("name");
    	boolean correctUser;
		if(name!=null) {
    		correctUser = name.equals(model.getAuthor());
    	} else {
    		correctUser = false;
    	}
    	Object admin = request.getSession(true).getAttribute("admin");
    	
    	// Set template
		ThreadTemplate template = new ThreadTemplate();
		template.loggedInCorrectly = (admin!=null && ((boolean)admin)==true) || correctUser;
		template.rootPostId = model.getRootPostId();
		template.lastModifiedDate = model.getLastModifiedDate();
		template.subject = model.getSubject();
		// Set posts in template
		template.posts = new ArrayList<>();
		for (int i = 0; i < model.getPosts().size(); i++) {
			PostOutput p = model.getPosts().get(i);
			template.posts.add(new Post(p.getUsername(), p.getHtmlContent(), p.getId(), i)); 
		}
		
		return template;
   	}
   	
	public static class ThreadTemplate {
		public static class Post {
			public Post(String username, String htmlContent, long id, int iterate) {
				this.username = username;
				this.htmlContent = htmlContent;
				this.id = id;
				this.iterate = iterate;
			}
			public String username;
			public String htmlContent;
			public long id;
			public int iterate;
		}
		public boolean loggedInCorrectly;
		public long rootPostId;
		public String lastModifiedDate;
		public String subject;
		public int numPosts;
		public ArrayList<Post> posts;
	}
}
