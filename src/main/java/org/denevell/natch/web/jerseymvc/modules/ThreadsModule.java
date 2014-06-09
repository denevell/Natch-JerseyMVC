package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.threads.io.ThreadsOutput;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class ThreadsModule {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();

   	@SuppressWarnings("unused")
	public static String template(
			final HttpServletRequest request,
			final int start,
			final int limit) throws IOException {

		Writer writer = new StringWriter();
		
		final ThreadsOutput threads = getThreads(start, limit);
        sFactory
            .compile("threads.mustache")
            .execute(writer, 
                    new Object() {
                        ThreadsOutput threads() {
                     		return threads;
                     	}
            		});
		writer.flush();
		return writer.toString();
	}
	
	public static ThreadsOutput getThreads(int start, int limit) {
        return sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("threads").path(String.valueOf(start)).path(String.valueOf(limit))
                .request()
                .get(ThreadsOutput.class); 	
	}
	

}
