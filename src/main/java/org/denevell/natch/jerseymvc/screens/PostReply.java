package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.PostReply.PostReplyView;
import org.denevell.natch.jerseymvc.services.ServiceInputs.PostAddInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.SessionUtils;
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
  
  Services mAddPostService = new Services();
  private PostOutput postOutput;

  public PostReplyView onGet(PostReplyView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Services.postSingle(req, ThreadEditServlet.post_edit, new ResponseObject() {
      @Override
      public void returned(Object o) {
        postOutput = (PostOutput) o;
      }
    });
		view.start = PostReplyServlet.start;
		view.limit = PostReplyServlet.limit;
		view.numPosts = PostReplyServlet.numPosts;
		view.id = PostReplyServlet.post;
		view.htmlContent = postOutput.getQuotedContent();
		view.username = postOutput.username;
		view.threadId = PostReplyServlet.thread;
		view.getLastModifiedDateWithTime = postOutput.getLastModifiedDateWithTime();
		return view;
  }

  public void onPost(PostReplyView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    view.replyError = Services.postAdd(
        SessionUtils.getAuthKey(req), 
        new PostAddInput(PostReplyServlet.content, PostReplyServlet.thread));
    if (view.replyError != null) {
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
