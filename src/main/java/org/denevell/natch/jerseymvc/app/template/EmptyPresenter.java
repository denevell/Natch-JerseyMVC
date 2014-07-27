package org.denevell.natch.jerseymvc.app.template;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.Presenter;

public class EmptyPresenter implements Presenter<Object, Object>{

	@Override
	public Object present(HttpServletRequest request, Object object) {
		return null;
	}

}
