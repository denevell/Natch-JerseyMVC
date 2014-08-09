package org.denevell.natch.web.jerseymvc.uitests;

public class AdminToggleTest {

	/*
	private WebDriver driver;
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
	public void shouldUseToggleAdmin() throws InterruptedException {
	    // Arrange
		LogonUITests.logonCorrectly(driver);
	    WebElement adminLink = driver.findElement(By.id("admin"));
	    
	    // Act
	    adminLink.click();
        assertTrue(driver.getPageSource().contains("aaron"));
        assertTrue(driver.getPageSource().contains("aaron2"));
	    WebElement aaron2AdminButton = driver.findElement(By.id("aaron2_admin"));
	    
	    // Assert
	    assertEquals(aaron2AdminButton.getText(), "false");

	    // Act - toggle admin
	    aaron2AdminButton.click();
	    
	    // Assert
	    aaron2AdminButton = driver.findElement(By.id("aaron2_admin"));
	    assertEquals(aaron2AdminButton.getText(), "true");

	    // Act - de toggle admin
	    aaron2AdminButton = driver.findElement(By.id("aaron2_admin"));
	    aaron2AdminButton.click();

	    // Assert
	    aaron2AdminButton = driver.findElement(By.id("aaron2_admin"));
	    assertEquals(aaron2AdminButton.getText(), "false");
	}

	@Test
	public void shouldSeeMoveToNewThreadAfterAdmin() throws InterruptedException {
	    // Assert
	    LogonUITests.logonCorrectly(driver); // Login as admin
	    AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sub", "cont", null);
	    AddPostToThreadUITests.shouldAddPost("new post", driver);
	    String threadUrl = driver.getCurrentUrl();
	    // Assert - login as normal user and check we don't see move to new thread
	    LogonUITests.logout(driver);
	    LogonUITests.logonCorrectlyAsUserTwo(driver);
	    driver.get(threadUrl);
	    assertFalse("Can't see move to thread", driver.getPageSource().toLowerCase().contains("move to new thread"));

	    // Act - make user2 an admin
	    LogonUITests.logout(driver);
	    LogonUITests.logonCorrectly(driver); // Login as admin
	    driver.findElement(By.id("admin")).click();
	    driver.findElement(By.id("aaron2_admin")).click();
	    driver.get(threadUrl);
	    LogonUITests.logout(driver);
	    LogonUITests.logonCorrectlyAsUserTwo(driver);
	    
	    // Assert - now we can see move to thread
	    assertTrue("Can't see move to thread", driver.getPageSource().toLowerCase().contains("move to new thread"));
	}
	*/

}
