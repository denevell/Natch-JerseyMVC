package org.denevell.natch.jerseymvc.services;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.services.ServiceInputs.PostAddInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.PostEditInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadAddFromPostResourceInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadAddInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadEditInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.UserLoginInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.UserPasswordChangeInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.UserRegisterInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadsOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserListOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserLoginOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserRegisterOutput;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseType;
import org.denevell.natch.jerseymvc.utils.Urls;
import org.denevell.natch.jerseymvc.utils.UtilsSession;

public class Services {

	public static String postAdd(String authKey, PostAddInput entity) {
	  return new Serv.Build<Void>(Urls.postAdd())
	  .header("AuthKey", authKey).entity(entity)
	  .statusMap(Urls.postAddErrorMessages())
	  .returnType(Void.class, ResponseType.PUT);
	}

	public static String postDelete(HttpServletRequest req, int postId) {
	  return new Serv.Build<Void>(Urls.postDelete(String.valueOf(postId)))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .statusMap(Urls.postDeleteErrorMessages())
	  .returnType(Void.class, ResponseType.DELETE);
	}

	public static String postEdit(HttpServletRequest req, int postId, PostEditInput input) {
	  return new Serv.Build<Void>(Urls.postEdit(postId))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .statusMap(Urls.postEditErrorMessages())
	  .entity(input)
	  .returnType(Void.class, ResponseType.POST);
	}

	public static String postSingle(HttpServletRequest req, int postId, ResponseObject<PostOutput> returnCallback) {
	  return new Serv.Build<PostOutput>(Urls.postSingle(postId))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .statusMap(Urls.postSingleErrorMessages())
	  .returnCallback(returnCallback)
	  .returnType(PostOutput.class, ResponseType.GET);
	}

	public static String threads(HttpServletRequest req, int start, int limit, ResponseObject<ThreadsOutput> callback) {
	  return new Serv.Build<ThreadsOutput>(Urls.threads(start, limit))
	  .returnCallback(callback)
	  .returnType(ThreadsOutput.class, ResponseType.GET);
	}

	
	public static String threads(HttpServletRequest req, String tag, int start, int limit, ResponseObject<ThreadsOutput> callback) {
	  return new Serv.Build<ThreadsOutput>(Urls.threads(tag, start, limit))
	  .returnCallback(callback)
	  .returnType(ThreadsOutput.class, ResponseType.GET); 
	}

	public static String threadAdd(HttpServletRequest req, ThreadAddInput input) {
	  return new Serv.Build<Void>(Urls.threadAdd())
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .entity(input)
	  .statusMap(Urls.threadAddErrorMessages())
	  .returnType(Void.class, ResponseType.PUT);
	}

	public static String threadDelete(HttpServletRequest req, String postId) {
	  return new Serv.Build<Void>(Urls.postDelete(postId))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .statusMap(Urls.threadDeleteErrorMessages())
	  .returnType(Void.class, ResponseType.DELETE); 
	}

	public static String thread(HttpServletRequest req, String threadId, int start, int limit, ResponseObject<ThreadOutput> callback) {
	  return new Serv.Build<ThreadOutput>(Urls.thread(threadId, start, limit))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .returnCallback(callback)
	  .returnType(ThreadOutput.class, ResponseType.GET); 
	}

  public static String threadFromPost(HttpServletRequest req, ThreadAddFromPostResourceInput input, ResponseObject<HashMap<String, String>> callback) {
	  return new Serv.Build<HashMap<String, String>>(Urls.threadFromPost())
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .entity(input)
	  .returnCallback(callback)
	  .statusMap(Urls.threadFromPostErrorMessages())
	  .returnType(HashMap.class, ResponseType.PUT);
	}

	public static String threadEdit(HttpServletRequest req, int postId, ThreadEditInput input) {
	  return new Serv.Build<Void>(Urls.threadEdit(postId))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .entity(input)
	  .statusMap(Urls.threadEditErrorMessages())
	  .returnType(Void.class, ResponseType.POST);
	}
	
	// ################

	public static String userLogin(HttpServletRequest req, UserLoginInput input, ResponseObject<UserLoginOutput> returnCallback) {
	  return new Serv.Build<UserLoginOutput>(Urls.userLogin())
	  .statusMap(Urls.loginErrorMessages())
	  .entity(input)
	  .returnCallback(returnCallback)
	  .returnType(UserLoginOutput.class, ResponseType.POST); 
	}

	public static String userLogout(HttpServletRequest req, Runnable inAllCasesRunnable) {
	  return new Serv.Build<Void>(Urls.userLogout())
	  .inAllCases(inAllCasesRunnable)
	  .returnType(Void.class, ResponseType.DELETE); 
	}

	public static String userRegister(HttpServletRequest req, UserRegisterInput input, ResponseObject<UserRegisterOutput> returnCallback) {
	  return new Serv.Build<UserRegisterOutput>(Urls.userRegister())
	  .statusMap(Urls.registerErrorMessages())
	  .entity(input)
	  .returnCallback(returnCallback)
	  .returnType(UserRegisterOutput.class, ResponseType.PUT); 
	}

	public static String userPwResetRequestRemove(HttpServletRequest req, String userId) {
	  return new Serv.Build<Void>(Urls.userPwResetRequestRemove(userId))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .statusMap(Urls.threadEditErrorMessages())
	  .returnType(Void.class, ResponseType.DELETE); 
	}

	public static String userPwChange(HttpServletRequest req, String user, UserPasswordChangeInput input) {
	  return new Serv.Build<Void>(Urls.userPwChange(user))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .entity(input)
	  .statusMap(Urls.pwChangeErrorMessages())
	  .returnType(Void.class, ResponseType.POST);
	}

	public static String userAdminToggle(HttpServletRequest req, String username) {
	  return new Serv.Build<Void>(Urls.userAdminToggle(username))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .returnType(Void.class, ResponseType.POST);
	}

	public static String userPwReset(HttpServletRequest req, String email) {
	  return new Serv.Build<Void>(Urls.userPwReset(email))
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .statusMap(Urls.pwResetErrorMessages())
	  .returnType(Void.class, ResponseType.POST);
	}

	public static String users(HttpServletRequest req, ResponseObject<UserListOutput> callback) {
	  return new Serv.Build<UserListOutput>(Urls.users())
	  .header("AuthKey", UtilsSession.getAuthKey(req))
	  .returnCallback(callback)
	  .returnType(UserListOutput.class, ResponseType.GET);
	}
	
}
