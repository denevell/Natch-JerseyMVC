package org.denevell.natch.jerseymvc.services;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.services.ServiceInputs.PostAddInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.PostEditInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.RegisterInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.RegisterOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.UserListOutput;
import org.denevell.natch.jerseymvc.utils.Serv;
import org.denevell.natch.jerseymvc.utils.Serv.ResponseObject;
import org.denevell.natch.jerseymvc.utils.SessionUtils;
import org.denevell.natch.jerseymvc.utils.Urls;

public class Services {

	public static String postAdd(String authKey, PostAddInput entity) {
	  return new Serv.Build(Urls.postAdd())
	  .header("AuthKey", authKey).entity(entity)
	  .statusMap(Urls.postAddErrorMessages())
	  .returnType(Void.class);
	}

	public static String postDelete(HttpServletRequest req, int postId) {
	  return new Serv.Build(Urls.postDelete(String.valueOf(postId)))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .statusMap(Urls.postDeleteErrorMessages())
	  .returnType(Void.class);
	}

	public static String postEdit(HttpServletRequest req, int postId, PostEditInput input) {
	  return new Serv.Build(Urls.postEdit(postId))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .statusMap(Urls.postEditErrorMessages())
	  .entity(input)
	  .returnType(Void.class);
	}

	public static String postSingle(HttpServletRequest req, int postId, ResponseObject returnCallback) {
	  return new Serv.Build(Urls.postSingle(postId))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .statusMap(Urls.postSingleErrorMessages())
	  .returnCallback(returnCallback)
	  .returnType(PostOutput.class);
	}


	public static String threadDelete(HttpServletRequest req, String postId) {
	  return new Serv.Build(Urls.postDelete(postId))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .statusMap(Urls.threadDeleteErrorMessages())
	  .returnType(Void.class); // DELETE
	}

	public static String thread(HttpServletRequest req, String threadId, int start, int limit, ResponseObject callback) {
	  return new Serv.Build(Urls.thread(threadId, start, limit))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .returnCallback(callback)
	  .returnType(ThreadOutput.class); // GET
	}

	public static String userRegister(HttpServletRequest req, RegisterInput input, ResponseObject returnCallback) {
	  return new Serv.Build(Urls.userRegister())
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .statusMap(Urls.registerErrorMessages())
	  .entity(input)
	  .returnCallback(returnCallback)
	  .returnType(RegisterOutput.class);
	}

	public static String userAdminToggle(HttpServletRequest req, String username) {
	  return new Serv.Build(Urls.userAdminToggle(username))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .returnType(Void.class);
	}

	public static String userPwReset(HttpServletRequest req, String email) {
	  return new Serv.Build(Urls.userPwReset(email))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .statusMap(Urls.pwResetErrorMessages())
	  .returnType(Void.class);
	}

	public static String users(HttpServletRequest req, ResponseObject callback) {
	  return new Serv.Build(Urls.users())
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .returnCallback(callback)
	  .returnType(UserListOutput.class); //GET
	}
	
}
