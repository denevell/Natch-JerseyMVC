package org.denevell.natch.jerseymvc.screens;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.ThreadFromPost.PostMoveToThreadView;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadAddFromPostResourceInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.Urls;
import org.denevell.natch.jerseymvc.utils.ViewBase;

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

  private PostOutput postOutput;
  private String mThreadId;

  public PostMoveToThreadView onGet(PostMoveToThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Services.postSingle(req, ThreadFromPostServlet.post, new ResponseObject<PostOutput>() {
      @Override
      public void returned(PostOutput o) {
        postOutput = o;
      }
    });
		view.username = postOutput.username;
		view.postId = ThreadFromPostServlet.post;
		return view;
  }

  public void onPost(PostMoveToThreadView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(ThreadFromPostServlet.subject==null || ThreadFromPostServlet.subject.trim().length()==0) {
		  view.moveError="Must supply a subject";
			Serv.send303(req, resp);
		} else {
		  view.moveError = Services.threadFromPost(req, 
		      new ThreadAddFromPostResourceInput(ThreadFromPostServlet.post, ThreadFromPostServlet.subject), 
		      new ResponseObject<HashMap<String, String>>() {
            @Override public void returned(HashMap<String, String> o) {
              mThreadId = o.get("threadId");
            }
          });
			if (view.moveError == null || view.moveError.trim().length() == 0) {
				String url = Urls.createThreadUrl(req, mThreadId);
				Serv.send303(resp, url);
			} else {
				Serv.send303(req, resp);
			}
		}
  }

  public static class PostMoveToThreadView extends ViewBase {
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
