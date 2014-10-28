package org.denevell.natch.jerseymvc.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlAccessorType(XmlAccessType.FIELD)
public class ThreadOutput {

	private List<String> tags;
	private String id;
	private String subject;
	private String author;
	private List<PostOutput> posts;
	private int numPosts;
	private long creation;
	private long modification;
	private long rootPostId;
	private long latestPostId;
	private boolean mLoggedin; 

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String title) {
		this.subject = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumPosts() {
		return numPosts;
	}

	public void setNumPosts(int maxPages) {
		this.numPosts = maxPages;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLatestPage(int postsPerPage) {
		double i = (double) this.numPosts / (double) postsPerPage;
		return (int) Math.ceil(i);
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public long getModification() {
		return modification;
	}

	public void setModification(long modification) {
		this.modification = modification;
	}

	public long getCreation() {
		return creation;
	}

	public void setCreation(long creation) {
		this.creation = creation;
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
			return posts.get(0).getId();
		} else {
			return rootPostId;
		}
	}

	public void setRootPostId(long rootPostId) {
		this.rootPostId = rootPostId;
	}

	public long getLatestPostId() {
		return latestPostId;
	}

	public void setLatestPostId(long latestPostId) {
		this.latestPostId = latestPostId;
	}

	int i = 0;
	int iterate() { return i++; }
	public List<PostOutput> getPosts() {
		return posts;
	}

	public void setPosts(List<PostOutput> posts) {
		this.posts = posts;
	}

	public void setLoggedinCorrectly(boolean b) {
		mLoggedin = b;
	}
	
	public boolean getLoggedinCorrectly() {
		return mLoggedin;
	}

}