package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostReplyPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PostReplyUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;
	private PostAddPo addpostPo;
	private PostReplyPo replyPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		driver.get(URLs.HOMEPAGE);
		registerPo = new RegisterPo(driver);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new ThreadAddPo(driver);
		replyPo = new PostReplyPo(driver);
		addpostPo = new PostAddPo(driver);
		registerPo.register("aaron", "aaron", "");
		loginPo.logout();
		registerPo.register("aaron2", "aaron2", "");
		loginPo.logout();
		driver.get(URLs.HOMEPAGE);
	}
	
	@After
	public void destroy() {
		try {
			loginPo.logout();
		} catch (Exception e) { } 
		driver.quit();
	}
	
	@Test
	public void shouldReplyToPostWithMarkdown() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
		addpostPo
			.addPageOfPosts()
			.add("**secondpage**");
		replyPo
			.clickReply(0)
			.canSeeRegexText(".*&gt;.*aaron.*&gt;.*secondpage.*", true)
        	.canSeeRegexText(".*\\*\\*secondpage\\*\\*.*", true)
        	.enterReplyText("\n\nsome more text")
        	.canSeeRegexText(".*aaron.*secondpage.*some more text.*", true);
	}	
	
	@Test
	public void shouldSeeReplyDisabledOnNotLoggedIn() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
		String url = driver.getCurrentUrl();
		driver.get(URLs.HOMEPAGE);
		loginPo
			.logout();
        driver.get(url);
        replyPo
        	.clickReply(0)
        	.submitIsDisabled(true)
        	.shouldSeeLoginText(true);
   }		
}
