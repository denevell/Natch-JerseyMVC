package org.denevell.natch.jerseymvc.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class ThreadEditInput {

  @NotEmpty @NotBlank private String content;
  @NotEmpty @NotBlank private String subject;
  private List<String> tags;
  
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public List<String> getTags() {
    return tags;
  }
  public void setTags(List<String> tags) {
    this.tags = tags;
  }

}
