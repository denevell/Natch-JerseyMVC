package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostDeletePo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PostDeleteUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private ThreadAddPo addthreadPo;
	private PostAddPo addpostPo;
	private PostDeletePo postDeletePo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		driver.get(URLs.HOMEPAGE);
		registerPo = new RegisterPo(driver);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new ThreadAddPo(driver);
		addpostPo = new PostAddPo(driver);
		postDeletePo = new PostDeletePo(driver);
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
	public void shouldDeletePost() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
		
		// Add a page plus one of posts
        String oldUrl = driver.getCurrentUrl();
        addpostPo.addPagePlusOneOfPosts();
        String secondPageUrl = driver.getCurrentUrl();
        org.junit.Assert.assertNotEquals("Gone to second page", oldUrl, secondPageUrl);        
        addpostPo.canSeePostOnSecondPage(true);

        postDeletePo
        	.pressDeleteLink(0)
        	.pressConfirmDelete();
	    
	    // Assert first post gone
        String currentUrl = driver.getCurrentUrl();
        org.junit.Assert.assertNotEquals("Gone to second page", currentUrl, secondPageUrl);        
        addpostPo.canSeePostOnSecondPage(false);
	}	
	
    @Test
    public void shouldDeletePostAsAdmin() throws InterruptedException {
		loginPo
			.login("aaron2", "aaron2");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
        addpostPo.add("pooost");
        
        // Login as admin
        driver.get(URLs.HOMEPAGE);
		loginPo
			.logout()
			.login("aaron", "aaron");
		addthreadPo
			.gotoThread(0);

        postDeletePo
        	.pressDeleteLink(0)
        	.pressConfirmDelete();
        addpostPo.cantSeePost("pooost");
    }   	

	@Test
	public void shouldNotSeeDeleteMarksWhenNotLoggedIn() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
        addpostPo
        	.add("pooost");
        postDeletePo
        	.canSeeDeleteLink(0, true);
        String deletePage = driver.getCurrentUrl();

        // Logout, shouldn't be active
        driver.get(URLs.HOMEPAGE);
		loginPo
			.logout();
		driver.get(deletePage);
        postDeletePo
        	.canSeeDeleteLink(0, false);
	}

	@Test
	public void shouldNotSeeDeleteMarksWhenLoggedInAsAnother() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("s", "b", "c")
			.gotoThread(0);
        addpostPo
        	.add("pooost");
        postDeletePo
        	.canSeeDeleteLink(0, true);
        String deletePage = driver.getCurrentUrl();

        // Logout, shouldn't be active
        driver.get(URLs.HOMEPAGE);
		loginPo
			.logout()
			.login("aaron2", "aaron2");
		driver.get(deletePage);
        postDeletePo
        	.canSeeDeleteLink(0, false);
	}	

	/*
	
	@Test
	public void shouldGoBackToPreviousPageOnDeleteOnlyPostOnPage() throws InterruptedException {
		// Arrange - logon
		LogonUITests.logonCorrectly(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
        
		// Act  - add posts
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        String oldUrl = driver.getCurrentUrl();
        
        AddPostToThreadUITests.shouldAddPost("yyy", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("pagetwopointone", driver);
        String secondPageUrl = driver.getCurrentUrl();
        assertNotEquals("Gone to second page", oldUrl, secondPageUrl);        

        // Act 
        // Press delete button
	    List<WebElement> del = driver.findElements(By.linkText("[Del]"));
	    del.get(0).click();
        
        // Assert - delete text there
	    // Act - confirm the delete
	    assertTrue("Contains delete text", driver.getPageSource().contains("Sure you want to delete the post?"));
	    driver.findElement(By.name("confirmdelete")).click();
	    
	    // Assert first post gone
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Gone to first page paga", currentUrl, oldUrl);
	    // Assert still have last post on page two
	    assertFalse("Doesn't contain post xxx", driver.getPageSource().contains("pagetwopointone"));
	}		
	
	@Test
	public void shouldSeeErrorWhenLoggedOut() throws InterruptedException {
		// Arrange - logon
		LogonUITests.logonCorrectly(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
        
		// Act  - add posts
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("yyy", driver);
        // Assert text there
	    assertTrue("Contains post xxx", 
	    		driver.getPageSource().contains("xxx"));
        // Press delete button
	    List<WebElement> del = driver.findElements(By.linkText("[Del]"));
	    del.get(0).click();
        // Logout and come back
	    String oldUrl = driver.getCurrentUrl();
        LogonUITests.logout(driver);
	    driver.get(oldUrl);
	    
	    // Assert login error
	    assertTrue("Should see auth error", driver.getPageSource().contains("please logon"));
	}		
	
	*/
}
