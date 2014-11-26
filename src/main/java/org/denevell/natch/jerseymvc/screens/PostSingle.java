package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.PostSingle.PostSingleView;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.ViewBase;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.Urls;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/post/{postId}",
    viewClass = PostSingleView.class,
    template = "/post_single.mustache",
    params = {},
    pathParams = {
      @Param(name = "postId", type=ParamType.INT)
    })
public class PostSingle {

  private PostOutput postOutput;

  public PostSingleView onGet(PostSingleView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Services.postSingle(req, PostSingleServlet.postId, new ResponseObject<PostOutput>() {
      @Override
      public void returned(PostOutput o) {
        postOutput = o;
      }
    });
		view.htmlContent = postOutput.getHtmlContent();
		view.username = postOutput.username;
		view.subject = postOutput.subject;
		view.backUrl = Urls.createThreadUrl(req, postOutput.threadId);
		return view;
  }

  public void onPost(PostSingleView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
  }

  public static class PostSingleView extends ViewBase {
    public PostSingleView(HttpServletRequest request) {
      super(request);
    }
    public String htmlContent;
    public String username;
    public String backUrl;
    public String subject;
  }

}
