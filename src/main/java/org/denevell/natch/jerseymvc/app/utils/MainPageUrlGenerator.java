package org.denevell.natch.jerseymvc.app.utils;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.URIBuilder;


public class MainPageUrlGenerator {

	public URI build(HttpServletRequest req) throws URISyntaxException {
		URI uri = new URIBuilder(req.getContextPath()+"/")
			.setParameter("start", String.valueOf(0))
			.setParameter("limit", String.valueOf(10))
			.build();
		return uri;
	}


}
