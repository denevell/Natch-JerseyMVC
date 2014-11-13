package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.ThreadDelete.ThreadDeleteConfirmView;
import org.denevell.natch.jerseymvc.services.ThreadDeleteService;
import org.denevell.natch.jerseymvc.utils.BaseView;
import org.denevell.natch.jerseymvc.utils.UrlGenerators;
import org.denevell.natch.jerseymvc.utils.Responses;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/thread/delete/*", 
    viewClass = ThreadDeleteConfirmView.class,
    template = "/thread_delete_confirm.mustache",
    params = {
      @Param(name = "delete_thread_id"),
      @Param(name = "delete_thread_id_form")})
public class ThreadDelete {

  private ThreadDeleteService mModel = new ThreadDeleteService();

  public ThreadDeleteConfirmView onGet(ThreadDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) {
		view.id = Integer.valueOf(ThreadDeleteServlet.delete_thread_id); 
		return view;
	}

  public void onPost(ThreadDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mModel.delete(req, ThreadDeleteServlet.delete_thread_id_form);
    if (mModel.errorMessage==null || mModel.errorMessage.trim().length()==0) {
      Responses.send303(resp, UrlGenerators.mainPage(req).toString());
    } else {
      view.errorMessage = mModel.errorMessage;
      Responses.send303(req, resp);
    }
  }

  public static class ThreadDeleteConfirmView extends BaseView {
    public ThreadDeleteConfirmView(HttpServletRequest request) {
      super(request);
    }
    public String errorMessage;
    public int id;
  }

}
