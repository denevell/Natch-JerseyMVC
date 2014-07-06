package org.denevell.natch.jerseymvc.urls;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;


public class MainPageUrlGenerator {

	public URI build() throws URISyntaxException {
		URI uri = new URIBuilder("/index")
			.setParameter("start", String.valueOf(0))
			.setParameter("limit", String.valueOf(10))
			.build();
		return uri;
	}


}
