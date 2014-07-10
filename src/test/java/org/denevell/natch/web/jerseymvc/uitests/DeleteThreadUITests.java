package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.DeleteThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ListThreadsPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class DeleteThreadUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private AddThreadPo addThreadPo;
	private DeleteThreadPo deleteThreadPo;
	private ListThreadsPo listThreadPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		listThreadPo = new ListThreadsPo(driver);
		deleteThreadPo = new DeleteThreadPo(driver);
		registerPo = new RegisterPo(driver);
		addThreadPo = new AddThreadPo(driver);
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
//	
//	@Test
//	public void shouldntSeeDeleteThreadWhenLoggedInAsSomeoneElse() throws InterruptedException {
//		// Arrange - logon
//		LogonUITests.logonCorrectly(driver);
//        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
//		
//		// Act 
//        String curUrl = driver.getCurrentUrl();
//        LogonUITests.logout(driver);
//		LogonUITests.logonCorrectlyAs(driver, "aaron2", "aaron2");
//        driver.get(curUrl);
//        
//        // Assert
//        assertFalse(driver.getPageSource().contains("Delete thread"));
//	}	
//	
//	@Test
//	public void shouldntSeeDeleteThreadIconOnPagesAfterFirst() throws InterruptedException {
//		// Arrange - add posts to go onto second page 
//		LogonUITests.logonCorrectly(driver);
//        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sub", "ccc", "");
//        AddPostToThreadUITests.shouldAddPost("xxx", driver);
//        AddPostToThreadUITests.shouldAddPost("xxx", driver);
//        AddPostToThreadUITests.shouldAddPost("xxx", driver);
//        AddPostToThreadUITests.shouldAddPost("xxx", driver);
//        assertTrue("Page two doesn't contain delete thread", driver.getPageSource().toLowerCase().contains("delete thread"));		
//        AddPostToThreadUITests.shouldAddPost("seconpage", driver);
//        WebElement nextText = driver.findElement(By.id("next"));
//
//        // Add final post
//        nextText.click();        
//        
//        // Assert
//        assertTrue("Page two doesn't contain delete thread", !driver.getPageSource().toLowerCase().contains("delete thread"));		
//	}		

}
