package org.denevell.natch.web.jerseymvc;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
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


}
