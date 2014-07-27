package org.denevell.natch.jerseymvc.screens.threads.modules;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.denevell.natch.jerseymvc.app.template.TemplateModule;
import org.denevell.natch.jerseymvc.app.template.TemplateModule.TemplateName;

@TemplateName("threads_pagination.mustache")
public class ThreadsPaginationModule extends TemplateModule {
	
	private String mRequestUri;
	private int mStart;
	private int mLimit;
	private int mNumPosts;

	public void calculatePagination(String requestUri, int start, int limit, int numPosts) throws URISyntaxException {
		mRequestUri = requestUri;
		mStart = start;
		mLimit = limit;
		mNumPosts = numPosts;
	}

	public URI getNext() throws URISyntaxException {
		if(!(mStart+mLimit > mNumPosts)) {
			mStart += mLimit;
		}
		return createUriForPagination(mRequestUri, mStart, mLimit);
	}

	public URI getPrev() throws URISyntaxException {
		mStart -= mLimit;
		if(mStart<0) {
			mStart=0;
		}
		return createUriForPagination(mRequestUri, mStart, mLimit);
	}
	
	public String getPages() throws URISyntaxException {
		if(mRequestUri==null) return null;
    	StringBuffer numbers = new StringBuffer();
		float pagesFloat = (mNumPosts/ mLimit);
		int pages = (int) pagesFloat;
		if(pagesFloat!=0) {
			pages++;
		}
    	for (int i = 0; i < pages; i++) {
    		String s = createUriForPagination(mRequestUri, i*(mLimit), mLimit).toString();
    		String startP = "<a id=\"page"+(i+1)+"\" href=\""+s+"\">";
    		String endP = "</a> | ";
    		numbers.append(startP + String.valueOf(i+1) + endP);
		}
    	return numbers.toString();
	}

	private URI createUriForPagination(String requestUri, int start, int limit) throws URISyntaxException {
		if(requestUri==null) return null;
		URI uri = new URIBuilder(requestUri)
			.setParameter("start", String.valueOf(start))
			.setParameter("limit", String.valueOf(limit))
			.build();
		return uri;
	}


}
