package org.denevell.natch.jerseymvc.app.template;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mustachejava.DefaultMustacheFactory;

public class TemplateController {

	public void createRawTemplate(
	    HttpServletRequest request, 
	    HttpServletResponse resp, 
	    Object templateObject, 
	    String templateName) {
    try {
      setTemplateIncludesOnTemplateObject(templateObject);
      PrintWriter writer = new PrintWriter(resp.getOutputStream());
      new DefaultMustacheFactory()
        .compile(templateName)
        .execute(writer, templateObject).flush();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
	}

  private void setTemplateIncludesOnTemplateObject(Object templateObject) throws IOException, IllegalAccessException {
    Field[] fields = templateObject.getClass().getFields();
    for (Field field : fields) {
	    if(field.isAnnotationPresent(TemplateInclude.class)) {
	      TemplateInclude include = field.getAnnotation(TemplateInclude.class);
	      String template = include.file();
	      StringWriter stringWriter = new StringWriter();
        new DefaultMustacheFactory().compile(template).execute(stringWriter, templateObject).flush();
        field.set(templateObject, stringWriter.toString());
	    }
    }
  }

}
