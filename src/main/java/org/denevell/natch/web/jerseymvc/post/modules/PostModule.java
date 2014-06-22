package org.denevell.natch.web.jerseymvc.post.modules;

import java.io.IOException;

import org.denevell.natch.web.jerseymvc.TemplateModule;
import org.denevell.natch.web.jerseymvc.post.io.PostOutput;

public class PostModule extends TemplateModule {
	
   	public PostOutput mPostOutput;

	@SuppressWarnings("unused")
	public String template(
			final int postId) throws IOException {
		mPostOutput = getPost(postId);
		return createTemplate("post.mustache", 
			new Object() {
				PostOutput post = mPostOutput;
        	});
	}
	
	public PostOutput getPost(int id) {
        mPostOutput = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("post").path("single")
                .path(String.valueOf(id))
                .request()
                .get(PostOutput.class); 	
        return mPostOutput;
	}
	

}
