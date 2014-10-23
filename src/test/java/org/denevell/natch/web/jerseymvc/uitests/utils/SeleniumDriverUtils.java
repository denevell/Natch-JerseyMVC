package org.denevell.natch.web.jerseymvc.uitests.utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDriverUtils {
	@SuppressWarnings("unused")
	public static WebDriver getDriver() {
		if(false) {
			//PhantomJSDriver pjs = new PhantomJSDriver();
			//return pjs;
			return null;
		} else {
			FirefoxBinary binary = new FirefoxBinary(new File("/home/user/bin/firefox-32/firefox-bin"));
      //HtmlUnitDriver fd = new HtmlUnitDriver(false);
			//fd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			FirefoxDriver fd = new FirefoxDriver(binary, null);
			fd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return fd;
		}
	}
}
