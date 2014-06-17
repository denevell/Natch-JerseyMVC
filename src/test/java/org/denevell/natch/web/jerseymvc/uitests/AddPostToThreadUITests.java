package org.denevell.natch.web.jerseymvc.uitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddPostPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class AddPostToThreadUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private AddThreadPo addthreadPo;
	private AddPostPo addpostPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		driver.get(URLs.HOMEPAGE);
		registerPo = new RegisterPo(driver);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new AddThreadPo(driver);
		addpostPo = new AddPostPo(driver);
		registerPo.register("aaron", "aaron", "");
		loginPo.logout();
		registerPo.register("aaron2", "aaron2", "");
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
	public void shouldAddPost() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
		addpostPo
			.add("xxx");
        String url = driver.getCurrentUrl();
        driver.navigate().back();
		loginPo
			.logout()
			.login("aaron2", "aaron2");
		driver.get(url);
		addpostPo
			.add("yyy")
			.hasPost(1, "xxx")
			.hasAuthor(1, "aaron")
			.hasPost(2, "yyy")
			.hasAuthor(2, "aaron2");
	}
	
	@Test
	public void shouldSeeErrorPageOnBlankInput() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
		addpostPo
			.add("  ")
			.hasInputError();
	}	
	
	@Test
	public void shouldSeePleaseLogon() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
        String oldUrl = driver.getCurrentUrl();
        driver.navigate().back();
		loginPo.logout();
        driver.get(oldUrl);
		addpostPo
			.submitButtonIsDisabled()
			.hasPleaseLogonToAddPost();
	}	
	
	@Test
	public void shouldAddAndRemainOnPaginatedPage() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
        String originalUrl = driver.getCurrentUrl();
        addpostPo
        	.add("xxx")
        	.add("xxx")
        	.add("xxx")
        	.add("xxx")
        	.add("xxx")
        	.add("second page");
        String url = driver.getCurrentUrl();
        assertNotEquals("On new page", originalUrl, url);
        addthreadPo
        	.gotoThread(0);
        addpostPo
        	.add("yyy");
        String newUrl = driver.getCurrentUrl();
        assertEquals("Added post and returned to page two", url, newUrl);
	}	
}
