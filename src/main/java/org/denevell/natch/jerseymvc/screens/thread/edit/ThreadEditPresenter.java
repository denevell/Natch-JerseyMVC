package org.denevell.natch.jerseymvc.screens.thread.edit;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.SessionUtils;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;

public class ThreadEditPresenter extends SessionSavingViewPresenter<ThreadEditView>  {
  
  //private SomeController mController;
  //public SomeService mSomeService = new SomeService();
  
  public ThreadEditPresenter(ThreadEditController controller) throws Exception {
    super(ThreadEditView.class);
    //mController = controller;
  }

  @Override
  public ThreadEditView onGet(HttpServletRequest request) throws Exception {
    super.onGet(request);
    
    // Logged in info
    mView.loggedIn = SessionUtils.isLoggedIn(request);
    mView.isAdmin = request.getSession(true).getAttribute("admin")!=null;

    Presenter.Utils.clearViewStateFromSEssion(request, ThreadEditView.class);
    return mView;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);

    //mSomeService.doSomething(request, null);
    //mView.someValue= mSomeService.someValue();

    String url = request.getRequestURL()+"?"+request.getQueryString(); 
    return Response.seeOther(new URI(url)).build();
  }


}
