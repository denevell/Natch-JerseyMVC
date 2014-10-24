package org.denevell.natch.jerseymvc.screens.thread.delete;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/thread_delete_confirm.mustache")
public class ThreadDeleteConfirmView extends BaseView {
	
	public ThreadDeleteConfirmView(HttpServletRequest request) {
    super(request);
  }
  public String errorMessage;
	public int id;
}