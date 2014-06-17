package org.denevell.natch.web.jerseymvc.uitests;

public class URLs {

	private static final String baseurl = "http://localhost:8081/index?start=0&limit=10";
	
	public static final String HOMEPAGE = baseurl;
	public static final String ADDTHREAD = baseurl+"AddThread";
	public static final String THREADPAGE = baseurl+"Posts?thread=";
	public static final String REPLYTO= baseurl+"ReplyTo?";
	public static final String THREADPAGE_WITHOUT_QUERY_STRING = baseurl+"Posts";
	public static final String DELETEPOST = baseurl+"ConfirmSinglePostDelete?";
	public static final String EDITPOST = baseurl+"EditPost";
	public static final String EDITTHREAD = baseurl+"EditThread";

	public static final String REGISTER = baseurl+"Register";


}
