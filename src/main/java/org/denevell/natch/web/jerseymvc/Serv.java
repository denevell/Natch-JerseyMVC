package org.denevell.natch.web.jerseymvc;

import javax.ws.rs.WebApplicationException;

import org.apache.log4j.Logger;

public class Serv {
	public static Serv serv(Runnable r) {
		try {
			r.run();
			return new Serv();
		} catch (WebApplicationException e) {
			Logger.getLogger(Serv.class).error("Error during fetch", e);
			return new Serv(e.getResponse().getStatus());
		} catch (Exception e) {
			Logger.getLogger(Serv.class).error("Error during fetch", e);
			return new Serv(-1);
		}
	}
	private int errorCode;
	private Runnable _500;
	private Runnable _403;
	private Runnable _400;
	private Runnable _exception;
	private Runnable _401;
	public Serv(int i) {
		errorCode = i;
	}
	public Serv() {
	}
	public Serv _500(Runnable r) {
		_500 = r;
		return this;
	}
	public Serv _403(Runnable r) {
		_403 = r;
		return this;
	}
	public Serv _401(Runnable r) {
		_401 = r;
		return this;
	}
	public Serv _400(Runnable r) {
		_400 = r;
		return this;
	}
	public Serv _exception(Runnable r) {
		_exception = r;
		return this;
	}
	public void go() {
		try {
			switch (errorCode) {
			case 0:
				break;
			case 500:
				_500.run();
				break;
			case 400:
				_400.run();
				break;
			case 401:
				_401.run();
				break;
			case 403:
				_403.run();
				break;
			default:
				_exception.run();
				break;
			}
		} catch (Exception e) {
			_exception.run();
		}
	}

}