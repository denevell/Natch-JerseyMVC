package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadEditPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ThreadEditUITests {

  private WebDriver driver;
  private RegisterPo registerPo;
  private LoginoutPo loginPo;
  private ThreadAddPo addthreadPo;
  private PostAddPo addpostPo;
  private ThreadEditPo threadEditPo;

  @Before
  public void setup() throws Exception {
    TestUtils.deleteTestDb();
    driver = SeleniumDriverUtils.getDriver();
    driver.get(URLs.HOMEPAGE);
    registerPo = new RegisterPo(driver);
    loginPo = new LoginoutPo(driver);
    addthreadPo = new ThreadAddPo(driver);
    addpostPo = new PostAddPo(driver);
    threadEditPo = new ThreadEditPo(driver);
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
    } catch (Exception e) {
    }
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
    threadEditPo
      .hasEditThreadMarker(0, true)
      .hasEditThreadMarker(1, false);
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
    threadEditPo.hasEditThreadMarker(0, false);
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
    threadEditPo.hasEditThreadMarker(0, false);
  }

  @Test
  public void shouldntSeeEditThreadIconOnPagesAfterFirst() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya").addPageOfPosts();
    threadEditPo.hasEditThreadMarker(0, false);
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
    threadEditPo.hasEditThreadMarker(0, true);
  }

  /*
   * @Test public void shouldEditThread() throws InterruptedException { //
   * Arrange LogonUITests.logonCorrectly(driver); // Arrange - Add post
   * AddThreadUITests.fromHomepageAddAndGotoThread(driver, "ax", "bx", "cx");
   * AddPostToThreadUITests.shouldAddPost("xxx", driver);
   * 
   * // Act driver.findElement(By.linkText("[Edit thread]")).click();
   * driver.findElement(By.name("content")).sendKeys("EDITEDC");
   * driver.findElement(By.name("subject")).sendKeys("SUBJECTS");
   * driver.findElement(By.name("tags")).sendKeys("TAGS");
   * driver.findElement(By.id("editpost_submit_input")).click();
   * 
   * // Assert String pageText = driver.getPageSource(); boolean post =
   * pageText.contains("EDITEDC"); boolean subject =
   * pageText.contains("SUBJECTS"); boolean author = pageText.contains("aaron");
   * assertTrue("Expected post", post); assertTrue("Expected subject", subject);
   * assertTrue("Expected author", author); }
   * 
   * @Test public void shouldEditAsAdminAndSeeText() throws InterruptedException
   * { // Arrange - login as non admin, add a thread and post
   * LogonUITests.logonCorrectlyAsUserTwo(driver);
   * AddThreadUITests.fromHomepageAddAndGotoThread(driver, "suuub", "cccon",
   * "taaag");
   * 
   * // Act - login as admin and see delete markers LogonUITests.logout(driver);
   * LogonUITests.logonCorrectly(driver);
   * driver.findElement(By.linkText("[Edit thread]")).click();
   * editWhileOnEditPage(driver, "New Subject", "Admin edited", "New tag");
   * 
   * // Assert boolean hasOldText =
   * driver.getPageSource().toLowerCase().contains("suuub".toLowerCase());
   * boolean edited =
   * driver.getPageSource().toLowerCase().contains("New Subject".toLowerCase());
   * boolean editedByAdmin =
   * driver.getPageSource().toLowerCase().contains("Edited by an admin"
   * .toLowerCase()); assertFalse("No longer has previously added text",
   * hasOldText); assertTrue("Has edited post", edited);
   * assertTrue("See admin edited statement", editedByAdmin); }
   */
}
