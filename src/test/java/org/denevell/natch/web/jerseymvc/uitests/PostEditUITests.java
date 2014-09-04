package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostEditPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PostEditUITests {
	
  private WebDriver driver;
  private RegisterPo registerPo;
  private LoginoutPo loginPo;
  private ThreadAddPo addthreadPo;
  private PostAddPo addpostPo;
  private PostEditPo postEditPo;

  @Before
  public void setup() throws Exception {
    TestUtils.deleteTestDb();
    driver = SeleniumDriverUtils.getDriver();
    driver.get(URLs.HOMEPAGE);
    registerPo = new RegisterPo(driver);
    loginPo = new LoginoutPo(driver);
    addthreadPo = new ThreadAddPo(driver);
    addpostPo = new PostAddPo(driver);
    postEditPo = new PostEditPo(driver);
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
    } catch (Exception e) {}
    driver.quit();
  }

  @Test
  public void shouldShowEditThreadLink() throws InterruptedException {
    loginPo
      .login("aaron", "aaron");
    addthreadPo
      .add("s", "b", "c")
      .gotoThread(0);
    addpostPo
      .add("pooost");
    postEditPo
      .hasEditPostMarker(0, false)
      .hasEditPostMarker(1, true);
  }

  @Test
  public void shouldntShowEditThreadLinkOnLoggedOut() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("pooost");
    String url = driver.getCurrentUrl();
    driver.get(URLs.HOMEPAGE);
    loginPo.logout();
    driver.get(url);
    postEditPo.hasEditPostMarker(1, false);
  }

  @Test
  public void shouldntShowEditThreadLinkOnDifferentUser() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("pooost");
    String url = driver.getCurrentUrl();
    driver.get(URLs.HOMEPAGE);
    loginPo.logout().login("aaron2", "aaron2");
    driver.get(url);
    postEditPo.hasEditPostMarker(1, false);
  }

  @Test
  public void shouldSeeEditAsAdmin() throws InterruptedException {
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    String url = driver.getCurrentUrl();
    driver.get(URLs.HOMEPAGE);
    loginPo.logout().login("aaron", "aaron");
    driver.get(url);
    postEditPo.hasEditPostMarker(1, true);
  }
  /*
	@Test
	public void shouldSeeEditPageWithSubjectTagsHidden() throws InterruptedException {
		// Arrange
		LogonUITests.logonCorrectly(driver);
		
		// Act
		AddThreadUITests.fromHomepageAddAndGotoThread(driver, "ax", "bx", "cx");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
		driver.findElement(By.linkText("[Edit]")).click();
		
		// Assert 
		WebElement contentIntput = driver.findElement(By.name("content"));
		assertNotNull(contentIntput);
		assertTrue("Can see edit thread submit button", driver.getPageSource().contains("Edit"));
		
		assertTrue("Can see content", driver.getPageSource().contains("xxx"));
	}	
	
	@Test
	public void shouldEditPost() throws InterruptedException {
		// Arrange
		LogonUITests.logonCorrectly(driver);
		// Arrange - Add post 
		AddThreadUITests.fromHomepageAddAndGotoThread(driver, "ax", "bx", "cx");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        String oldUrl = driver.getCurrentUrl();
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("**pagetwo**", driver);
        String secondPageUrl = driver.getCurrentUrl();
        assertNotEquals("Gone to second page", oldUrl, secondPageUrl);
		
        // Act 
		driver.findElement(By.linkText("[Edit]")).click();

		// Assert - can see text to edit
        Pattern p = Pattern.compile(".*\\*\\*pagetwo\\*\\*.*", Pattern.DOTALL);
        String pageSource = driver.getPageSource();
		Matcher m = p.matcher(pageSource);
        boolean b = m.matches();
        assertTrue("Can see markdown in edit box, not html", b);

        // Act - submit edited text
		editWhileOnEditPage(driver, "EDITEDC");		
        
        // Assert
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Gone to second page", currentUrl, secondPageUrl);
        String pageText = driver.getPageSource();
        boolean post = pageText.contains("EDITEDC");
        boolean author = pageText.contains("aaron");
        assertTrue("Expected post", post);
        assertTrue("Expected author", author);        
	}

	@Test
	public void shouldFailThenSucceedToEditPost() throws InterruptedException {
		// Arrange
		LogonUITests.logonCorrectly(driver);
		// Arrange - Add post 
		AddThreadUITests.fromHomepageAddAndGotoThread(driver, "ax", "bx", "cx");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
		
        // Act 
		driver.findElement(By.linkText("[Edit]")).click();
		driver.findElement(By.name("content")).clear();
		driver.findElement(By.name("content")).sendKeys(" ");
        driver.findElement(By.id("editpost_submit_input")).click();		
        
        // Assert
        assertTrue(driver.getCurrentUrl().startsWith(URLs.EDITPOST));
        
        // Act - try again
		driver.findElement(By.name("content")).sendKeys("blar");
        driver.findElement(By.id("editpost_submit_input")).click();		
        
        // Assert
        String pageText = driver.getPageSource();
        boolean post = pageText.contains("blar");
        boolean author = pageText.contains("aaron");
        assertTrue("Expected post", post);
        assertTrue("Expected author", author);     
	}			

	@Test
	public void shouldEditAsAdminAndSeeText() throws InterruptedException {
		// Arrange - login as non admin, add a thread and post
		LogonUITests.logonCorrectlyAsUserTwo(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "s", "b", "c");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        
        // Act - login as admin and see delete markers
        LogonUITests.logout(driver);
		LogonUITests.logonCorrectly(driver);
        driver.findElement(By.linkText("[Edit]")).click();
        editWhileOnEditPage(driver, "Admin edited");
 
        // Assert
        boolean hasOldText = driver.getPageSource().toLowerCase().contains("xxx".toLowerCase());
        boolean edited = driver.getPageSource().toLowerCase().contains("Admin edited".toLowerCase());
        boolean editedByAdmin = driver.getPageSource().toLowerCase().contains("Edited by an admin".toLowerCase());
        assertFalse("No longer has previously added text", hasOldText);
        assertTrue("Has edited post", edited);
        assertTrue("See admin edited statement", editedByAdmin);
	}	
	
	@Test
	public void shouldntBeAbleToEditWhenNotLoggedIn() throws InterruptedException {
		// Arrange
		LogonUITests.logonCorrectly(driver);
		
		// Act
		AddThreadUITests.fromHomepageAddAndGotoThread(driver, "ax", "bx", "cx");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
		driver.findElement(By.linkText("[Edit]")).click();
		
        // Act - logout
        String currentUrl = driver.getCurrentUrl();
        LogonUITests.logout(driver);
        driver.get(currentUrl);		
		
		// Assert 
		WebElement submit = driver.findElement(By.id("editpost_submit_input"));
        String attribute = submit.getAttribute("disabled");
		assertTrue("Submit should be disabled", attribute.equals("true"));
        assertTrue(driver.getPageSource().contains("please login"));
	}		
	*/

}
