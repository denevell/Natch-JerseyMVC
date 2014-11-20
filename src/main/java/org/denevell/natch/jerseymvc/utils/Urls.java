package org.denevell.natch.jerseymvc.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Urls {

  public static String threads(String tag, int start, int limit) {
    return ListenerManifestVars.getValue("rest_service") + "rest/thread/bytag/"+tag+"/"+String.valueOf(start)+"/"+String.valueOf(limit);
  }

  public static String threads(int start, int limit) {
    return ListenerManifestVars.getValue("rest_service") + "rest/thread/"+String.valueOf(start)+"/"+String.valueOf(limit);
  }

  public static String thread(String threadId, int start, int limit) {
    return ListenerManifestVars.getValue("rest_service") + "rest/"+threadId+"/"+start+"/"+limit;
  }

  public static String threadFromPost() {
    return ListenerManifestVars.getValue("rest_service") + "rest/thread/frompost";
  }

  public static String threadEdit(int id) {
    return ListenerManifestVars.getValue("rest_service") + "rest/thread/edit/"+String.valueOf(id);
  }

  public static String postAdd() {
    return ListenerManifestVars.getValue("rest_service") + "rest/post/add";
  }

  public static String postDelete(String postId) {
    return ListenerManifestVars.getValue("rest_service") + "rest/post/del/"+postId;
  }

  public static String postEdit(int postId) {
    return ListenerManifestVars.getValue("rest_service") + "rest/post/editpost/"+postId;
  }

  public static String postSingle(int postId) {
    return ListenerManifestVars.getValue("rest_service") + "rest/post/single/"+postId;
  }

  public static String userRegister() {
    return ListenerManifestVars.getValue("user_service") + "rest/user/";
  }

  public static String userAdminToggle(String username) {
    return ListenerManifestVars.getValue("user_service") + "rest/user/admin/toggle/"+username;
  }

  public static String userPwReset(String email) {
    return ListenerManifestVars.getValue("user_service") + "rest/user/password_reset/"+email;
  }

  public static String users() {
    return ListenerManifestVars.getValue("user_service") + "rest/user/list";
  }

  public static Map<Integer, String >postAddErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(403, "You're not logged in");
		hm.put(401, "You're not logged in");
		hm.put(400, Strings.getPostFieldsCannotBeBlank());
		hm.put(-1, "Whoops.... erm...");
		return hm;
  }

  public static Map<Integer, String >loginErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(403, Strings.getLogonError());
		hm.put(400, Strings.getBlankUsernameOrPassword());
		hm.put(-1, "Whoops.... erm...");
		return hm;
  }

  public static Map<Integer, String >threadFromPostErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		return hm;
  }

  public static Map<Integer, String >threadEditErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		hm.put(403, "You're not logged in");
		hm.put(401, "You're not logged in");
		hm.put(400, "Please check the fields and try again");
		return hm;
  }

  public static Map<Integer, String >threadAddErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		hm.put(403, "You're not logged in");
		hm.put(401, "You're not logged in");
		hm.put(400, Strings.getPostFieldsCannotBeBlank());
		return hm;
  }

  public static Map<Integer, String >threadDeleteErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		hm.put(403, "You're not logged in");
		hm.put(401, "You're not logged in");
		return hm;
  }

  public static Map<Integer, String >registerErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		hm.put(401, Strings.getBlankUsernameOrPassword());
		hm.put(403, "Username or password incorrect");
		hm.put(400, Strings.getPostFieldsCannotBeBlank());
		return hm;
  }

  public static Map<Integer, String >pwChangeErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		return hm;
  }

  public static Map<Integer, String >pwResetErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		return hm;
  }

  public static Map<Integer, String >postDeleteErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		hm.put(403, "You're not logged in");
		hm.put(500, "Unable to delete");
		hm.put(401, "You're not logged in");
		return hm;
  }

  public static Map<Integer, String >postEditErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		hm.put(403, "You're not logged in");
		hm.put(500, "Unable to delete");
		hm.put(401, "You're not logged in");
		hm.put(400, "Error eiditing");
		return hm;
  }

  public static Map<Integer, String >postSingleErrorMessages() {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(-1, "Whoops.... erm...");
		return hm;
  }

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
