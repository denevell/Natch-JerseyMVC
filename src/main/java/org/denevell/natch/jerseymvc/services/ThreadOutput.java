package org.denevell.natch.jerseymvc.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public class ThreadOutput {

  public List<String> tags;
	public String id;
	public String subject;
	public String author;
	public List<PostOutput> posts;
	public int numPosts;
	public long creation;
	public long modification;
	public long rootPostId;
	public long latestPostId;
	public boolean mLoggedin; 

	public ThreadOutput(ThreadOutput tr) {
		subject = tr.subject;
		author = tr.author;
		numPosts = tr.numPosts;
		tags = tr.tags;
		rootPostId = tr.rootPostId;
		latestPostId = tr.latestPostId;
	}

	public ThreadOutput(String subject, String author, long creation,
			long modification, List<String> tags) {
		this.tags = tags;
		this.subject = subject;
		this.author = author;
		this.creation = creation;
		this.modification = modification;
	}

	public ThreadOutput() {
	}

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
		String dateString = new SimpleDateFormat("d MMM yyyy, K:mm:ss a")
				.format(c.getTime());
		return dateString;
	}

	public long getRootPostId() {
		List<PostOutput> posts = getPosts();
		if(posts!=null && posts.get(0)!=null) {
			return posts.get(0).id;
		} else {
			return rootPostId;
		}
	}

	int i = 0;
	int iterate() { return i++; }
	public List<PostOutput> getPosts() {
		return posts;
	}

	public boolean getLoggedinCorrectly() {
		return mLoggedin;
	}

}