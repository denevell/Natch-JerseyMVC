package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.PostDelete.PostDeleteConfirmView;
import org.denevell.natch.jerseymvc.services.PostDeleteService;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/post/delete/{delete_post_id}",
    viewClass = PostDeleteConfirmView.class,
    template = "/post_delete_confirm.mustache",
    params = {
      @Param(name = "thread"),
      @Param(name = "delete_post_id_form", type=ParamType.INT),
      @Param(name = "start", type=ParamType.INT, defaultValue="0"),
      @Param(name = "limit", type=ParamType.INT, defaultValue="10")},
    pathParams = {
      @Param(name = "delete_post_id", type=ParamType.INT)
    })
public class PostDelete {

  public PostDeleteConfirmView onGet(PostDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		view.id = PostDeleteServlet.delete_post_id; 
		view.parentThreadId =PostDeleteServlet.thread;
		view.start = PostDeleteServlet.start;
		view.limit = PostDeleteServlet.limit;
    return view;
  }

  public void onPost(PostDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		PostDeleteService service = new PostDeleteService();
    service.delete(req, PostDeleteServlet.delete_post_id_form);
		if (service.errorMessage==null || service.errorMessage.trim().length()==0) {
			String createThreadUrl = UrlGenerators
      	.createThreadUrl(req,
      			PostDeleteServlet.thread,
      			PostDeleteServlet.start, 
      			PostDeleteServlet.limit);
			Responses.send303(resp, createThreadUrl);
		} else {
		  view.errorMessage = service.errorMessage;
		  Responses.send303(req, resp);
		}
  }

  public static class PostDeleteConfirmView extends BaseView {
    public PostDeleteConfirmView(HttpServletRequest request) {
      super(request);
    }
    public String errorMessage;
    public int id;
    public String parentThreadId;
    public int start;
    public int limit;
  }

}
