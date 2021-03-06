package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PostsListsInThreadUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;
	private RegisterPo registerPo;
	private PostAddPo addpostPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		loginPo = new LoginoutPo(driver);
		addthreadPo = new ThreadAddPo(driver);
		addpostPo = new PostAddPo(driver);
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
	public void shouldSeePaginationAndTitlesAndMarkdown() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.addPagePlusOneOfPosts()
			.hasPost(0, "c10")
			.cantSeePost("c0")
			.hasCorrectPageTitle("suby0")
			.clickPrev()
			.hasCorrectPageTitle("suby0")
			.hasPost(1, "c0")
			.cantSeePost("c10")
			.hasAuthor(0, "aaron")
			.hasTodaysDate(0);
	}

	@Test
	public void shouldGoBackToHomeViaHeader() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0)
			.clickOnHeader()
			.amOnHomepage();
	}		
	
	@Test
	public void shouldClickOnPageTwoLinkThenPageOneLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.addPagePlusOneOfPosts()
			.clickOnPageLink("2")
			.isOnPageViaUrl(2)
			.clickOnPageLink("1")
			.isOnPageViaUrl(1);
	}		
	
	@Test
	public void shouldClickOnNextAndOnFirstPage() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.addPagePlusOneOfPosts()
			.clickPrev()
			.clickNext()
			.hasPost(0, "c10")
			.cantSeePost("c0")
			.clickOnPageLink("1")
			.cantSeePost("c10")
			.hasPost(1, "c0");
	}			

	@Test
	public void shouldSeeNumberOfPaginatedThreads() throws Exception {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.add("hi")
			.seeNumberOfPaginatedPages(1)
			.addPagePlusOneOfPosts()
			.seeNumberOfPaginatedPages(2);
	}


	@Test
	public void shouldStayOnSamePageOnNextWithOnePageOfPosts() throws Exception {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo.clickOnPageLink("1");
		String addr = driver.getCurrentUrl();
		addpostPo.clickPrev();
		String addr1 = driver.getCurrentUrl();
		org.junit.Assert.assertTrue("Url didn't change on prev button", addr.equals(addr1));
		addpostPo.clickNext();
		addr1 = driver.getCurrentUrl();
		org.junit.Assert.assertTrue("Url didn't change on next button", addr.equals(addr1));
	}
	
}
