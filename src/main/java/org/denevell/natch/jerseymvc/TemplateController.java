package org.denevell.natch.jerseymvc;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.TemplateModule.TemplateModuleInfo;
import org.denevell.natch.jerseymvc.TemplateModule.TemplateName;
import org.glassfish.jersey.server.mvc.Viewable;

public class TemplateController {

	@SuppressWarnings("unchecked")
	public void storeSessionTemplateObjectFromTemplateModules(HttpServletRequest mRequest, Object ob) throws Exception {
		// Create hash map object that sits on session
		Object attribute = mRequest.getSession().getAttribute("to");
		HashMap<String, String> hm = new HashMap<>();
		if(attribute!=null) {
			 hm = (HashMap<String, String>) attribute;
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
          }
        }
        // Now set that to the session
		mRequest.getSession().setAttribute("to", hm);
	}
	
	public String getTemplate() {
		return getClass().getAnnotation(TemplateName.class).value();
	}

	public Viewable viewableFromSession(HttpServletRequest request) {
    	Object attr = request.getSession().getAttribute("to");
    	request.getSession().removeAttribute("to");
    	return new Viewable(getTemplate(), attr);
	}


}
