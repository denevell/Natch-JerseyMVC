package org.denevell.natch.web.jerseymvc;

import java.io.StringWriter;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class TemplateModule {
	
	protected StringWriter writer;
	protected static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

	public TemplateModule() {
		writer = new StringWriter();
	}
	
	protected String createTemplate(String templateName, Object object) {
    	sFactory
    		.compile(templateName)
    		.execute(writer,object);
		writer.flush();
		return writer.toString();
	}


}
