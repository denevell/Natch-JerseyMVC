package org.denevell.natch.jerseymvc.app.models;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class PostEditInput {

  @NotEmpty @NotBlank private String content;
  
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

}
