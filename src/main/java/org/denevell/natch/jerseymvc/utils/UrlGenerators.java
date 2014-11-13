package org.denevell.natch.jerseymvc.utils;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.URIBuilder;


public class UrlGenerators {

	public static URI mainPage(HttpServletRequest req) throws URISyntaxException {
		URI uri = new URIBuilder("/")
			.setParameter("start", String.valueOf(0))
			.setParameter("limit", String.valueOf(10))
			.build();
		return uri;
	}

	public static String createThreadUrl(HttpServletRequest req, String threadId) {
		return "/thread/"+threadId;
	}

	public static String createThreadUrl(HttpServletRequest req, String threadId, int start, int limit) {
		return "/thread/"+threadId+"?start="+start+"&limit="+limit;
	}

}
