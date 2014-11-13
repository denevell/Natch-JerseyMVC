package org.denevell.natch.jerseymvc.screens;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.ThreadSingle.ThreadView;
import org.denevell.natch.jerseymvc.screens.ThreadSingle.ThreadView.Post;
import org.denevell.natch.jerseymvc.services.PostAddService;
import org.denevell.natch.jerseymvc.services.PostOutput;
import org.denevell.natch.jerseymvc.services.ThreadService;
import org.denevell.natch.jerseymvc.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.SessionUtils;
import org.denevell.natch.jerseymvc.utils.Urls;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/thread/{threadId}",
    viewClass = ThreadView.class,
    template = "/thread_single.mustache",
    params = {
      @Param(name = "numPosts", type=ParamType.INT),
      @Param(name = "start", type=ParamType.INT),
      @Param(name = "limit", type=ParamType.INT),
      @Param(name = "content")},
    pathParams = {
      @Param(name = "threadId")
    })
public class ThreadSingle {

  PostAddService addPostService = new PostAddService();
	ThreadService mService;

  public ThreadView onGet(ThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mService = new ThreadService(ThreadSingleServlet.start, ThreadSingleServlet.limit, ThreadSingleServlet.threadId);
    view.loggedInCorrectly = getCorrectlyLoggedIn(req, mService.model().author);
    view.rootPostId = (int) mService.model().getRootPostId();
    view.subject = mService.model().subject;
    view.tags = mService.model().tags;

		// Set posts in template
		int postsSize = mService.model().getPosts().size();
		view.numPosts = postsSize;
		for (int i = 0; i < postsSize; i++) {
			PostOutput p = mService.model().getPosts().get(i);
			Post e = new Post(p.username, p.getHtmlContent(), (int)p.id, i, p.getLastModifiedDateWithTime());
			e.parentThreadId = mService.model().id;
			e.editedByAdmin = p.isAdminEdited();
			// Logged in info
			e.loggedInCorrectly = view.loggedInCorrectly;
			e.isAdmin = SessionUtils.isAdmin(req);
			// Pagination
			e.returnToThreadFromDeletePostLimitParam = ThreadSingleServlet.limit; 
			e.returnToThreadFromReplyStartParam = ThreadSingleServlet.start;
			e.returnToThreadFromEditStartParam = ThreadSingleServlet.start;
			e.returnToThreadFromDeletePostStartParam = ThreadSingleServlet.start;
			if(postsSize==1 && mService.model().numPosts > postsSize) {
				e.returnToThreadFromDeletePostStartParam -= 10;
			}
			// Can edit thread via this post
			if(i==0 && ThreadSingleServlet.start==0 && view.loggedInCorrectly) {
			  e.hasEditThreadText = true;
			}
			if((i!=0 || ThreadSingleServlet.start>1) && view.loggedInCorrectly) {
			  e.hasEditPostText = true;
			}
			if((i!=0 || ThreadSingleServlet.start>1) && e.isAdmin) {
			  e.hasMoveToThreadText= true;
			}
			view.posts.add(e); 
		}
    	
		// Pagination
		int numPosts = mService.model().numPosts;
		ThreadsPaginationService pagination = getPagination(req, numPosts);
		view.next = pagination.getNext().toString();
		view.prev = pagination.getPrev().toString();
		view.pages = pagination.getPages().toString();
    return view;
  }

  public void onPost(ThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    addPostService.add(new Object(), req, ThreadSingleServlet.content, ThreadSingleServlet.threadId);
    view.addPostError = addPostService.errorMessage;
    if(view.addPostError==null || view.addPostError.trim().length()==0) {
      int numPosts = ThreadSingleServlet.numPosts+1;
      ThreadsPaginationService pagination = getPagination(req, numPosts);
      if (numPosts > ThreadSingleServlet.start + ThreadSingleServlet.limit) {
        Responses.send303(resp, pagination.getNext().toString()); 
      } else {
        Responses.send303(req, resp); 
      }
    } else {
      Responses.send303(req, resp); 
    }
  }

	private ThreadsPaginationService getPagination(HttpServletRequest request, int numPosts) throws URISyntaxException {
		ThreadsPaginationService pagination = new ThreadsPaginationService();
		pagination.calculatePagination(
		    Urls.getRelativeUrlWithQueryString(request),
				ThreadSingleServlet.start, 
				ThreadSingleServlet.limit, 
				numPosts);
		return pagination;
	}

  private boolean getCorrectlyLoggedIn(HttpServletRequest request, String author) {
    Object name = request.getSession(true).getAttribute("name");
    boolean correctUser;
    if (name != null) {
      correctUser = name.equals(author);
    } else {
      correctUser = false;
    }
    Object admin = request.getSession(true).getAttribute("admin");
    return (admin != null && ((boolean) admin) == true) || correctUser;
  }

  public static class ThreadView extends org.denevell.natch.jerseymvc.utils.BaseView {
    public int numPosts;
    public ThreadView(HttpServletRequest request) {
      super(request);
    }
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
      public boolean hasMoveToThreadText;
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

}
