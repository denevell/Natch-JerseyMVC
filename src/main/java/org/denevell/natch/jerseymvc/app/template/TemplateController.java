package org.denevell.natch.jerseymvc.app.template;

import java.io.StringWriter;
import java.lang.reflect.Field;

import org.glassfish.jersey.server.mvc.Viewable;

import com.github.mustachejava.DefaultMustacheFactory;

public class TemplateController {

	public Viewable createTemplate(Object templateObject) throws Exception {
	  Field[] fields = templateObject.getClass().getFields();
	  for (Field field : fields) {
	    if(field.isAnnotationPresent(TemplateInclude.class)) {
	      TemplateInclude include = field.getAnnotation(TemplateInclude.class);
	      String template = include.file();
	      StringWriter stringWriter = new StringWriter();
        new DefaultMustacheFactory().compile(template).execute(stringWriter, new Object()).flush();
        field.set(templateObject, stringWriter.toString());
	    }
      
    }
		String name = templateObject.getClass().getAnnotation(TemplateName.class).value();
    return new Viewable(name, templateObject);
	}

}
