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
    registerPo.registerFromHomepage("aaron", "aaron", "");
    loginPo.logout();
    registerPo.registerFromHomepage("aaron2", "aaron2", "");
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

   @Test 
   public void shouldEditThread() throws InterruptedException { 
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    threadEditPo.pressEditThread().editAsContent("new subject é", "new content é");
    addpostPo.hasPost(0, "new content é")
    .hasCorrectPageTitle("new subject é")
    .hasAuthor(0, "aaron2");
   }

	@Test
	public void shouldSeeEditThreadPreserveAccents() throws InterruptedException {
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("hiya é", "there ê", "againè").gotoThread(0);
    threadEditPo.pressEditThread();
    threadEditPo.shouldSee("hiya é", true);
    threadEditPo.shouldSee("there ê", true);
    threadEditPo.shouldSee("againè", true);
	}	


   @Test 
   public void shouldEditAsAdminAndSeeText() throws InterruptedException { 
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    String url = driver.getCurrentUrl();
    driver.get(URLs.HOMEPAGE);
    loginPo.logout().login("aaron", "aaron");
    driver.get(url);
    threadEditPo.pressEditThread().editAsContent("new subject", "new content");
    addpostPo.hasPost(0, "new content")
    .hasCorrectPageTitle("new subject")
    .hasAuthor(0, "aaron2")
    .hasEditedByAdminFlag(0);
   }

   @Test 
   public void shouldFailThenSucceedToEdit() throws InterruptedException { 
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    threadEditPo.pressEditThread()
    .shouldSeeErrorValue(false)
    .editAsContent("", "new content")
    .shouldSeeErrorValue(true)
    .editAsContent("new subject", "new content")
    .shouldSeeErrorValue(false);
    addpostPo.hasPost(0, "new content")
    .hasCorrectPageTitle("new subject")
    .hasAuthor(0, "aaron2");
   }
}
