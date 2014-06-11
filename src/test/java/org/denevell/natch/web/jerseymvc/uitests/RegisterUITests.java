package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AdminPO;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class RegisterUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private AdminPO adminPo;
	private LoginoutPo loginPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
        loginPo = new LoginoutPo(driver);
		registerPo = new RegisterPo(driver);
		adminPo = new AdminPO(driver);
        registerPo.registerFromHomepage("aaron", "aaron", "");
        loginPo.logout();
        registerPo.registerFromHomepage("aaron2", "aaron2", "");
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
	public void shouldRegister() throws InterruptedException {
		registerPo
			.registerFromHomepage("aaron3", "aaron3", "")
			.shouldBeOnHomepage()
			.shouldntSeeRegisterLink();
	}

	@Test
	public void shouldRegisterWithOptionalEmailRecovery() throws InterruptedException {
		registerPo
			.register("aaron3", "aaron3", "a@b.com");
		loginPo
			.logout()
			.loginFromHomepage("aaron", "aaron");
		adminPo
			.gotoAdminPage()
			.checkRecoveryEmailExists("aaron3", "a@b.com");
	}
	
	@Test
	public void shouldntRegisterWithDupUsernames() throws InterruptedException {
		registerPo
			.register("aaron", "aaron", "")
			.shouldSeeUsernameAlreadyExists()
			.shouldSeeRegisterLink();
	}		
	
	@Test
	public void shouldSeeErrorOnExistingRegisterAndThenRegSuccess() throws InterruptedException {
		registerPo
			.register("aaron", "", "")
			.shouldSeeBlanksError()
			.register("aaronnew", "aaronnew", "")
			.shouldntSeeRegisterLink();
	}			
	
	@Test
	public void shouldSeeErrorOnEnterOfBlankUsername() throws InterruptedException {
		registerPo
			.register(" ", "aaron", "")
			.shouldSeeBlanksError()
			.shouldSeeRegisterLink();
	}
	
}
