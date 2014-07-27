package org.denevell.natch.jerseymvc;

import javax.servlet.http.HttpServletRequest;

public interface Presenter<ModelInput, TemplateOutput> {
	
	TemplateOutput present(HttpServletRequest request, ModelInput object);

}
