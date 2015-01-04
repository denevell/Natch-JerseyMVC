package org.denevell.natch.jerseymvc.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.pegdown.PegDownProcessor;

public class ServiceOutputs {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PostOutput {

    public long id;
    public String username;
    public String errorMessage;
    public String subject;
    public String content;
    public String threadId;
    public long creation;
    public long modification;
    public List<String> tags;
    public boolean adminEdited;

    public PostOutput() {}

    public PostOutput(String username, long created, long modified, String subject, String content, List<String> tags, boolean adminEdit) {
      this.username = username;
      this.creation = created;
      this.modification = modified;
      this.subject = subject;
      this.content = content;
      this.tags = tags;
      this.adminEdited = adminEdit;
    }

    public PostOutput(PostOutput post) {
      this(post.username, post.creation, post.modification, post.subject, post.content, post.tags, post.adminEdited);
      this.threadId = post.threadId;
    }

    public String getContent() {
      if (content == null)
        return null;
      String content2 = content;
      content2 = content2.replace("&gt;", ">");
      return content2;
    }

    public String getQuotedContent() {
      if (content == null)
        return null;
      String[] split = content.split("\n");
      for (int i = 0; i < split.length; i++) {
        split[i] = "> " + split[i];
        if (!split[i].matches("^.*\\n?\\r$")) {
          split[i] += "\n";
        }
        split[i] = split[i].replace("&gt;", ">");
      }
      String c = "";
      for (String string : split) {
        c += string;
      }
      return c + "\n"; // Newline is important to denote end of quoted text
    }

    public String getHtmlContent() {
      String c = content.replace("&gt;", ">");
      String html = new PegDownProcessor(org.pegdown.Extensions.ALL).markdownToHtml(c);
      return html;
    }

    public String getTagsString() {
      if (tags == null)
        return "";
      List<String> ts = this.tags;
      String tagStrings = StringUtils.join(ts, ",");
      return tagStrings;
    }

    public String getLastModifiedDate() {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(modification);
      int dom = c.get(Calendar.DAY_OF_MONTH);
      String month = new SimpleDateFormat("MMM").format(c.getTime());
      int year = c.get(Calendar.YEAR);
      String dateString = dom + " " + month + " " + year;
      return dateString;
    }

    public String getLastModifiedDateWithTime() {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(modification);
      String dateString = new SimpleDateFormat("d MMM yyyy, K:mm:ss a").format(c.getTime());
      return dateString;
    }

    public boolean isAdminEdited() {
      return adminEdited;
    }

    public void setAdminEdited(boolean adminEdited) {
      this.adminEdited = adminEdited;
    }

    public String getErrorMessage() {
      return errorMessage;
    }

  }

  public static class ThreadOutput {

    public List<String> tags;
    public String id;
    public String subject;
    public String author;
    public PostsOutput posts;
    public int numPosts;
    public long creation;
    public long modification;
    public PostOutput rootPost;
    public PostOutput latestPost;
    public boolean mLoggedin;

    public ThreadOutput(ThreadOutput tr) {
      subject = tr.subject;
      author = tr.author;
      numPosts = tr.numPosts;
      tags = tr.tags;
      rootPost = tr.rootPost;
      latestPost = tr.latestPost;
    }

    public ThreadOutput(String subject, String author, long creation, long modification, List<String> tags) {
      this.tags = tags;
      this.subject = subject;
      this.author = author;
      this.creation = creation;
      this.modification = modification;
    }

    public ThreadOutput() {}

    public int getLatestPage(int postsPerPage) {
      double i = (double) this.numPosts / (double) postsPerPage;
      return (int) Math.ceil(i);
    }

    @XmlTransient
    public String getLastModifiedDate() {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(modification);
      int dom = c.get(Calendar.DAY_OF_MONTH);
      String month = new SimpleDateFormat("MMM").format(c.getTime());
      int year = c.get(Calendar.YEAR);
      String dateString = dom + " " + month + " " + year;
      return dateString;
    }

    public String getLastModifiedDateWithTime() {
      Calendar c = Calendar.getInstance();
      c.setTimeInMillis(modification);
      String dateString = new SimpleDateFormat("d MMM yyyy, K:mm:ss a").format(c.getTime());
      return dateString;
    }

    public long getRootPostId() {
      List<PostOutput> posts = getPosts();
      if (posts != null && posts.get(0) != null) {
        return posts.get(0).id;
      } else {
        return rootPost.id;
      }
    }

    int i = 0;

    int iterate() {
      return i++;
    }

    public List<PostOutput> getPosts() {
      return posts.results;
    }

    public boolean getLoggedinCorrectly() {
      return mLoggedin;
    }

  }

  public static class ThreadsOutput {
    public long count;
    public List<ThreadOutput> results = new ArrayList<ThreadOutput>();
    int i = 0;
    int iterate() {
      return i++;
    }
  }

  public static class PostsOutput {
    public long count;
    public List<PostOutput> results = new ArrayList<PostOutput>();
    int i = 0;
    int iterate() {
      return i++;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class UserLoginOutput {
    public String authKey = "";
    public boolean admin;
    public String errorMessage;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class UserRegisterOutput {
    public String error;
    public String errorMessage;
  }

  public static class UserListOutput {
    public long numUsers;
    public List<UserOutput> users = new ArrayList<UserOutput>();
  }

  public static class UserOutput {
    public String username;
    public boolean admin;
    public boolean resetPasswordRequest;
    public String recoveryEmail;
  }



}