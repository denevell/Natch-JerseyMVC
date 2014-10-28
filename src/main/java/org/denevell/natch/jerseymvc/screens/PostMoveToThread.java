package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.services.PostFromThreadService;
import org.denevell.natch.jerseymvc.app.services.PostSingleService;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.app.utils.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.screens.PostMoveToThread.PostMoveToThreadView;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/app/post/movetothread/{post}",
    viewClass = PostMoveToThreadView.class,
    template = "/post_movetothread.mustache",
    params = {
      @Param(name = "subject"),
      @Param(name = "content")},
    pathParams = {
      @Param(name = "post", type=ParamType.INT)
    })
public class PostMoveToThread {

	private PostSingleService mPostSingleService = new PostSingleService();
	private PostFromThreadService mPostFromThreadService = new PostFromThreadService();

  public PostMoveToThreadView onGet(PostMoveToThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		mPostSingleService.fetchPost(new Object(), PostMoveToThreadServlet.post);
		view.username = mPostSingleService.getPost().getUsername();
		view.postId = PostMoveToThreadServlet.post;
		return view;
  }

  public void onPost(PostMoveToThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(PostMoveToThreadServlet.subject==null || PostMoveToThreadServlet.subject.trim().length()==0) {
		  view.moveError="Must supply a subject";
			Responses.send303(req, resp);
		} else {
			mPostFromThreadService.fetchPost( req,
					PostMoveToThreadServlet.post, 
					PostMoveToThreadServlet.subject);
			view.moveError = mPostFromThreadService.mThreadOutput .getErrorMessage();
			if (view.moveError == null || view.moveError.trim().length() == 0) {
				String url = new ThreadUrlGenerator().createThreadUrl(mPostFromThreadService.mThreadOutput.getThread().getId());
				Responses.send303(resp, url);
			} else {
				Responses.send303(req, resp);
			}
		}
  }

  public static class PostMoveToThreadView extends BaseView {
    public PostMoveToThreadView(HttpServletRequest request) {
      super(request);
    }
    public String username;
    public int postId;
    public String moveError;
    public int limit;
    public int start;
  }

}
