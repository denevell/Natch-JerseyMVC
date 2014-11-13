package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.ThreadFromPost.PostMoveToThreadView;
import org.denevell.natch.jerseymvc.services.PostSingleService;
import org.denevell.natch.jerseymvc.services.ThreadFromPostService;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/post/movetothread/{post}",
    viewClass = PostMoveToThreadView.class,
    template = "/post_movetothread.mustache",
    params = {
      @Param(name = "subject"),
      @Param(name = "content")},
    pathParams = {
      @Param(name = "post", type=ParamType.INT)
    })
public class ThreadFromPost {

	private PostSingleService mPostSingleService = new PostSingleService();
	private ThreadFromPostService mPostFromThreadService = new ThreadFromPostService();

  public PostMoveToThreadView onGet(PostMoveToThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		mPostSingleService.fetchPost(new Object(), ThreadFromPostServlet.post);
		view.username = mPostSingleService.getPost().username;
		view.postId = ThreadFromPostServlet.post;
		return view;
  }

  public void onPost(PostMoveToThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(ThreadFromPostServlet.subject==null || ThreadFromPostServlet.subject.trim().length()==0) {
		  view.moveError="Must supply a subject";
			Responses.send303(req, resp);
		} else {
			mPostFromThreadService.fetchPost( req,
			    ThreadFromPostServlet.post, 
			    ThreadFromPostServlet.subject);
			view.moveError = mPostFromThreadService.errorMessage;
			if (view.moveError == null || view.moveError.trim().length() == 0) {
				String url = UrlGenerators.createThreadUrl(req, mPostFromThreadService.threadId);
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
