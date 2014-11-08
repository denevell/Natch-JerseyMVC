package org.denevell.natch.jerseymvc.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.pegdown.PegDownProcessor;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PostOutput {

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
	
	public PostOutput() {
	}
	
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
		if(content==null) return null;
		String content2 = content;
		content2 = content2.replace("&gt;", ">");
		return content2;
	}
	public String getQuotedContent() {
		if(content==null) return null;
		String[] split = content.split("\n");
		for (int i = 0; i < split.length; i++) {
			split[i] = "> " + split[i];
			if(!split[i].matches("^.*\\n?\\r$")) {
				split[i] += "\n";
			}
			split[i] = split[i].replace("&gt;", ">");
		}
		String c = "";
		for (String string : split) {
			c += string;
		}
		return c+"\n"; // Newline is important to denote end of quoted text
	}
	public String getHtmlContent() {
		String c = content.replace("&gt;", ">");
		String html = new PegDownProcessor(org.pegdown.Extensions.ALL).markdownToHtml(c);
		return html;
    }

	public String getTagsString() {
		if(tags==null) return "";
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

