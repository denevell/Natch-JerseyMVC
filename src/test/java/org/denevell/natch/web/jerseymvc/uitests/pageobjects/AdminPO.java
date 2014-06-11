package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.denevell.natch.web.jerseymvc.uitests.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPO {
	
	private WebDriver driver;

	public AdminPO(WebDriver driver) {
		this.driver = driver;
	}

	public AdminPO gotoAdminPage() {
		driver.get(URLs.HOMEPAGE);
	    WebElement adminLink = driver.findElement(By.id("admin"));
	    adminLink.click();
	    return this;
	}
	
	public void changePassword(String username, String password) {
	    WebElement aaron2ChangePassword = driver.findElement(By.id(username+"_changePassword"));
	    aaron2ChangePassword.sendKeys(password);
	    WebElement changePasswordButton = driver.findElement(By.id(username+"_changePasswordButton"));
	    changePasswordButton.click();
	}

	public boolean isRequestPasswordReset(String username) {
	    WebElement resetPw = driver.findElement(By.id(username+"_requestingReset"));
	    return resetPw.getText().equals("true");
	}

	public String getRecoveryEmail(String username) {
	    WebElement e = driver.findElement(By.id(username+"_recoveryEmail"));
	    return e.getText();
	}

	public void checkRecoveryEmailExists(String username, String recoveryEmail) {
		org.junit.Assert.assertEquals("Has correct recovery password set", recoveryEmail, getRecoveryEmail(username));
	}

}
