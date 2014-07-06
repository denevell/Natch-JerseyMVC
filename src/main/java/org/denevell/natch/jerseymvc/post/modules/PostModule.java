package org.denevell.natch.jerseymvc.post.modules;

import org.denevell.natch.jerseymvc.TemplateModule;
import org.denevell.natch.jerseymvc.post.io.PostOutput;

public class PostModule extends TemplateModule {
	
   	public PostOutput mPostOutput;
	
	public PostModule() {
		super("post.mustache");
	}
	
	public PostOutput getPost() {
		return mPostOutput;
	}
	
	public PostOutput fetchPost(int id) {
        mPostOutput = sService
                .target("http://localhost:8080/Natch-REST-ForAutomatedTests/")
                .path("rest").path("post").path("single")
                .path(String.valueOf(id))
                .request()
                .get(PostOutput.class); 	
        return mPostOutput;
	}
	

}
