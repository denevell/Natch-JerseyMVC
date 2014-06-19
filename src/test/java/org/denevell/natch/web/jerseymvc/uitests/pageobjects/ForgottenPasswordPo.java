package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgottenPasswordPo {

	private WebDriver driver;

	public ForgottenPasswordPo(WebDriver driver) {
		this.driver = driver;
	}

	public ForgottenPasswordPo requestReset(String username) {
	    WebElement input = driver.findElement(By.name("recoveryEmail"));
	    input.sendKeys(username);
	    WebElement button = driver.findElement(By.id("reset_password_input"));
	    button.click();
	    return this;
	}

	public ForgottenPasswordPo hasRequestedSuccessfully(boolean t) {
		if(t) {
			org.junit.Assert.assertTrue(
				driver.getPageSource().toLowerCase().contains("password reset sent"));
		} else if(t){
			org.junit.Assert.assertFalse(
				driver.getPageSource().toLowerCase().contains("password reset sent"));
		}
		return this;
	}

}
