package org.denevell.natch.jerseymvc.app.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;

public class SessionSavingViewPresenter<View> implements Presenter {
  protected View mView;

  public SessionSavingViewPresenter(View view) {
    mView = view;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public View onGet(HttpServletRequest request) {
    mView = (View) Presenter.Utils.restoreViewFromSession(request, mView);
    return mView;
  }

  @Override
  public Response onPost(HttpServletRequest request, HttpServletResponse resp) throws Exception {
    Presenter.Utils.saveViewToSession(request, mView);
    return null;
  }
}
