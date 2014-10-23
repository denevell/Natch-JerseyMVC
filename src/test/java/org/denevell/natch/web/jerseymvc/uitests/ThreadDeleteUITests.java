package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadDeletePo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadsListPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ThreadDeleteUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private ThreadAddPo addThreadPo;
	private ThreadDeletePo deleteThreadPo;
	private ThreadsListPo listThreadPo;
  private PostAddPo addpostPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		listThreadPo = new ThreadsListPo(driver);
		deleteThreadPo = new ThreadDeletePo(driver);
		registerPo = new RegisterPo(driver);
		addThreadPo = new ThreadAddPo(driver);
		addpostPo = new PostAddPo(driver);
		loginPo = new LoginoutPo(driver);
		driver.get(URLs.HOMEPAGE);
		registerPo.register("aaron", "aaron", null);
		loginPo.logout();
		registerPo.register("aaron2", "aaron", null);
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
	public void shouldDeleteThread() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addThreadPo
			.add("subjectt", "conttt", "c")
			.gotoThread(0);
		
		deleteThreadPo
			.pressDeleteLink()
			.pressConfirmDelete();
		
		listThreadPo
			.hasAuthor("aaron", false)
			.hasSubject("subjectt", false)
			.hasContent("conttt", false);
	}
	
    @Test
    public void shouldDeleteThreadAsAdmin() throws InterruptedException {
		loginPo
			.login("aaron2", "aaron");
		addThreadPo
			.add("subjectt", "conttt", "c");
		driver.get(URLs.HOMEPAGE);

		loginPo
			.logout()
			.login("aaron", "aaron");
		addThreadPo
			.gotoThread(0);

		deleteThreadPo
			.pressDeleteLink()
			.pressConfirmDelete();
		
		listThreadPo
			.hasAuthor("aaron", false)
			.hasSubject("subjectt", false)
			.hasContent("conttt", false);
    } 

	@Test
	public void shouldntSeeDeleteThreadWhenNotLoggedIn() throws InterruptedException {
		loginPo
			.login("aaron2", "aaron");
		addThreadPo
			.add("subjectt", "conttt", "c");
		driver.get(URLs.HOMEPAGE);
		loginPo
			.logout();

		addThreadPo
			.gotoThread(0);

		deleteThreadPo
			.canSeeDeleteLink(false);
	}

	@Test
	public void shouldntSeeDeleteThreadPageMoreThanOne() throws InterruptedException {
		loginPo
			.login("aaron2", "aaron");
		addThreadPo
			.add("subjectt", "conttt", "c")
			.gotoThread(0);
		addpostPo.addPageOfPosts().add("yoooooooooooooooooonubian");
		deleteThreadPo
			.canSeeDeleteLink(false);
	}
	
	@Test
	public void shouldntSeeDeleteThreadWhenLoggedInAsSomeoneElse() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addThreadPo
			.add("subjectt", "conttt", "c");
		driver.get(URLs.HOMEPAGE);
		loginPo
			.logout();

		loginPo
			.login("aaron2", "aaron");
		addThreadPo
			.gotoThread(0);

		deleteThreadPo
			.canSeeDeleteLink(false);
	}	

}
