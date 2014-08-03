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

public class PostSingleUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;
	private PostAddPo addpostPo;
	private RegisterPo registerPo;

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
	public void shouldClickOnLinkForSinglePostAndSeeParentThreadLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.add("*cont1*")
			.clickOnSinglePost(1)
			.hasPostInSinglePage("<em>.*cont1.*</em>")
			.clickOnParentThreadFromSinglePostPage()
			.hasPostWithMarkdown(1, "<em>.*cont1.*</em>");
	}
	
}
