package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.denevell.natch.web.jerseymvc.uitests.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPo {
	
	private WebDriver driver;

	public RegisterPo(WebDriver driver) {
		this.driver = driver;
	}

	public RegisterPo registerFromHomepage(String username, String password, String recoveryEmail) {
        driver.get(URLs.HOMEPAGE);
		//driver.findElement(By.id("reg_link")).click();
        register(username, password, "");
        return this;
	}
	
	public RegisterPo register(String username, String password, String recoveryEmail) {
		WebElement username_input = driver.findElement(By.id("register_username_input"));
        WebElement password_input = driver.findElement(By.id("register_password_input"));
        WebElement recovery_input = driver.findElement(By.id("register_recovery_input"));
        WebElement submit_input = driver.findElement(By.id("register_form_submit"));
		username_input.clear();
        username_input.sendKeys(username);
        password_input.clear();
        password_input.sendKeys(password);
        recovery_input.clear();
        recovery_input.sendKeys(recoveryEmail);
        submit_input.click();
        return this;
	}

	public RegisterPo shouldBeOnHomepage() {
        String currentUrl = driver.getCurrentUrl();
        org.junit.Assert.assertTrue(currentUrl.equals(URLs.HOMEPAGE));
		return this;
	}

	public RegisterPo shouldSeeRegisterLink() {
		org.junit.Assert.assertTrue("Shouldn't see register link", driver.getPageSource().toLowerCase().contains("register_form_submit"));
		return this;
	}

	public RegisterPo shouldntSeeRegisterLink() {
		org.junit.Assert.assertFalse("Shouldn't see register link", driver.getPageSource().toLowerCase().contains("register_form_submit"));
		return this;
	}

	public RegisterPo shouldSeeUsernameAlreadyExists() {
        String pageSource = driver.getPageSource();
		org.junit.Assert.assertTrue("Shouldnt see reg error", pageSource.contains("Username already exists"));
		return this;
	}

	public RegisterPo shouldSeeBlanksError() {
        String pageSource = driver.getPageSource();
		org.junit.Assert.assertTrue("Should see reg error", pageSource.contains("cannot be blank"));
		return this;
	}

}
