package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class LogoutUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private RegisterPo registerPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		loginPo = new LoginoutPo(driver);
		registerPo = new RegisterPo(driver);
		registerPo.registerFromHomepage("aaron", "aaron", "");
		loginPo.logout();
	}
	
	@Test
	public void shouldLogoutCorrectly() throws InterruptedException {
		loginPo
			.loginFromHomepage("aaron", "aaron")
			.canSeeLogout()
			.logout()
			.cantSeeLogoutButton();
	}
	
}
