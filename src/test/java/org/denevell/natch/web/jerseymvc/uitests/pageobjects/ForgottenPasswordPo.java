package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgottenPasswordPo {

	private WebDriver driver;

	public ForgottenPasswordPo(WebDriver driver) {
		this.driver = driver;
	}

	public ForgottenPasswordPo requestReset(String email) {
	    WebElement input = driver.findElement(By.name("resetpw_email"));
	    input.sendKeys(email);
	    WebElement button = driver.findElement(By.id("resetpw_form_submit"));
	    button.click();
	    return this;
	}

	public ForgottenPasswordPo hasRequestedSuccessfully(boolean t) {
		if(t) {
			org.junit.Assert.assertTrue(driver.getPageSource().toLowerCase().contains("password reset request"));
		} else if(!t){
			org.junit.Assert.assertTrue(driver.getPageSource().toLowerCase().contains("problem requesting"));
		}
		return this;
	}

  public ForgottenPasswordPo pressRequestLink() {
	  driver.findElement(By.id("pwrequest")).click();
    return this;
  }

}
