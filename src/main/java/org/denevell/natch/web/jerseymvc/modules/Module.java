package org.denevell.natch.web.jerseymvc.modules;

import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class Module {
	
	protected StringWriter writer;
	protected static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();
	protected static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);

	public Module() {
		writer = new StringWriter();
	}
	
	protected String createTemplate(String templateName, Object object) {
    	sFactory
    		.compile(templateName)
    		.execute(writer,object);
		writer.flush();
		return writer.toString();
	}
	
	protected Serv serv(Runnable r) {
		try {
			r.run();
			return new Serv();
		} catch(WebApplicationException e) {
			return new Serv(e.getResponse().getStatus());
		} catch(Exception e) {
			return new Serv(-1);
		}
	}

	public class Serv {
		private int errorCode;
		private Runnable _500;
		private Runnable _403;
		private Runnable _400;
		private Runnable _exception;
		public Serv(int i) {
			errorCode = i;
		}
		public Serv() {
		}
		public Serv _500(Runnable r) {
			_500 = r;
			return this;
		}
		public Serv _403(Runnable r) {
			_403 = r;
			return this;
		}
		public Serv _400(Runnable r) {
			_400 = r;
			return this;
		}
		public Serv _exception(Runnable r) {
			_exception = r;
			return this;
		}
		public void go() {
			switch (errorCode) {
			case 0:
				break;
			case 500:
				_500.run();
				break;
			case 400:
				_400.run();
				break;
			case 403:
				_403.run();
			default:
				_exception.run();
				break;
			}
			
		}
	
	}


}
