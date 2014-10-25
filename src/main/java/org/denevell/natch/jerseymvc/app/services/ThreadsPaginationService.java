package org.denevell.natch.jerseymvc.app.services;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.denevell.natch.jerseymvc.app.template.TemplateName;

@TemplateName("threads_pagination.mustache")
public class ThreadsPaginationService {
	
	private String mRequestUri;
	private int mStart;
	private int mLimit;
	private int mNumPosts;

	public void calculatePagination(String requestUri, int start, int limit, int numPosts) {
		mRequestUri = requestUri;
		mStart = start;
		mLimit = limit;
		mNumPosts = numPosts;
	}

	public URI getNext() {
		if(!(mStart+mLimit > mNumPosts)) {
			mStart += mLimit;
		}
		return createUriForPagination(mRequestUri, mStart, mLimit);
	}

	public URI getCurrent() throws URISyntaxException {
		return createUriForPagination(mRequestUri, mStart, mLimit);
	}

	public URI getPrev() {
		mStart -= mLimit;
		if(mStart<0) {
			mStart=0;
		}
		return createUriForPagination(mRequestUri, mStart, mLimit);
	}
	
	public String getPages() {
		if(mRequestUri==null) return null;
    	StringBuffer numbers = new StringBuffer();
		float pagesFloat = ((float) mNumPosts/ (float) mLimit);
		int pages = (int) pagesFloat;
		if(pagesFloat>(float) pages) {
			pages++;
		}
    	for (int i = 0; i < pages; i++) {
    		String s = createUriForPagination(mRequestUri, i*(mLimit), mLimit).toString();
    		String startP = "<li><a id=\"page"+(i+1)+"\" href=\""+s+"\">";
    		String endP = "</a></li>";
    		numbers.append(startP + String.valueOf(i+1) + endP);
		}
    	return numbers.toString();
	}

	private URI createUriForPagination(String requestUri, int start, int limit) {
		if(requestUri==null) return null;
		URI uri;
    try {
      uri = new URIBuilder(requestUri)
      	.setParameter("start", String.valueOf(start))
      	.setParameter("limit", String.valueOf(limit))
      	.build();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
		return uri;
	}


}
