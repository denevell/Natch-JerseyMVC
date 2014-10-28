package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.screens.PostSingle.PostSingleView;
import org.denevell.natch.jerseymvc.services.PostSingleService;

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

	private PostSingleService mPostService = new PostSingleService();

  public PostSingleView onGet(PostSingleView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		mPostService.fetchPost(new Object(), PostSingleServlet.postId);
		view.htmlContent = mPostService.getPost().getHtmlContent();
		view.username = mPostService.getPost().getUsername();
		view.subject = mPostService.mPostOutput.getSubject();
		view.backUrl = new ThreadUrlGenerator().createThreadUrl(mPostService.mPostOutput.getThreadId());
		return view;
  }

  public void onPost(PostSingleView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
  }

  public static class PostSingleView extends BaseView {
    public PostSingleView(HttpServletRequest request) {
      super(request);
    }
    public String htmlContent;
    public String username;
    public String backUrl;
    public String subject;
  }

}
