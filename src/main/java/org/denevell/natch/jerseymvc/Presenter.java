package org.denevell.natch.jerseymvc;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public interface Presenter<TemplateOutput> {
	TemplateOutput onGet(HttpServletRequest request) throws Exception;
	Response onPost(HttpServletRequest request) throws Exception;
	
	public static class Utils {
		public static void saveViewToSession(HttpServletRequest request, Object o) {
			request.getSession(true).setAttribute("view", o);
		}

		public static <View> void  clearViewStateFromSession(HttpServletRequest request, View c) throws Exception {
			request.getSession(true).setAttribute("view", new Object());
		}

		public static Object restoreViewFromSession(HttpServletRequest request, Object o) {
			Object view = request.getSession(true).getAttribute("view");
			if(view!=null && view.getClass() == o.getClass()) {
            	return view;
			} else {
				return o;
			}
		}
	}

}
