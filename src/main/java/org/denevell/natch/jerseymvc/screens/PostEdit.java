package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.app.utils.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.screens.PostEdit.PostEditView;
import org.denevell.natch.jerseymvc.services.PostEditService;
import org.denevell.natch.jerseymvc.services.PostEditService.PostEditOutput;
import org.denevell.natch.jerseymvc.services.PostSingleService;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/post/edit/{post_edit}",
    viewClass = PostEditView.class,
    template = "/post_edit.mustache",
    params = {
      @Param(name = "thread"),
      @Param(name = "start", type=ParamType.INT, defaultValue="0"),
      @Param(name = "limit", type=ParamType.INT, defaultValue="10"),
      @Param(name = "content")},
    pathParams = {
      @Param(name = "post_edit", type=ParamType.INT)
    })
public class PostEdit {

  public PostEditService mPostEditService = new PostEditService();
	private PostSingleService mPostService = new PostSingleService();

  public PostEditView onGet(PostEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		mPostService.fetchPost(new Object(), PostEditServlet.post_edit);
		view.content = mPostService.getPost().getContent();
		view.thread= PostEditServlet.thread;
		view.postId = PostEditServlet.post_edit;
		view.start = PostEditServlet.start;
		view.limit = PostEditServlet.limit;
		return view;
  }

  public void onPost(PostEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mPostEditService.fetch(new Object(), req, 
        PostEditServlet.content, 
        PostEditServlet.post_edit);
    PostEditOutput output = mPostEditService.mOutput;
    if(output.errorMessage==null || output.errorMessage.trim().length()==0) {
      String url = new ThreadUrlGenerator().createThreadUrl(req, PostEditServlet.thread, PostEditServlet.start, PostEditServlet.limit);
		  Responses.send303(resp, url);
    } else {
      view.error = output.errorMessage;
		  Responses.send303(req, resp);
    }
  }

  public static class PostEditView extends BaseView {
    
    public PostEditView(HttpServletRequest request) {
      super(request);
    }
    public String content;
    public String error;
    public String thread;
    public int postId;
    public int start;
    public int limit;
  }

}
