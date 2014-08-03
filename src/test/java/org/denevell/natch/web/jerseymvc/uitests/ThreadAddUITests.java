package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ThreadAddUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		driver.get(URLs.HOMEPAGE);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new ThreadAddPo(driver);
		registerPo = new RegisterPo(driver);
		registerPo.registerFromHomepage("aaron", "aaron", "");
		loginPo.logout();
	}
	
	@After
	public void destroy() {
		try {
			loginPo.logout();
		} catch (Exception e) { }
		driver.quit();
	}
	
	@Test
	public void shouldAddThread() throws InterruptedException {
		loginPo.login("aaron", "aaron");
        addthreadPo
        	.add("subjy", "cccont", "tag,tag2")
        	.hasTitle(0, "subjy")
        	.hasAuthor(0, "aaron");
	}

	@Test
	public void shouldShowInputError() throws InterruptedException {
		loginPo.login("aaron", "aaron");
        addthreadPo
        	.add("subjy", " ", "tag,tag2")
        	.showsInputError();
	}
	
}
