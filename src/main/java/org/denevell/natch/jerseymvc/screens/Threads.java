package org.denevell.natch.jerseymvc.screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.models.ThreadOutput;
import org.denevell.natch.jerseymvc.app.services.ThreadAddService;
import org.denevell.natch.jerseymvc.app.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.app.services.ThreadsService;
import org.denevell.natch.jerseymvc.app.utils.Responses;
import org.denevell.natch.jerseymvc.app.utils.Urls;
import org.denevell.natch.jerseymvc.screens.Threads.ThreadsView;
import org.denevell.natch.jerseymvc.screens.Threads.ThreadsView.Thread.Tag;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "/app/index", 
    viewClass = ThreadsView.class,
    template = "/threads.mustache",
    params = {
      @Param(name = "start", type=ParamType.INT, defaultValue="0"),
      @Param(name = "limit", type=ParamType.INT, defaultValue="10"),
      @Param(name = "subject"),
      @Param(name = "content"),
      @Param(name = "tags"),
      @Param(name = "tag")})
public class Threads {

  public ThreadAddService mAddThreadService = new ThreadAddService();
  public ThreadsService mThreadsService = new ThreadsService();
  public ThreadsPaginationService mPagination = new ThreadsPaginationService();

  public ThreadsView onGet(ThreadsView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mThreadsService.fetchThreads(ThreadsServlet.start, ThreadsServlet.limit, ThreadsServlet.tag);
    for (int i = 0; i < mThreadsService.mThreads.getThreads().size(); i++) {
      ThreadOutput t = mThreadsService.mThreads.getThreads().get(i);
      ThreadsView.Thread e = new ThreadsView.Thread(t.getSubject(), t.getAuthor(), t.getLastModifiedDateWithTime(), t.getId(), i);
      e.numPages = getPagesPerThread(t);
      e.tags = sortAndTruncateTags(t.getTags());

      view.threads.add(e);
    }

    mPagination.calculatePagination(
        Urls.getUrlWithQueryString(req),
        ThreadsServlet.start, ThreadsServlet.limit, mThreadsService.getNumThreads());
    view.pages = mPagination.getPages();
    view.next = mPagination.getNext().toString();
    view.prev = mPagination.getPrev().toString();
    return view;
  }

  public void onPost(ThreadsView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    mAddThreadService.add(
        new Object(), 
        req, 
        ThreadsServlet.subject,
        ThreadsServlet.content, 
        ThreadsServlet.tags);
    view.addThreadErrorMessage = mAddThreadService.getAddThread().getErrorMessage();
    Responses.send303(req, resp);
  }

  private List<Tag> sortAndTruncateTags(List<String> tags) {
    Collections.sort(tags, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    if (tags.size() > 3) {
      tags = tags.subList(0, 3);
    }
    ArrayList<Tag> tagsOb = new ArrayList<Tag>();
    for (String t : tags) {
      tagsOb.add(new Tag(t));
    }
    return tagsOb;
  }

  private List<Integer> getPagesPerThread(ThreadOutput t) {
    ArrayList<Integer> num = new ArrayList<Integer>();
    int latestPage = t.getLatestPage(5);
    for (int i = 0; i < latestPage; i++) {
      num.add(808);
    }
    return num;
  }
  
  public static class ThreadsView extends BaseView {
    public ThreadsView(HttpServletRequest request) {
      super(request);
    }
    public String addThreadErrorMessage;
    public String next;
    public String prev;
    public String pages;
    public ArrayList<Thread> threads = new ArrayList<Thread>();
    public static class Thread {
      public Thread(String subject, String author, String lastModifiedDate, String id, int iterate) {
        this.subject = subject;
        this.author = author;
        this.lastModifiedDate = lastModifiedDate;
        this.id = id;
        this.iterate = iterate;
      }
      public int iterate;
      public List<Integer> numPages;
      public String id;
      public String subject;
      public String author;
      public String lastModifiedDate;
      public List<Tag> tags;
      public static class Tag {
        private String tag;
        public Tag(String tag) {
          this.tag = tag;
        }
        @Override public String toString() {
          if (tag.length() > 15) {
            return tag.substring(0, 15) + "...";
          } else {
            return tag;
          }
        }
      }
    }
  }

}
