package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.WebDriver;

public class PostMoveToNewThreadTest {
	
	private WebDriver driver;

	/*
	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
		LogonUITests.addTestUserToDb(driver);
		LogonUITests.addTestTwoUserToDb(driver);
        LogonUITests.logout(driver);
        driver.get(URLs.HOMEPAGE);
	}
	
	@After
	public void destroy() {
        LogonUITests.logout(driver);
        driver.quit();
	}
	
	@Test
	public void shouldMovePostToNewThread() throws InterruptedException {
	    // Arrange - Add a new post and then logon as admin
		LogonUITests.logonCorrectlyAsUserTwo(driver);
		AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sub", "con", null);
		AddPostToThreadUITests.shouldAddPost("new post", driver);
		String oldThread = driver.getCurrentUrl();
        LogonUITests.logout(driver);
		LogonUITests.logonCorrectly(driver);
		
		// Act
        driver.findElement(By.id("move_to_thread")).click(); // This one to click the link
        driver.findElement(By.name("subject")).sendKeys("New thread innit");
        driver.findElement(By.id("move_to_thread")).click(); // This one to submit to form
        
        // Assert
        assertTrue("Can see admin edited flag", driver.getPageSource().toLowerCase().contains("edited by an admin"));
        assertTrue("Can see subject", driver.getPageSource().toLowerCase().contains("new thread innit"));
        assertTrue("Can see post", driver.getPageSource().toLowerCase().contains("new post"));
        
        // Act / Assert gone from old thread
        driver.get(oldThread);
        assertFalse("Can't see moved post", driver.getPageSource().toLowerCase().contains("new post"));
	}

	@Test
	public void shouldntSeeMovePostToThreadAsNormalUser() throws InterruptedException {
	    // Arrange - Add a new post and then logon as admin
		LogonUITests.logonCorrectlyAsUserTwo(driver);
		AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sub", "con", null);
		AddPostToThreadUITests.shouldAddPost("new post", driver);

		// Assert - Can't see as normal user
        assertFalse("Can't see move to post id", driver.getPageSource().toLowerCase().contains("move_to_thread"));
        
        // Assert - But can as an admin
        LogonUITests.logout(driver);
		LogonUITests.logonCorrectly(driver);
        assertTrue("Can't see move to post id", driver.getPageSource().toLowerCase().contains("move_to_thread"));
	}
	*/

}
