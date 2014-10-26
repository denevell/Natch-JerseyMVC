package org.denevell.natch.jerseymvc.app.template;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.server.mvc.Viewable;

import com.github.mustachejava.DefaultMustacheFactory;

public class TemplateController {

	public Viewable createTemplate(HttpServletRequest request, Object templateObject) throws Exception {
		restoreVariablesSetInSession(request, templateObject);
	  Field[] fields = templateObject.getClass().getFields();
	  setTemplateIncludesOnTemplateObject(templateObject, fields);
		String name = templateObject.getClass().getAnnotation(TemplateName.class).value();
    return new Viewable(name, templateObject);
	}

	public void createRawTemplate(
	    HttpServletRequest request, 
	    HttpServletResponse resp, 
	    Object templateObject, 
	    String templateName) {
    try {
      restoreVariablesSetInSession(request, templateObject);
      Field[] fields = templateObject.getClass().getFields();
      setTemplateIncludesOnTemplateObject(templateObject, fields);
      PrintWriter writer = new PrintWriter(resp.getOutputStream());
      new DefaultMustacheFactory()
        .compile(templateName)
        .execute(writer, templateObject).flush();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
	}

  private void restoreVariablesSetInSession(HttpServletRequest request, Object templateObject) {
    Object ob = request.getSession(true).getAttribute("temp_state");
		if(ob!=null && ob instanceof HashMap) {
		  @SuppressWarnings({ "rawtypes", "unchecked" })
      HashMap<String, Object> hm = (HashMap) ob;
		  Set<String> keys = hm.keySet();
		  for (String key: keys) {
		    String value = (String) hm.get(key);
		      try {
		        Field field = templateObject.getClass().getField(key);
		        field.set(templateObject, value);
		      } catch(Exception e) {
		        e.printStackTrace();
		      }
      }
		}
		request.getSession(true).setAttribute("temp_state", new HashMap<String, Object>());
  }

  private void setTemplateIncludesOnTemplateObject(Object templateObject, Field[] fields) throws IOException, IllegalAccessException {
    for (Field field : fields) {
	    if(field.isAnnotationPresent(TemplateInclude.class)) {
	      TemplateInclude include = field.getAnnotation(TemplateInclude.class);
	      String template = include.file();
	      StringWriter stringWriter = new StringWriter();
        HashMap<String, Object> object = getTemplateIncludeObjectFromField(templateObject, field);
        new DefaultMustacheFactory().compile(template).execute(stringWriter, object).flush();
        field.set(templateObject, stringWriter.toString());
	    }
    }
  }

  private HashMap<String,Object> getTemplateIncludeObjectFromField(Object ob, Field field) {
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
