package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AdminPO;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class RegisterUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private AdminPO adminPo;
	private LoginoutPo loginPo;
  private ThreadAddPo addthreadPo;

  @Before
  public void setup() throws Exception {
    TestUtils.deleteTestDb();
    driver = SeleniumDriverUtils.getDriver();
    loginPo = new LoginoutPo(driver);
    registerPo = new RegisterPo(driver);
    adminPo = new AdminPO(driver);
		addthreadPo = new ThreadAddPo(driver);
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
			.shouldntSeeRegisterFormSubmit()
		  .shouldSeeRegisterLinkText(false);
	}

	@Test
	public void shouldRegisterWithOptionalEmailRecovery() throws InterruptedException {
		registerPo
			.registerFromHomepage("aaron3", "aaron3", "a@b.com");
		adminPo
			.gotoAdminPage()
			.checkRecoveryEmailExists("aaron3", "a@b.com");
	}
	
	@Test
	public void shouldntRegisterWithDupUsernames() throws InterruptedException {
		registerPo
			.registerFromHomepage("aaron3", "aaron3", "");
		loginPo.logout();
	  registerPo.registerFromHomepage("aaron3", "aaron3", "")
			.shouldSeeUsernameAlreadyExists()
			.shouldSeeRegisterFormSubmit();
	}		
	
	@Test
	public void shouldSeeErrorOnExistingRegisterAndThenRegSuccess() throws InterruptedException {
		registerPo
			.registerFromHomepage("aaron3", "aaron3", "");
		loginPo.logout();
	  registerPo.registerFromHomepage("aaron3", "aaron3", "")
			.shouldSeeUsernameAlreadyExists()
			.register("aaronnew", "aaronnew", "")
			.shouldntSeeRegisterFormSubmit();
	}			
	
	@Test
	public void shouldSeeErrorOnEnterOfBlankUsername() throws InterruptedException {
		registerPo
			.registerFromHomepage("", "aaron3", "")
			.shouldSeeBlanksError(true)
			.shouldSeeRegisterFormSubmit();
	}

	@Test
	public void shouldRegisterFromNonMainPage() throws InterruptedException {
		registerPo
			.registerFromHomepage("aaron4", "aaron4", "");
		addthreadPo
			.add("sup", "yeah", "yeah")
			.gotoThread(0);
		loginPo.logout();
		registerPo
		  .shouldSeeRegisterLinkText(true)
			.registerFromElsewhere("aaron5", "aaron5", "")
		  .shouldSeeRegisterLinkText(false);
	}

	
}
