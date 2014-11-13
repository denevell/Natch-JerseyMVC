package org.denevell.natch.jerseymvc.utils;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

public class Urls {
  
  public static String getUrlWithQueryString(HttpServletRequest request) throws URISyntaxException {
    String qs = "";
    if(request.getQueryString()!=null && request.getQueryString().trim().length()!=0) {
      qs = "?" + request.getQueryString();
    }
    String url = request.getRequestURL() + qs; 
    return new URI(url).toString();
  }

  public static String getRelativeUrlWithQueryString(HttpServletRequest request) throws URISyntaxException {
    String qs = "";
    if(request.getQueryString()!=null && request.getQueryString().trim().length()!=0) {
      qs = "?" + request.getQueryString();
    }
    String url = request.getServletPath()+request.getPathInfo() + qs; 
    return new URI(url).toString();
  }

}
