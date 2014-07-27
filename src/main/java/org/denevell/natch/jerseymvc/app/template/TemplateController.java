package org.denevell.natch.jerseymvc.app.template;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.Model;
import org.denevell.natch.jerseymvc.Presenter;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.MVP;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.UsedInGet;
import org.glassfish.jersey.server.mvc.Viewable;

import com.github.mustachejava.DefaultMustacheFactory;

public class TemplateController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void storeSessionTemplateObjectFromTemplateModules(HttpServletRequest mRequest, Object ob) throws Exception {
		// Create hash map object that sits on session
		Object attribute = mRequest.getSession().getAttribute("to");
		HashMap<String, Object> hm = new HashMap<>();
		if(attribute!=null) {
			 hm = (HashMap<String, Object>) attribute;
		}
		// Get all the TemplateModule fields
        Class<?> c = Class.forName(ob.getClass().getName());
        Field fields[] = c.getDeclaredFields();
        for (Field fld : fields) {
          if(TemplateModule.class.isAssignableFrom(fld.getType())) {
			System.out.println(fld);
			TemplateModuleInfo annotation = fld.getAnnotation(TemplateModuleInfo.class);
			String name = annotation.value();
			boolean overwrite = annotation.usedInGET();
        	TemplateModule x = (TemplateModule) fld.get(ob);
        	// Put the value, via template(), of the TemplateModule into the 
        	// HashMap with the annotation name as the key
        	if(overwrite || !hm.containsKey(name)) {
        		hm.put(name, x.template(mRequest));
        	}
          } else if(Model.class.isAssignableFrom(fld.getType())) {
			System.out.println(fld);
			
			// Get the variables in the annotation
			MVP annotation = fld.getAnnotation(MVP.class);
			String templateName = annotation.templateName();
			String hashName = annotation.templateValueName();
			boolean overwrite = fld.getAnnotation(UsedInGet.class)!=null;
			Presenter presenter = annotation.presenter().newInstance();
        	// Put the value, via template(), of the TemplateModule into the 
        	// HashMap with the annotation name as the key
        	if(overwrite || !hm.containsKey(hashName)) {

        		// Get the model output
        		Model model = (Model) fld.get(ob);
        		Object modelOutput = model.model();
        	
        		// Pass the model into the presenter
        		Object presenterOutput = presenter.present(mRequest, modelOutput);

        		// Create a template from the presenter
				String createTemplate = createTemplate(templateName, presenterOutput);

				hm.put(hashName, createTemplate);
        	}
          }
        }
        // Now set that to the session
		mRequest.getSession().setAttribute("to", hm);
	}
	
	@SuppressWarnings("unchecked")
	public void addToTemplateSession(HttpServletRequest request, String name, Object o) {
		Object attribute = request.getSession().getAttribute("to");
		HashMap<String, Object> hm = new HashMap<>();
		if(attribute!=null) {
			 hm = (HashMap<String, Object>) attribute;
		}
		hm.put(name, o);
		request.getSession().setAttribute("to", hm);
	}
	
	public String getTemplate() {
		return getClass().getAnnotation(TemplateName.class).value();
	}

	public Viewable viewableFromSession(HttpServletRequest request) {
    	Object attr = request.getSession().getAttribute("to");
    	request.getSession().removeAttribute("to");
    	return new Viewable(getTemplate(), attr);
	}

		protected StringWriter writer;
		protected static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
		
		protected String createTemplate(String templateName, Object object) {
			writer = new StringWriter();
	    	sFactory
	    		.compile(templateName)
	    		.execute(writer,object);
			writer.flush();
			return writer.toString();
		}


}
