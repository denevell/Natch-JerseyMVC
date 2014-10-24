package org.denevell.natch.jerseymvc.screens.threads;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.services.PwResetService;
import org.denevell.natch.jerseymvc.app.services.RegisterService;
import org.denevell.natch.jerseymvc.app.services.ThreadAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.services.ThreadsService;
import org.denevell.natch.jerseymvc.app.template.SessionSavingViewPresenter;
import org.denevell.natch.jerseymvc.screens.thread.single.ThreadView;

public class ThreadsPresenter extends SessionSavingViewPresenter<ThreadsView> {

  private ThreadsController mController;
  public RegisterService mRegister = new RegisterService();
  public ThreadAddService mAddThread = new ThreadAddService();
  public PwResetService mResetPwModule = new PwResetService();
  public ThreadsPaginationService mPagination = new ThreadsPaginationService();
  public ThreadsService mThreadsService = new ThreadsService();

  public ThreadsPresenter(ThreadsController controller) throws Exception {
		super(new ThreadsView(controller.mRequest));
    mController = controller;
  }

  @Override
  public ThreadsView onGet(HttpServletRequest request) throws Exception {
    super.onGet(request);

    // Threads
    mThreadsService.fetchThreads(mController.start, mController.limit,
        mController.tag);
    for (int i = 0; i < mThreadsService.mThreads.getThreads().size(); i++) {
      ThreadOutput t = mThreadsService.mThreads.getThreads().get(i);
      ThreadsView.Thread e = new ThreadsView.Thread(t.getSubject(),
          t.getAuthor(), t.getLastModifiedDateWithTime(), t.getId(), i);
      List<String> tags = formatTagsList(t);
      e.numPages = getPagesPerThread(t);
      e.tags = tags;

      mView.threads.add(e);
    }

    // Pagination
    String requestUri = "";
    if(request.getQueryString()!=null && request.getQueryString().trim().length()>0) {
      requestUri = "?" + request.getQueryString();
    } else {
      requestUri = "";
    }
    mPagination.calculatePagination(
        request.getRequestURL() + requestUri,
        mController.start, mController.limit, mThreadsService.getNumThreads());
    mView.pages = mPagination.getPages();
    mView.next = mPagination.getNext().toString();
    mView.prev = mPagination.getPrev().toString();

    Presenter.Utils.clearViewStateFromSession(request, ThreadView.class);
    return mView;
  }

  private List<Integer> getPagesPerThread(ThreadOutput t) {
    ArrayList<Integer> num = new ArrayList<Integer>();
    int latestPage = t.getLatestPage(5);
    for (int i = 0; i < latestPage; i++) {
      num.add(808);
    }
    return num;
  }

  @Override
  public Response onPost(HttpServletRequest request) throws Exception {
    super.onPost(request);

    mAddThread.add(new Object(), request, mController.subject,
        mController.content, mController.tags);
    mView.addThreadErrorMessage = mAddThread.getAddThread().getErrorMessage();

    String url = request.getRequestURL() + "?" + request.getQueryString();
    return Response.seeOther(new URI(url)).build();
  }

  private List<String> formatTagsList(ThreadOutput t) {
    List<String> tags = t.getTags();
    for (int i = 0; i < tags.size(); i++) {
      String s = tags.get(i);
      if (s.length() > 15) {
        tags.set(i, s.substring(0, 15) + "...");
      }
    }
    Collections.sort(tags, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    if (tags.size() > 3) {
      tags = tags.subList(0, 3);
    }
    return tags;
  }

}
