package org.denevell.natch.jerseymvc.app.template;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.denevell.natch.jerseymvc.Presenter;

public class SessionSavingViewPresenter<View> implements Presenter<View> {
	protected View mView;

	public SessionSavingViewPresenter(Class<View> view) throws Exception {
		mView = view.newInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onGet(HttpServletRequest request) throws Exception {
    	mView = (View) Presenter.Utils.restoreViewFromSession(request, mView);
		return mView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response onPost(HttpServletRequest request) throws Exception {
		mView = (View) mView.getClass().newInstance();
    	Presenter.Utils.saveViewToSession(request, mView);
		return null;
	}
}
