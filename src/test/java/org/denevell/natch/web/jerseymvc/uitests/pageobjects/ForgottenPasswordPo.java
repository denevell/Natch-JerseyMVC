package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgottenPasswordPo {

	private WebDriver driver;
	private LoginoutPo loginPo;

	public ForgottenPasswordPo(WebDriver driver) {
		this.driver = driver;
		loginPo = new LoginoutPo(driver);
	}

	public ForgottenPasswordPo requestReset(String username, String pass, String email) {
		loginPo.loginFromHomepage(username, pass);
	    WebElement input = driver.findElement(By.id("resetpw_input"));
	    input.sendKeys(email);
	    WebElement button = driver.findElement(By.id("resetpw_form_submit"));
	    button.click();
	    return this;
	}

	public ForgottenPasswordPo hasRequestedSuccessfully(boolean t) {
		if(t) {
			// org.junit.Assert.assertTrue(driver.getPageSource().toLowerCase().contains("password reset sent"));
			// TODO: Find a way to sent template date on redirects for this
			org.junit.Assert.assertFalse(driver.getPageSource().toLowerCase().contains("error reseting password"));
		} else if(!t){
			org.junit.Assert.assertTrue(driver.getPageSource().toLowerCase().contains("error reseting password"));
		}
		return this;
	}

}
