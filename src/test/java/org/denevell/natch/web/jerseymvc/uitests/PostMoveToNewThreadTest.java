package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostMoveToThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PostMoveToNewThreadTest {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private RegisterPo registerPo;
	private ThreadAddPo addthreadPo;
	private PostAddPo addpostPo;
	private PostMoveToThreadPo postMoveToThreadPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		driver.get(URLs.HOMEPAGE);
		registerPo = new RegisterPo(driver);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new ThreadAddPo(driver);
		addpostPo = new PostAddPo(driver);
		postMoveToThreadPo = new PostMoveToThreadPo(driver);
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
	public void shouldMovePostToNewThread() throws InterruptedException {
		// Add as normal user
		loginPo
			.login("aaron2", "aaron2");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
        addpostPo.add("one");
        String originalThread = driver.getCurrentUrl();
        // Go back as admin
        driver.get(URLs.HOMEPAGE);
        loginPo
        	.logout();
		loginPo
			.login("aaron", "aaron");
		driver.get(originalThread);
		// Move the post
		postMoveToThreadPo
			.pressMoveLink(1)
			.addSubject("new thread innit")
			.pressConfirm();
		addpostPo
			.hasCorrectPageTitle("new thread innit")
			.hasPost(0, "one")
			.hasEditedByAdminFlag(0);
        // Gone from old thread
		driver.get(originalThread);
        org.junit.Assert.assertFalse("Can't see moved post", 
        		driver.getPageSource().toLowerCase().contains("one"));
	}

	@Test
	public void shouldntSeeMovePostToThreadAsNormalUser() throws InterruptedException {
		loginPo
			.login("aaron2", "aaron2");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
        addpostPo
        	.add("one");
		postMoveToThreadPo
			.canSeeMoveLink(1, false);
	}

}
