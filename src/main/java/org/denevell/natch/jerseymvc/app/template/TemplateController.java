package org.denevell.natch.jerseymvc.app.template;

import org.glassfish.jersey.server.mvc.Viewable;

public class TemplateController {

	public Viewable createTemplate(Object templateObject) {
		String name = templateObject.getClass().getAnnotation(TemplateName.class).value();
    	return new Viewable(name, templateObject);
	}

}
