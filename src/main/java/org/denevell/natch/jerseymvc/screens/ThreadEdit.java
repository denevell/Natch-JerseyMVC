package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.app.utils.ThreadUrlGenerator;
import org.denevell.natch.jerseymvc.screens.ThreadEdit.ThreadEditView;
import org.denevell.natch.jerseymvc.services.PostSingleService;
import org.denevell.natch.jerseymvc.services.ThreadEditService;
import org.denevell.natch.jerseymvc.services.ThreadEditService.ThreadEditOutput;

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
	private PostSingleService mPostService = new PostSingleService();

  public ThreadEditView onGet(ThreadEditView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		mPostService.fetchPost(new Object(), ThreadEditServlet.post_edit);
		view.content = mPostService.getPost().getContent();
		view.username = mPostService.getPost().username;
		view.subject = mPostService.getPost().subject;
		view.tags = mPostService.getPost().getTagsString();
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
    ThreadEditOutput output = mThreadEditService.getOutput();
    if(output.errorMessage==null || output.errorMessage.trim().length()==0) {
      String url = new ThreadUrlGenerator().createThreadUrl(req, ThreadEditServlet.thread);
      Responses.send303(resp, url);
    } else {
      view.error = output.errorMessage;
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
