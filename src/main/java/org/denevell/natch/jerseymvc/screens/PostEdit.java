package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.PostEdit.PostEditView;
import org.denevell.natch.jerseymvc.services.ServiceInputs.PostEditInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;

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

  private PostOutput postOutput;

  public PostEditView onGet(PostEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Services.postSingle(req, ThreadEditServlet.post_edit, new ResponseObject() {
      @Override
      public void returned(Object o) {
        postOutput = (PostOutput) o;
      }
    });
		view.content = postOutput.getContent();
		view.thread= PostEditServlet.thread;
		view.postId = PostEditServlet.post_edit;
		view.start = PostEditServlet.start;
		view.limit = PostEditServlet.limit;
		return view;
  }

  public void onPost(PostEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String errorMessage = Services.postEdit(req, PostEditServlet.post_edit, 
        new PostEditInput(PostEditServlet.content));
    if(errorMessage==null || errorMessage.trim().length()==0) {
      String url = UrlGenerators.createThreadUrl(req, PostEditServlet.thread, PostEditServlet.start, PostEditServlet.limit);
		  Responses.send303(resp, url);
    } else {
      view.error = errorMessage;
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
