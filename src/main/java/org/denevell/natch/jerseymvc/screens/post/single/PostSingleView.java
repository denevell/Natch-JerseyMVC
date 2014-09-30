package org.denevell.natch.jerseymvc.screens.post.single;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.BaseView;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("/post_single.mustache")
public class PostSingleView extends BaseView {
	
	public PostSingleView(HttpServletRequest request) {
    super(request);
  }
  public String htmlContent;
	public String username;
	public String backUrl;

}