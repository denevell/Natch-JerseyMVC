package org.denevell.natch.jerseymvc.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class UtilsPagination {
	
	public static class PageNumInfo {
    public String pageNum;
	  public String url;
	}

	public static List<PageNumInfo> getPagination(HttpServletRequest request, int limit, int numPosts) throws URISyntaxException {
		int numPages = (int) Math.ceil(((float) numPosts/ (float) limit));
		String requestUri = Urls.getRelativeUrlWithQueryString(request);

    List<PageNumInfo> numbers = new ArrayList<PageNumInfo>();
    for (int i = 0; i < numPages; i++) {
      PageNumInfo pni = new PageNumInfo();
      pni.pageNum = String.valueOf(i+1);
      pni.url = Urls.addQueryStringsToUrl(requestUri, "start", String.valueOf(i*limit), "limit", String.valueOf(limit)).toString();
    	numbers.add(pni);
		}
    return numbers;
	}

	public static URI getNext(HttpServletRequest req, int start, int limit, int numPosts) throws Exception {
		if(!(start+limit > numPosts)) {
			start += limit;
		}
		return Urls.addQueryStringsToUrl(req, "start", String.valueOf(start), "limit", String.valueOf(limit));
	}

	public static URI getPrev(HttpServletRequest req, int start, int limit) throws Exception {
		start -= limit;
		if(start<0) {
			start=0;
		}
		return Urls.addQueryStringsToUrl(req, "start", String.valueOf(start), "limit", String.valueOf(limit));
	}

}
