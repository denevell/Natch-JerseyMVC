package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.utils.MainPageUrlGenerator;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.screens.ThreadDeleteConfirm.ThreadDeleteConfirmView;
import org.denevell.natch.jerseymvc.services.ThreadDeleteService;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;

@ServletGenerator(
    path = "/thread/delete/*", 
    viewClass = ThreadDeleteConfirmView.class,
    template = "/thread_delete_confirm.mustache",
    params = {
      @Param(name = "delete_thread_id"),
      @Param(name = "delete_thread_id_form")})
public class ThreadDeleteConfirm {

  private ThreadDeleteService mModel = new ThreadDeleteService();

  public ThreadDeleteConfirmView onGet(ThreadDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) {
		view.id = Integer.valueOf(ThreadDeleteConfirmServlet.delete_thread_id); 
		return view;
	}

  public void onPost(ThreadDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mModel.delete(req, ThreadDeleteConfirmServlet.delete_thread_id_form);
    if (mModel.getDeleteThread().isSuccessful()) {
      Responses.send303(resp, new MainPageUrlGenerator().build().toString());
    } else {
      view.errorMessage = mModel.getDeleteThread().getErrorMessage();
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
