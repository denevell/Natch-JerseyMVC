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
	
	public AdminPO changePassword(String username, String password) {
	    WebElement aaron2ChangePassword = driver.findElement(By.id(username+"_changepw"));
	    WebElement pass = aaron2ChangePassword.findElement(By.name("changepw_password"));
	    pass.sendKeys(password);
	    WebElement changePasswordButton = aaron2ChangePassword.findElement(By.name("changepw_submit"));
	    changePasswordButton.click();
	    return this;
	}

	public AdminPO isRequestPasswordReset(String username, boolean t) {
	    WebElement resetPw = driver.findElement(By.id(username+"_passwordResetRequest"));
	    if(t) {
	    	org.junit.Assert.assertTrue("Password reset is set", resetPw.getText().equals("true"));
	    } else {
	    	org.junit.Assert.assertFalse("Password reset is set", resetPw.getText().equals("true"));
	    }
	    return this;
	}

	public String getRecoveryEmail(String username) {
	    WebElement e = driver.findElement(By.id(username+"_recoveryEmail"));
	    return e.getText();
	}

	public void checkRecoveryEmailExists(String username, String recoveryEmail) {
		org.junit.Assert.assertEquals("Has correct recovery password set", recoveryEmail, getRecoveryEmail(username));
	}

}
