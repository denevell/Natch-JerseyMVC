package org.denevell.natch.jerseymvc.utils;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyInvocation.Builder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class Serv {

	public static JerseyClient service = JerseyClientBuilder.createClient().register(JacksonFeature.class);

	public static interface ResponseRunnable {
		Response run();
	}
	public static interface ResponseObject {
		void returned(Object o);
	}
	
	public static class Build {
	  private String url;
	  private Map<String, String> headers = new HashMap<>();
    private Object entity;
    private Map<Integer, String> errorMap;
    private ResponseObject callback;
    public Build(String url) {
	    this.url = url;
	  }
    public Build header(String key, String val) {
	    this.headers.put(key, val);
	    return this;
	  }
    public Build entity(Object o) {
      this.entity = o;
	    return this;
	  }
    public Build statusMap(Map<Integer, String> errorMap) {
      this.errorMap = errorMap;
	    return this;
	  }
    public Build returnCallback(ResponseObject callback) {
      this.callback = callback;
	    return this;
	  }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String returnType(Class clazz) {
      return Serv.serv(new Serv.ResponseRunnable() { 
        @Override public Response run() {
          Builder s = Serv.service.target(url).request();
          for (String key: headers.keySet()) {
            s.header(key, headers.get(key));
          }
          return s.put(Entity.json(entity));
        }
			}, clazz)
			.addStatusMap(this.errorMap)
			.returnType(callback)
			.go();
	  }
	}

  public static <ReturnClass> Serv serv(ResponseRunnable r, Class<ReturnClass> returnClass) {
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
			  Serv s = new Serv(resp.getStatus());
				s.returnType = rc; 
				return s;
			} else {
				Serv s = new Serv(0);
				s.returnType = rc; 
				return s;
			}
		} catch (Exception e) {
			Logger.getLogger(Serv.class).error("Error during fetch", e);
			return new Serv(-1);
		}
	}

	private int errorCode;
  private Object returnType;
  private ResponseObject returnTypeCallback;
	private Runnable _exceptionCallback;
  private Map<Integer, String> errorMessageForCodeMap = new HashMap<Integer, String>();

	public Serv(int i) {
		errorCode = i;
	}

  public Serv exception(Runnable runnable) {
    _exceptionCallback = runnable;
    return this;
  }
  public Serv addStatusMap(Map<Integer, String> hm) {
    errorMessageForCodeMap.putAll(hm);
    return this;
  }
	public Serv returnType(ResponseObject ret) {
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
			if(returnType!=null && returnTypeCallback!=null) {
			  returnTypeCallback.returned(returnType);
			}
			return errorMessage;
		} catch (Exception e) {
			_exceptionCallback.run();
			return errorMessage;
		}
	}



}