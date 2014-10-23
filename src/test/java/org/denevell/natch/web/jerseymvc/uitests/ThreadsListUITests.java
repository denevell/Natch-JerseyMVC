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

public class ThreadsListUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;
	private RegisterPo registerPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
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
			.amOnPageByUrl(1)
			.hasPost(0, "suby0")
			.clickOnNext()
			.amOnPageByUrl(2)
			.hasPost(0, "suby10");
	}
	
	@Test
	public void shouldClickOnPageTwoLinkThenPageOneLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.addPagePlusOneOfThreads()
			.clickOnPrev()
			.clickOnPage(2)
			.amOnPageByUrl(2)
			.hasPost(0, "suby5")
			.clickOnPage(1)
			.amOnPageByUrl(1)
			.hasPost(0, "suby0");
	}	

	@Test
	public void shouldStayOnSamePageOnNextWithOnePageOfThreads() throws Exception {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo.add("hi", "hi", "hi");
	  addthreadPo.clickOnPage(1);
		String addr = driver.getCurrentUrl();
	  addthreadPo.clickOnPrev();
		String addr1 = driver.getCurrentUrl();
		org.junit.Assert.assertTrue("Url didn't change on prev button", addr.equals(addr1));
	  addthreadPo.clickOnNext();
		addr1 = driver.getCurrentUrl();
		org.junit.Assert.assertTrue("Url didn't change on next button", addr.equals(addr1));
	}

	@Test
	public void shouldSeeNumberOfPaginatedThreads() throws Exception {

	}
	
	@Test
	public void shouldSeeNoThreadsMessage() {
        driver.get(URLs.HOMEPAGE);
        addthreadPo
        	.seeEmptyThreadsMessage();
	}	
	
}
