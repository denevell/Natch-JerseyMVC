package org.denevell.natch.web.jerseymvc.uitests.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDriverUtils {
	@SuppressWarnings("unused")
	public static WebDriver getDriver() {
		if(false) {
			//PhantomJSDriver pjs = new PhantomJSDriver();
			//return pjs;
			return null;
		} else {
			//HtmlUnitDriver fd = new HtmlUnitDriver(false);
			//fd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			FirefoxDriver fd = new FirefoxDriver();
			fd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			return fd;
		}
	}
}
