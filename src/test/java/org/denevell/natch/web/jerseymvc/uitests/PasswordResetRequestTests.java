package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AdminPO;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ForgottenPasswordPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PasswordResetRequestTests {
	
	private WebDriver driver;
	private AdminPO adminPo;
	private LoginoutPo loginoutPo;
	private ForgottenPasswordPo forgottenPwPo;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		registerPo = new RegisterPo(driver);
		adminPo = new AdminPO(driver);
		loginoutPo = new LoginoutPo(driver);
		forgottenPwPo = new ForgottenPasswordPo(driver);
		loginPo = new LoginoutPo(driver);
    driver.get(URLs.HOMEPAGE);
		registerPo.registerFromHomepage("aaron", "aaron", "");
		loginPo.logout();
		registerPo.registerFromHomepage("aaron2", "aaron2", "a@b.com");
		loginPo.logout();
    driver.get(URLs.HOMEPAGE);
	}
	
	@After
	public void destroy() {
		try{
			loginPo.logout();
		} catch(Exception e) {}
		driver.quit();
	}
	
	
	@Test
	public void shouldRequestResetPassword() throws InterruptedException {
		loginoutPo.login("aaron", "aaron");
		adminPo
			.gotoAdminPage()
			.isRequestPasswordReset("aaron2", false);
        driver.get(URLs.HOMEPAGE);
		loginoutPo
			.logout();
		loginoutPo
			.login("aaron2", "incorrect");
		forgottenPwPo
		  .pressRequestLink()
			.requestReset("incorrect@b.com")
			.hasRequestedSuccessfully(false);
		loginoutPo
			.login("aaron2", "incorrect");
		forgottenPwPo
		  .pressRequestLink()
			.requestReset("a@b.com")
			.hasRequestedSuccessfully(true);
        driver.get(URLs.HOMEPAGE);
		loginoutPo.login("aaron", "aaron");
		adminPo
			.gotoAdminPage()
			.isRequestPasswordReset("aaron2", true)
			.changePassword("aaron2", "newpass")
			.canSeeSuccessFulPasswordChange()
			.isRequestPasswordReset("aaron2", false);
        driver.get(URLs.HOMEPAGE);
		loginoutPo
			.logout()
			.login("aaron2", "newpass")
			.canSeeLogout()
			.shouldntSeeLoginButton();

	}



}
