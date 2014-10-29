package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.PostDeletePo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
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
  public void shouldDeletePost() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);

    // Add a page plus one of posts
    String oldUrl = driver.getCurrentUrl();
    addpostPo.addPagePlusOneOfPosts();
    String secondPageUrl = driver.getCurrentUrl();
    org.junit.Assert.assertNotEquals("Gone to second page", oldUrl,
        secondPageUrl);
    addpostPo.canSeePostOnSecondPage(true);

    postDeletePo.pressDeleteLink(0).pressConfirmDelete();

    // Assert first post gone
    String currentUrl = driver.getCurrentUrl();
    org.junit.Assert.assertNotEquals("Gone to second page", currentUrl,
        secondPageUrl);
    addpostPo.canSeePostOnSecondPage(false);
  }

  @Test
  public void shouldDeletePostAsAdmin() throws InterruptedException {
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("pooost");

    // Login as admin
    driver.get(URLs.HOMEPAGE);
    loginPo.logout().login("aaron", "aaron");
    addthreadPo.gotoThread(0);

    postDeletePo.pressDeleteLink(1).pressConfirmDelete();
    addpostPo.cantSeePost("pooost");
  }

  @Test
  public void shouldNotSeeDeleteMarksWhenNotLoggedIn()
      throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("pooost");
    postDeletePo.canSeeDeleteLink(1, true);
    String deletePage = driver.getCurrentUrl();

    // Logout, shouldn't be active
    driver.get(URLs.HOMEPAGE);
    loginPo.logout();
    driver.get(deletePage);
    postDeletePo.canSeeDeleteLink(1, false);
  }

  @Test
  public void shouldNotSeeDeleteMarksWhenLoggedInAsAnother()
      throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("pooost");
    postDeletePo.canSeeDeleteLink(1, true);
    String deletePage = driver.getCurrentUrl();

    // Logout, shouldn't be active
    driver.get(URLs.HOMEPAGE);
    loginPo.logout().login("aaron2", "aaron2");
    driver.get(deletePage);
    postDeletePo.canSeeDeleteLink(1, false);
  }

  @Test
  public void shouldNotSeeDeleteOnFirstPost()
      throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("pooost");
    postDeletePo.canSeeDeleteLink(0, false);
  }

  @Test
  public void shouldSeeDisableButtoWhenLoggedOut() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    postDeletePo.pressDeleteLink(1).canPressConfirmLink(true);
    String deletePage = driver.getCurrentUrl();

    // Logout and come back
    driver.get(URLs.HOMEPAGE);
    loginPo.logout();
    driver.get(deletePage);
    postDeletePo.canPressConfirmLink(false);
  }

  @Test
  public void shouldSeeDeletPostError() throws Exception {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    postDeletePo.pressDeleteLink(1);
    postDeletePo.pressConfirmDelete();
    driver.navigate().back();
    postDeletePo.pressConfirmDelete();
    postDeletePo.canSeeErrorText();
  }

  @Test
  public void shouldGoBackToPreviousPageOnDeleteOnlyPostOnPage()
      throws InterruptedException {
    // Add a page and two posts of data
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    String pageOneUrl = driver.getCurrentUrl();
    addpostPo.addPagePlusOneOfPosts().add("last_post");
    String pageTwoUrl = driver.getCurrentUrl();
    // Delete the last on page two, and still on page two
    postDeletePo.pressDeleteLink(1).pressConfirmDelete();
    addpostPo.canSeePostOnSecondPage(true);
    org.junit.Assert.assertEquals("Still on second page", pageTwoUrl,
        driver.getCurrentUrl());
    // Delete the last on page two and page on page one
    postDeletePo.pressDeleteLink(0).pressConfirmDelete();
    org.junit.Assert.assertEquals("Now on first page", pageOneUrl,
        driver.getCurrentUrl());
  }
}
