package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.web.jerseymvc.uitests.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginoutPo {
	
	private WebDriver driver;

	public LoginoutPo(WebDriver driver) {
		this.driver = driver;
	}
	
	public LoginoutPo logout() {
		WebElement findElement = driver.findElement(By.id("logout_form_submit"));
		findElement.click();
		return this;
	}
	
	public LoginoutPo login(String user, String pass) {
		WebElement username_input = driver.findElement(By.id("login_username_input"));
        WebElement password_input = driver.findElement(By.id("login_password_input"));
        WebElement submit_input = driver.findElement(By.id("login_form_submit"));
		username_input.clear();
        username_input.sendKeys(user);
        password_input.clear();
        password_input.sendKeys(pass);
        submit_input.click();
        return this;
	}

	public LoginoutPo loginFromHomepage(String user, String pass) {
        driver.get(URLs.HOMEPAGE);
		login(user, pass);
		return this;
	}

	public LoginoutPo shouldSeeLoginButton() {
        org.junit.Assert.assertTrue(driver.getPageSource().contains("login_form_submit"));
		return this;
	}

	public LoginoutPo shouldntSeeLoginButton() {
        org.junit.Assert.assertFalse(driver.getPageSource().contains("login_form_submit"));
		return this;
	}

	public LoginoutPo shouldSeeBlankUsernameOrPassError() {
        boolean source = driver.getPageSource().contains(Strings.getBlankUsernameOrPassword());
        org.junit.Assert.assertTrue(source);
		return this;
	}

	public LoginoutPo shouldntSeeBlankUsernameOrPassError() {
        boolean source = driver.getPageSource().contains(Strings.getBlankUsernameOrPassword());
        org.junit.Assert.assertFalse(source);
		return this;
	}

	public LoginoutPo shouldSeeLoginIncorrectError() {
        boolean source = driver.getPageSource().contains(Strings.getLogonError());
        org.junit.Assert.assertTrue("Should see user/pass login error", source);
		return this;
	}

	public LoginoutPo shouldBeOnHomepage() {
        org.junit.Assert.assertTrue(driver.getCurrentUrl().startsWith(URLs.HOMEPAGE));
		return this;
	}

	public LoginoutPo canSeeLogout() {
		try {
			driver.findElement(By.id("logout_form_submit"));
		} catch (NoSuchElementException e) {
			org.junit.Assert.assertTrue("Wanted to see logout", false);
		}
		return this;
	}

	public LoginoutPo pressForgottenPasswordLink() {
		return this;
	}

	public LoginoutPo cantSeeLogoutButton() {
		try {
			driver.findElement(By.id("logout_form_submit"));
		} catch (NoSuchElementException e) {
			// Goooood.
			return this;
		}
		org.junit.Assert.assertTrue("Wanted logout to be invisible", false);
		return this;
	}

}
