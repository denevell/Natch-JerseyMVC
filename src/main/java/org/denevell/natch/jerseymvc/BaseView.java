package org.denevell.natch.jerseymvc;

import org.denevell.natch.jerseymvc.app.template.TemplateInclude;

public class BaseView {
  @TemplateInclude(file="/common_header_elements.mustache", name="common_header_elements")
	public String common_header_elements;

  @TemplateInclude(file="/nav_bar.mustache", name="nav_bar")
	public String nav_bar;
}
