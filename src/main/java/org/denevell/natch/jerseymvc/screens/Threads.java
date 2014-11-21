package org.denevell.natch.jerseymvc.screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.screens.Threads.ThreadsView;
import org.denevell.natch.jerseymvc.screens.Threads.ThreadsView.Thread.Tag;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadAddInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadsOutput;
import org.denevell.natch.jerseymvc.services.Services;
import org.denevell.natch.jerseymvc.services.ThreadsPaginationService;
import org.denevell.natch.jerseymvc.utils.Responses;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.SessionUtils;
import org.denevell.natch.jerseymvc.utils.Urls;

import com.yeah.ServletGenerator;
import com.yeah.ServletGenerator.Param;
import com.yeah.ServletGenerator.Param.ParamType;

@ServletGenerator(
    path = "", 
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

  public ThreadsPaginationService mPagination = new ThreadsPaginationService();
  public ThreadsOutput mThreads;

  public ThreadsView onGet(ThreadsView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
    ResponseObject callback = new ResponseObject() { @Override public void returned(Object o) {
      mThreads = (ThreadsOutput) o;
      }
    };
    if(ThreadsServlet.tag==null) {
      Services.threads(req, ThreadsServlet.start, ThreadsServlet.limit, callback);
    } else {
      Services.threads(req, ThreadsServlet.tag, ThreadsServlet.start, ThreadsServlet.limit, callback);
    }
    for (int i = 0; i < mThreads.threads.size(); i++) {
      ThreadOutput t = mThreads.threads.get(i);
      ThreadsView.Thread e = new ThreadsView.Thread(t.subject, t.author, t.getLastModifiedDateWithTime(), t.id, i);
      e.numPages = getPagesPerThread(t);
      e.tags = sortAndTruncateTags(t.tags);

      view.threads.add(e);
    }

    mPagination.calculatePagination(
        Urls.getRelativeUrlWithQueryString(req),
        ThreadsServlet.start, ThreadsServlet.limit, (int) mThreads.numOfThreads);
    view.pages = mPagination.getPages();
    view.next = mPagination.getNext().toString();
    view.prev = mPagination.getPrev().toString();
    return view;
  }

  public void onPost(ThreadsView view, HttpServletRequest req, HttpServletResponse resp) throws Exception {
	  String authKey = SessionUtils.getAuthKey(req);
    if(authKey ==null || authKey.toString().trim().length()==0) {
			  Response.status(401).build();
		}
    view.addThreadErrorMessage = Services.threadAdd(req, 
        new ThreadAddInput(ThreadsServlet.subject, ThreadsServlet.content, null, ThreadsServlet.tags));
    if(view.addThreadErrorMessage!=null && !view.addThreadErrorMessage.isEmpty()) {
      view.subject = ThreadsServlet.subject;
      view.content = ThreadsServlet.content; 
      view.tags = ThreadsServlet.tags;
    }
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
  
  public static class ThreadsView extends org.denevell.natch.jerseymvc.utils.BaseView {
    public ThreadsView(HttpServletRequest request) {
      super(request);
    }
    public String tags;
    public String content;
    public String subject;
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
