package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AdminPO;
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

public class AdminToggleTest {

	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;
	private PostAddPo addpostPo;
	private AdminPO adminPo;
	private PostMoveToThreadPo postMoveToThreadPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		driver.get(URLs.HOMEPAGE);
		registerPo = new RegisterPo(driver);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new ThreadAddPo(driver);
		adminPo = new AdminPO(driver);
		addpostPo = new PostAddPo(driver);
		postMoveToThreadPo = new PostMoveToThreadPo(driver);
		registerPo.registerFromHomepage("aaron", "aaron", "");
		loginPo.logout();
		registerPo.registerFromHomepage("aaron2", "aaron2", "");
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
	public void shouldUseToggleAdmin() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		adminPo.gotoAdminPage()
			.isAdmin("aaron2", false)
			.adminToggle("aaron2")
			.isAdmin("aaron2", true)
			.adminToggle("aaron2")
			.isAdmin("aaron2", false);
	}	
	
	@Test
	public void shouldSeeMoveToNewThreadAfterAdmin() throws InterruptedException {
		// Add post as admin
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
		addpostPo
			.add("xxx");
    String url = driver.getCurrentUrl();
    driver.navigate().back();
    // Ensure we can't see move to thread as normal user
		loginPo
			.logout()
			.login("aaron2", "aaron2");
	  driver.get(url);
	  postMoveToThreadPo.canSeeMoveLink(1, false);
	  // Make normal user admin
    driver.navigate().back();
		loginPo
			.logout()
			.login("aaron", "aaron");
		adminPo.gotoAdminPage()
			.adminToggle("aaron2")
			.isAdmin("aaron2", true);
	    driver.get(URLs.HOMEPAGE);
		loginPo
			.logout()
			.login("aaron2", "aaron2");
	  driver.get(url);
	  postMoveToThreadPo
	    	.canSeeMoveLink(2, false);
	}
}
