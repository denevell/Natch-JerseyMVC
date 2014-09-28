package org.denevell.natch.jerseymvc.app.template;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;

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
        HashMap<String, Object> object = getTemplateObjectFromField(templateObject, field);
        new DefaultMustacheFactory().compile(template).execute(stringWriter, object).flush();
        field.set(templateObject, stringWriter.toString());
	    }
      
    }
		String name = templateObject.getClass().getAnnotation(TemplateName.class).value();
    return new Viewable(name, templateObject);
	}

  private HashMap<String,Object> getTemplateObjectFromField(Object ob, Field field) {
    HashMap<String, Object> templateObject = new HashMap<>();
    TemplateIncludeObjects include = field.getAnnotation(TemplateIncludeObjects.class);
    if(include==null) return null;
    String[] object = include.objects();
    for (String fieldName : object) {
      try {
        Field f2 = ob.getClass().getField(fieldName);
        Object f1 = f2.get(ob);
        templateObject.put(fieldName, f1);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    return templateObject;
  }

}
