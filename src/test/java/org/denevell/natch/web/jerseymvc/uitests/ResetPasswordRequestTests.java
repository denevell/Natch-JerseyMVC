package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AdminPO;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ForgottenPasswordPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ResetPasswordRequestTests {
	
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
		registerPo.register("aaron", "aaron", "");
		loginPo.logout();
		registerPo.register("aaron2", "aaron2", "a@b.com");
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
		forgottenPwPo
			.requestReset("aaron2", "incorrect", "incorrect@b.com")
			.hasRequestedSuccessfully(false)
			.requestReset("aaron2", "incorrect", "a@b.com")
			.hasRequestedSuccessfully(true);
        driver.get(URLs.HOMEPAGE);
		loginoutPo.login("aaron", "aaron");
		adminPo
			.gotoAdminPage()
			.isRequestPasswordReset("aaron2", true)
			.changePassword("aaron2", "newpass")
			.isRequestPasswordReset("aaron2", false);
	}



}
