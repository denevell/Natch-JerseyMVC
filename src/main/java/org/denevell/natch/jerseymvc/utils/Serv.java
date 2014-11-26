package org.denevell.natch.jerseymvc.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyInvocation.Builder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class Serv <ReturnClass> {

	public enum ResponseType {
	  GET, PUT, POST, DELETE
  }

  public static JerseyClient service = JerseyClientBuilder.createClient().register(JacksonFeature.class);

	public static interface ResponseRunnable {
		Response run();
	}
	public static interface ResponseObject<T> {
		void returned(T o);
	}
	
	public static class Build<T> {
	  private String url;
	  private Map<String, String> headers = new HashMap<>();
    private Object entity;
    private Map<Integer, String> errorMap;
    private ResponseObject<T> callback;
    private Runnable inAllCasesCallback;
    public Build(String url) {
	    this.url = url;
	  }
    public Build<T> header(String key, String val) {
	    this.headers.put(key, val);
	    return this;
	  }
    public Build<T> entity(Object o) {
      this.entity = o;
	    return this;
	  }
    public Build<T> inAllCases(Runnable r) {
      this.inAllCasesCallback = r;
	    return this;
	  }
    public Build<T> statusMap(Map<Integer, String> errorMap) {
      this.errorMap = errorMap;
	    return this;
	  }
    public Build<T> returnCallback(ResponseObject<T> callback) {
      this.callback = callback;
	    return this;
	  }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String returnType(Class clazz, final ResponseType type) {
      return Serv.serv(new Serv.ResponseRunnable() { 
        @Override public Response run() {
          Builder s = Serv.service.target(url).request();
          for (String key: headers.keySet()) {
            s.header(key, headers.get(key));
          }
          switch (type) {
          case GET:
            return s.get();
          case PUT:
            return s.put(Entity.json(entity));
          case POST:
            return s.post(Entity.json(entity));
          case DELETE:
            return s.delete();
          default:
            return null;
          }
        }
			}, clazz)
			.addStatusMap(this.errorMap)
			.inAllCases(this.inAllCasesCallback)
			.returnType(callback)
			.go();
	  }
	}

  public static <ReturnClass> Serv<ReturnClass> serv(ResponseRunnable r, Class<ReturnClass> returnClass) {
		try {
			Response resp = r.run();
			ReturnClass rc = null;
			try {
			  if(returnClass!=null) {
			    rc = (ReturnClass) resp.readEntity(returnClass);
			  }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if(resp==null) {
				throw new NullPointerException();
			} else if(resp.getStatus()>=300 || resp.getStatus()<200) {
			  Serv<ReturnClass> s = new Serv<>(resp.getStatus());
				s.returnType = rc; 
				return s;
			} else {
				Serv<ReturnClass> s = new Serv<>(0);
				s.returnType = rc; 
				return s;
			}
		} catch (Exception e) {
			Logger.getLogger(Serv.class).error("Error during fetch", e);
			return new Serv<ReturnClass>(-1);
		}
	}

	private int errorCode;
  private ReturnClass returnType;
  private ResponseObject<ReturnClass> returnTypeCallback;
	private Runnable _exceptionCallback;
	private Runnable inAllCases;
  private Map<Integer, String> errorMessageForCodeMap = new HashMap<Integer, String>();

	public Serv(int i) {
		errorCode = i;
	}

  public Serv<ReturnClass> exception(Runnable runnable) {
    _exceptionCallback = runnable;
    return this;
  }

  public Serv<ReturnClass> inAllCases(Runnable runnable) {
    inAllCases = runnable;
    return this;
  }

  public Serv<ReturnClass> addStatusMap(Map<Integer, String> hm) {
    if(hm!=null) {
      errorMessageForCodeMap.putAll(hm);
    }
    return this;
  }
	public Serv<ReturnClass> returnType(ResponseObject<ReturnClass> ret) {
		returnTypeCallback = ret;
		return this;
	}

	public String go() {
	  String errorMessage = null;
		try {
		  errorMessage = errorMessageForCodeMap.get(errorCode);
		  if(this.errorCode!=0 && errorMessage == null) {
		    errorMessage = errorMessageForCodeMap.get(-1);
		    if (_exceptionCallback!=null) _exceptionCallback.run();
		  }
			if(returnTypeCallback!=null && returnType!=null) {
			  returnTypeCallback.returned(returnType);
			}
			if(this.inAllCases!=null) {
			  this.inAllCases.run();
			}
			return errorMessage;
		} catch (Exception e) {
			_exceptionCallback.run();
			return errorMessage;
		}
	}

  public static void send303(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    resp.addHeader("Location", Urls.getUrlWithQueryString(req));
    resp.sendError(303);
  }

  public static void send303(HttpServletResponse resp, String url) throws Exception {
    resp.addHeader("Location", url);
    resp.sendError(303);
  }

  public static void send303ToRedirectString(HttpServletRequest req, HttpServletResponse resp, String redirect) throws Exception {
    String base = req.getRequestURL().toString();
    String realBase = base.substring(0, base.length()-(req.getServletPath().length()+req.getContextPath().length()));
    if(redirect.startsWith(realBase)) {
      send303(resp, redirect);
    } else {
      send303(resp, realBase);
    }
  }



}