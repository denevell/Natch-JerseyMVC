package org.denevell.natch.web.jerseymvc;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class TemplateModule {
	
	protected StringWriter writer;
	protected HttpServletRequest mRequest;
	private String mTemplateFile;
	protected static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface TemplateModuleInfo {
	    String value();
	    boolean overwrite() default false;
    }

	public TemplateModule(String templateFile) {
		mTemplateFile = templateFile;
		writer = new StringWriter();
	}
	
	protected String createTemplate(String templateName, Object object) {
    	sFactory
    		.compile(templateName)
    		.execute(writer,object);
		writer.flush();
		return writer.toString();
	}

	public String template(final HttpServletRequest request) throws Exception {
		mRequest = request;
		HashMap<String, Object> hm = new HashMap<>();
		for(PropertyDescriptor propertyDescriptor : 
		    Introspector.getBeanInfo(getClass(), Object.class).getPropertyDescriptors()){
		    Method readMethod = propertyDescriptor.getReadMethod();
		    String property = readMethod.getName().substring(3).toLowerCase();
			//System.out.println("#: " + property);
			hm.put(property, readMethod.invoke(this));
		}
		return createTemplate(mTemplateFile, hm);
	}

	@SuppressWarnings("unchecked")
	public static void storeSessionTemplateObjectFromTemplateModules(HttpServletRequest mRequest, Object ob) throws Exception {
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
			boolean overwrite = annotation.overwrite();
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

	public static Object getThenRemoveSessionTemplateObject(HttpServletRequest mRequest) {
    	Object attr = mRequest.getSession().getAttribute("to");
    	mRequest.getSession().removeAttribute("to");
    	return attr;
	}


}
