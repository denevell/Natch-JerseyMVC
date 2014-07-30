package org.denevell.natch.jerseymvc.app.services;

import org.denevell.natch.jerseymvc.app.models.PostOutput;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("post.mustache")
public class PostService extends TemplateModule {
	
   	public PostOutput mPostOutput;
	
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
