package org.denevell.natch.web.jerseymvc.uitests.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumDriverUtils {
	@SuppressWarnings("unused")
	public static WebDriver getDriver() {
		if(false) {
			//PhantomJSDriver pjs = new PhantomJSDriver();
			//return pjs;
			return null;
		} else {
		  //*
      HtmlUnitDriver h = new HtmlUnitDriver(false);
			h.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			return h;
			//*/

		  /*
			FirefoxBinary binary = new FirefoxBinary(new File("/home/user/bin/firefox-32/firefox-bin"));
			FirefoxDriver ffd = new FirefoxDriver(binary, null);
			ffd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			return ffd;
			/*/
		}
	}
}
