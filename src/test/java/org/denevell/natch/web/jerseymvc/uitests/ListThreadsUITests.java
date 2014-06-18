package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ListThreadsUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private AddThreadPo addthreadPo;
	private RegisterPo registerPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		loginPo = new LoginoutPo(driver);
		addthreadPo = new AddThreadPo(driver);
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
	public void shouldSeeThreads() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("Hello", "cont", "tag")
        	.hasPost(0, "Hello")
        	.hasAuthor(0, "aaron")
        	.hasTodaysDate(0);
	}

	@Test
	public void shouldClickOnNextButton() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.addPagePlusOneOfThreads()
			.clickOnPrev()
			.amOnPageByUrl("1")
			.hasPost(0, "suby0")
			.clickOnNext()
			.amOnPageByUrl("2")
			.hasPost(0, "suby5");
	}
	
	@Test
	public void shouldClickOnPageTwoLinkThenPageOneLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.addPagePlusOneOfThreads()
			.clickOnPrev()
			.clickOnPage("2")
			.amOnPageByUrl("2")
			.hasPost(0, "suby5")
			.clickOnPage("1")
			.amOnPageByUrl("1")
			.hasPost(0, "suby0");
	}	
	
	@Test
	public void shouldSeeNoThreadsMessage() {
        driver.get(URLs.HOMEPAGE);
        addthreadPo
        	.seeEmptyThreadsMessage();
	}	
	
}
