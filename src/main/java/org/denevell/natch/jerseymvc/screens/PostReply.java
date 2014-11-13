package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.PostReply.PostReplyView;
import org.denevell.natch.jerseymvc.services.PostAddService;
import org.denevell.natch.jerseymvc.services.PostSingleService;
import org.denevell.natch.jerseymvc.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/post/reply/{post}",
    viewClass = PostReplyView.class,
    template = "/post_reply.mustache",
    params = {
      @Param(name = "thread"),
      @Param(name = "numPosts", type=ParamType.INT),
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
		view.numPosts = PostReplyServlet.numPosts;
		view.id = PostReplyServlet.post;
		view.htmlContent = mSinglePostService.getPost().getQuotedContent();
		view.username = mSinglePostService.getPost().username;
		view.threadId = PostReplyServlet.thread;
		view.getLastModifiedDateWithTime = mSinglePostService.getPost().getLastModifiedDateWithTime();
		return view;
  }

  public void onPost(PostReplyView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mAddPostService.add(new Object(), req, PostReplyServlet.content, PostReplyServlet.thread);
    if (mAddPostService.errorMessage != null) {
      Responses.send303(req, resp);
    } else {
      int numPosts = PostReplyServlet.numPosts+1;
      ThreadsPaginationService pagin = new ThreadsPaginationService().calculatePagination(
		    UrlGenerators.createThreadUrl(req, PostReplyServlet.thread),
		    PostReplyServlet.start, 
		    PostReplyServlet.limit, 
		    numPosts);
      if (numPosts > PostReplyServlet.start + PostReplyServlet.limit) {
        Responses.send303(resp, pagin.getNext().toString());
      } else {
        Responses.send303(resp, pagin.getCurrent().toString());
      }
    } 
  }

  public static class PostReplyView extends BaseView {
    public int numPosts;
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
