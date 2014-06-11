package org.denevell.natch.web.jerseymvc.modules;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.web.jerseymvc.io.UserList;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.mustachejava.DefaultMustacheFactory;

public class AdminModule {
	
	private static JerseyClient sService = JerseyClientBuilder.createClient().register(JacksonFeature.class);
	private static DefaultMustacheFactory sFactory = new DefaultMustacheFactory();

   	@SuppressWarnings("unused")
	public String template(
			final HttpServletRequest request
			) throws IOException {

		Writer writer = new StringWriter();
		
		final UserList users = getUsers((String) request.getSession(true).getAttribute("authkey"));
        sFactory
            .compile("admin_users.mustache")
            .execute(writer, 
                    new Object() {
                        UserList users() {
                     		return users;
                     	}
            		});
		writer.flush();
		return writer.toString();
	}
	
	private UserList getUsers(String authKey) {
        return sService
                .target("http://localhost:8080/CoreUserService-ForAutomatedTests/")
                .path("rest").path("user").path("list")
                .request()
                .header("AuthKey", authKey)
                .get(UserList.class); 	
	}
	

}
