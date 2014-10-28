package org.denevell.natch.jerseymvc.screens;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.app.utils.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.screens.PostReply.PostReplyView;
import org.denevell.natch.jerseymvc.services.PostAddService;
import org.denevell.natch.jerseymvc.services.PostSingleService;
import org.denevell.natch.jerseymvc.services.ThreadsPaginationService;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/post/reply/{post}",
    viewClass = PostReplyView.class,
    template = "/post_reply.mustache",
    params = {
      @Param(name = "thread"),
      @Param(name = "start", type=ParamType.INT, defaultValue="0"),
      @Param(name = "limit", type=ParamType.INT, defaultValue="10"),
      @Param(name = "content")},
    pathParams = {
      @Param(name = "post", type=ParamType.INT)
    })
public class PostReply {
  
  PostSingleService mSinglePostService = new PostSingleService();
  PostAddService mAddPostService = new PostAddService();

  public PostReplyView onGet(PostReplyView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		mSinglePostService.fetchPost(new Object(), PostReplyServlet.post);
		view.start = PostReplyServlet.start;
		view.limit = PostReplyServlet.limit;
		view.id = PostReplyServlet.post;
		view.htmlContent = mSinglePostService.getPost().getQuotedContent();
		view.username = mSinglePostService.getPost().getUsername();
		view.threadId = PostReplyServlet.thread;
		view.getLastModifiedDateWithTime = mSinglePostService.getPost().getLastModifiedDateWithTime();
		return view;
  }

  public void onPost(PostReplyView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mAddPostService.add(new Object(), req, PostReplyServlet.content, PostReplyServlet.thread);
    if (mAddPostService.getAddpost().getErrorMessage() == null) {
      int numPosts = mAddPostService.getAddpost().getThread().getNumPosts();
      ThreadsPaginationService pagination = getPagination(numPosts);
      if (numPosts > PostReplyServlet.start + PostReplyServlet.limit) {
        Responses.send303(resp, pagination.getNext().toString());
      } else {
        Responses.send303(resp, pagination.getCurrent().toString());
      }
    } else {
      Responses.send303(req, resp);
    }
  }

	private ThreadsPaginationService getPagination(int numPosts) throws URISyntaxException {
    String url = new ThreadUrlGenerator().createThreadUrl(PostReplyServlet.thread);
    ThreadsPaginationService pag = new ThreadsPaginationService();
		pag.calculatePagination(url,
		    PostReplyServlet.start, 
		    PostReplyServlet.limit, 
				numPosts);
		return pag;
	}

  public static class PostReplyView extends BaseView {
    public PostReplyView(HttpServletRequest request) {
      super(request);
    }
    public int id;
    public String replyError = null;
    public String htmlContent;
    public String username;
    public String getLastModifiedDateWithTime;
    public String threadId;
    public String errorMessage;
    public int limit;
    public int start;
  }

}
