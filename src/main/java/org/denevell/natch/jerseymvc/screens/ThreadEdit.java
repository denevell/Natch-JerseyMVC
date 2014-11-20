package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.denevell.natch.jerseymvc.screens.ThreadEdit.ThreadEditView;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.services.ThreadEditService;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/thread/edit/{post_edit}",
    viewClass = ThreadEditView.class,
    template = "/thread_edit.mustache",
    params = {
      @Param(name = "thread"),
      @Param(name = "subject"),
      @Param(name = "content"),
      @Param(name = "tags")},
    pathParams = {
      @Param(name = "post_edit", type = ParamType.INT)
    })
public class ThreadEdit {

  private ThreadEditService mThreadEditService = new ThreadEditService();
  private PostOutput postOutput;

  public ThreadEditView onGet(ThreadEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    Services.postSingle(req, ThreadEditServlet.post_edit, new ResponseObject() {
      @Override
      public void returned(Object o) {
        postOutput = (PostOutput) o;
      }
    });
		view.content = postOutput.getContent();
		view.username = postOutput.username;
		view.subject = postOutput.subject;
		view.tags = postOutput.getTagsString();
		view.thread= ThreadEditServlet.thread;
		view.postId = ThreadEditServlet.post_edit;
    return view;
  }

  public void onPost(ThreadEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mThreadEditService.fetch(req, 
        StringEscapeUtils.unescapeHtml4(ThreadEditServlet.subject), 
        StringEscapeUtils.unescapeHtml4(ThreadEditServlet.content), 
        StringEscapeUtils.unescapeHtml4(ThreadEditServlet.tags), 
        ThreadEditServlet.post_edit);
    String errorMessage = mThreadEditService.errorMessage;
    if(errorMessage==null || errorMessage.trim().length()==0) {
      String url = UrlGenerators.createThreadUrl(req, ThreadEditServlet.thread);
      Responses.send303(resp, url);
    } else {
      view.error = errorMessage;
      Responses.send303(req, resp);
    }
  }

  public static class ThreadEditView extends BaseView {
    public ThreadEditView(HttpServletRequest request) {
      super(request);
    }
    public String content;
    public String username;
    public String tags;
    public String subject;
    public String error;
    public String thread;
    public int postId;
  }

}
