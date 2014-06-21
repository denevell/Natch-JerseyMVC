package org.denevell.natch.web.jerseymvc.onethread.modules;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

public class OneThreadPaginationModule {

	public String createUriForNextPagination(String requestUri, int start, int limit, int numPosts) throws URISyntaxException {
		if(!(start+limit > numPosts)) {
			start += limit;
		}
		return createUriForPagination(requestUri, start, limit).toString();
	}

	public String createUriForPrevPagination(String requestUri, int start, int limit) throws URISyntaxException {
		start -= limit;
		if(start<0) {
			start=0;
		}
		return createUriForPagination(requestUri, start, limit).toString();
	}

	private URI createUriForPagination(String requestUri, int start, int limit) throws URISyntaxException {
		URI uri = new URIBuilder(requestUri)
			.setParameter("start", String.valueOf(start))
			.setParameter("limit", String.valueOf(limit))
			.build();
		return uri;
	}
	
	public String createPagintionNumbers(String requestUri, int limit, int numPosts) throws URISyntaxException {
    	
    	StringBuffer numbers = new StringBuffer();
		float pagesFloat = (numPosts/ limit);
		int pages = (int) pagesFloat;
		if(pagesFloat!=0) {
			pages++;
		}
    	for (int i = 0; i < pages; i++) {
    		String s = createUriForPagination(requestUri, i*(limit), limit).toString();
    		String startP = "<a id=\"page"+(i+1)+"\" href=\""+s+"\">";
    		String endP = "</a> | ";
    		numbers.append(startP + String.valueOf(i+1) + endP);
		}
    	return numbers.toString();
	}


}
