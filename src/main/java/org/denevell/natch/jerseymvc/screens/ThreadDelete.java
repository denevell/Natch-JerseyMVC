package org.denevell.natch.jerseymvc.screens;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.screens.ThreadDelete.ThreadDeleteConfirmView;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Urls;
import org.denevell.natch.jerseymvc.utils.ViewBase;

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

  public ThreadDeleteConfirmView onGet(ThreadDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) {
		view.id = Integer.valueOf(ThreadDeleteServlet.delete_thread_id); 
		return view;
	}

  public void onPost(ThreadDeleteConfirmView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    view.errorMessage = Services.threadDelete(req, ThreadDeleteServlet.delete_thread_id_form);
    if (view.errorMessage==null || view.errorMessage.trim().length()==0) {
      Serv.send303(resp, Urls.mainPage(req).toString());
    } else {
      Serv.send303(req, resp);
    }
  }

  public static class ThreadDeleteConfirmView extends ViewBase {
    public ThreadDeleteConfirmView(HttpServletRequest request) {
      super(request);
    }
    public String errorMessage;
    public int id;
  }

}
