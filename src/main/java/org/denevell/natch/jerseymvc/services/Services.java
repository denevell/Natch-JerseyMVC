package org.denevell.natch.jerseymvc.services;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.denevell.natch.jerseymvc.services.ServiceInputs.AddThreadFromPostResourceInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.PostAddInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.PostEditInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.RegisterInput;
import org.denevell.natch.jerseymvc.services.ServiceInputs.ThreadEditInput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.PostOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.RegisterOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadOutput;
import org.denevell.natch.jerseymvc.services.ServiceOutputs.ThreadsOutput;
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

	public static String threads(HttpServletRequest req, int start, int limit, ResponseObject callback) {
	  return new Serv.Build(Urls.threads(start, limit))
	  .returnCallback(callback)
	  .returnType(ThreadsOutput.class); // GET
	}

	public static String threads(HttpServletRequest req, String tag, int start, int limit, ResponseObject callback) {
	  return new Serv.Build(Urls.threads(tag, start, limit))
	  .returnCallback(callback)
	  .returnType(ThreadsOutput.class); // GET
	}

  /*
		errorMessage = Serv.serv(new ResponseRunnable() { @Override public Response run() {
			List<String> tagStrings = Arrays.asList(tags.split("[,\\s]+"));
			for (String string : tagStrings) {
			  entity.tags.add(new StringWrapper(string));
      }
			Object authKey = serv.getSession(true).getAttribute("authkey");
			if(authKey==null || authKey.toString().trim().length()==0) {
			  return Response.status(401).build();
			}
      return Serv.service 
				.target(ListenerManifestVars.getValue("rest_service"))
				.path("rest").path("thread").request()
				.header("AuthKey", (String) authKey)
				.put(Entity.json(entity));
			}}, Void.class)
			.addStatusMap(Urls.threadAddErrorMessages()).go();
			*/

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

	public static String threadFromPost(HttpServletRequest req, AddThreadFromPostResourceInput input, ResponseObject callback) {
	  return new Serv.Build(Urls.threadFromPost())
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .entity(input)
	  .returnCallback(callback)
	  .statusMap(Urls.threadFromPostErrorMessages())
	  .returnType(HashMap.class); // PUT
	}

	public static String threadEdit(HttpServletRequest req, int postId, ThreadEditInput input) {
	  return new Serv.Build(Urls.threadEdit(postId))
	  .header("AuthKey", SessionUtils.getAuthKey(req))
	  .entity(input)
	  .statusMap(Urls.threadEditErrorMessages())
	  .returnType(Void.class); // POST 
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
