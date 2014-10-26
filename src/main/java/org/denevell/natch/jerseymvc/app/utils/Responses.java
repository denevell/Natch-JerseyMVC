package org.denevell.natch.jerseymvc.app.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Responses {

  public static void send303(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    resp.addHeader("Location", Urls.getUrlWithQueryString(req));
    resp.sendError(303);
  }

}
