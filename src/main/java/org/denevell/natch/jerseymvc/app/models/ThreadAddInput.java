package org.denevell.natch.jerseymvc.app.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class ThreadAddInput {

	@NotEmpty @NotBlank private String subject;
	@NotEmpty @NotBlank private String content;
	private String threadId;
	private List<String> tags = new ArrayList<String>();
	
	public ThreadAddInput() {
	}
	
	public ThreadAddInput (String subject, String content) {
		this.subject = subject;
		this.content = content;
	}
	
	public ThreadAddInput(String subject, String content, List<String> tags) {
		this.subject = subject;
		this.content = content;
		this.tags = tags;
	}
	
	public ThreadAddInput(String subject, String content, String threadId) {
		this.subject = subject;
		this.content = content;
		this.threadId = threadId;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getThreadId() {
		return threadId;
	}

	@XmlElement(required=false)
	public void setThreadId(String thread) {
		this.threadId = thread;
	}

	public List<String> getTags() {
		return tags;
	}

  @XmlElement(name="tags")
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setTags(String tags) {
		this.tags = Arrays.asList(tags.split(","));
	}

}

