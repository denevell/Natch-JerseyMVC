package org.denevell.natch.web.jerseymvc.uitests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

public class DeletePostUITests {
	
	private WebDriver driver;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		//LogonUITests.addTestUserToDb(driver);
		//LogonUITests.addTestTwoUserToDb(driver);
        //LogonUITests.logout(driver);
		driver.get(URLs.HOMEPAGE);
	}
	
	@After
	public void destroy() {
        //LogonUITests.logout(driver);
		driver.quit();
	}

	/*
	
	
	@Test
	public void shouldDeletePost() throws InterruptedException {
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
        AddPostToThreadUITests.shouldAddPost("pagetwopointtwo", driver);
        String secondPageUrl = driver.getCurrentUrl();
        assertNotEquals("Gone to second page", oldUrl, secondPageUrl);        
        // Act 
		
        // Assert text there
	    assertTrue("Contains post xxx", driver.getPageSource().contains("pagetwopointone"));
        // Press delete button
	    List<WebElement> del = driver.findElements(By.linkText("[Del]"));
	    del.get(0).click();
        
        // Assert - delete text there
	    assertTrue("Contains delete text", driver.getPageSource().contains("Sure you want to delete the post?"));
	    
	    // Act - confirm the delete
	    driver.findElement(By.name("confirmdelete")).click();
	    
	    // Assert first post gone
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Gone to second page", currentUrl, secondPageUrl);
	    // Assert still have last post on page two
	    assertFalse("Doesn't contain post xxx", driver.getPageSource().contains("pagetwopointone"));
	}	

    @Test
    public void shouldDeletePostAsAdmin() throws InterruptedException {
        // Arrange
        LogonUITests.logonCorrectlyAsUserTwo(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sss", "b", "c");
        
        // Act  - add posts
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        
        // Press delete button
        driver.findElement(By.linkText("[Del]")).click();
        driver.findElement(By.name("confirmdelete")).click();
        
        // Assert first post gone
        assertFalse("Doesn't contain post xxx", driver.getPageSource().contains("xxx"));
        assertTrue("Does contain original thread text", driver.getPageSource().contains("sss"));
    }   	
	
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

	@Test
	public void shouldNotSeeDeleteMarksWhenNotLoggedIn() throws InterruptedException {
		// Arrange - logon
		LogonUITests.logonCorrectly(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
        
		// Act  - add posts
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("yyy", driver);
        
        // Assert - Delete is there when logged in 
        String pageText = driver.getPageSource();
        boolean b = pageText.toLowerCase().contains("[Del]".toLowerCase());
        assertTrue(b);
        
        // Act - logout
        String currentUrl = driver.getCurrentUrl();
        LogonUITests.logout(driver);
        driver.get(currentUrl);
        
        // Assert - Delete is not there when logged out 
        pageText = driver.getPageSource();
        b = pageText.toLowerCase().contains("[Del]".toLowerCase());
        assertFalse(b);
	}
	
	@Test
	public void shouldNotSeeDeleteMarksWhenLoggedInAsAnother() throws InterruptedException {
		// Arrange - logon
		LogonUITests.logonCorrectly(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
        
		// Act  - add posts
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        
        // Assert - Delete is there when logged in 
        String pageText = driver.getPageSource();
        boolean b = pageText.toLowerCase().contains("[Del]".toLowerCase());
        assertTrue(b);
        
        // Act - logout
        String currentUrl = driver.getCurrentUrl();
        LogonUITests.logout(driver);
		LogonUITests.logonCorrectlyAsUserTwo(driver);
        driver.get(currentUrl);
        
        // Assert - Delete is not there when logged out 
        b = driver.getPageSource().toLowerCase().contains("[Del]".toLowerCase());
        assertFalse(b);
	}	

	@Test
	public void shouldSeeDeleteMarksWhenAdmin() throws InterruptedException {
		// Arrange - login as non admin, add a thread and post
		LogonUITests.logonCorrectlyAsUserTwo(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        
        // Act - login as admin and see delete markers
        LogonUITests.logout(driver);
		LogonUITests.logonCorrectly(driver);
        String pageText = driver.getPageSource();
        boolean del = pageText.toLowerCase().contains("[Del]".toLowerCase());
        boolean delthread = pageText.toLowerCase().contains("[Delete thread]".toLowerCase());
        
        // Assert
        assertTrue(del);
        assertTrue(delthread);
	}	
	
	*/
}
