package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class LogonUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
        driver.get(URLs.HOMEPAGE);
        loginPo = new LoginoutPo(driver);
        new RegisterPo(driver).registerFromHomepage("aaron", "aaron", "");
		loginPo.logout();
	}
	
	@After
	public void destroy() {
		try {
			loginPo.logout();
		} catch(Exception e) {}
        driver.quit();
	}
	
	@Test
	public void shouldLogonCorrectly() throws InterruptedException {
        loginPo
        	.shouldBeOnHomepage()
        	.shouldSeeLoginButton()
        	.loginFromHomepage("aaron", "aaron")
        	.shouldBeOnHomepage()
        	.shouldntSeeBlankUsernameOrPassError()
        	.shouldntSeeLoginButton();
	}

	@Test
	public void shouldntLogonCorrectly() {
        loginPo
        	.loginFromHomepage("sup", "notreeeel")
        	.shouldSeeLoginIncorrectError();
	}

	
	@Test
	public void shouldntLogonWithBlankUsername() {
        loginPo
        	.loginFromHomepage(" ", "xxx")
        	.shouldSeeLoginIncorrectError();
	}	
	
	@Test
	public void shouldntLogonWithBlankPassword() {
        loginPo
        	.loginFromHomepage("xxx", " ")
        	.shouldSeeLoginIncorrectError();
	}		
	
	@Test
	public void shouldntLogonWithBlanks() {
        loginPo
        	.loginFromHomepage(" ", " ")
        	.shouldSeeLoginIncorrectError();
	}			

}
