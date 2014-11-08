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

	@Test
	public void shouldSeeEditPage() throws InterruptedException {
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya é");
    postEditPo.pressEditPost(1).hasEditContent("hiya é", true);
	}	

	@Test
	public void shouldEditPostAndGoBackToPaginatedPage() throws InterruptedException {
    // Add a page and two posts of data
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    String pageOneUrl = driver.getCurrentUrl();
    addpostPo.addPagePlusOneOfPosts();
    String pageTwoUrl = driver.getCurrentUrl();
		org.junit.Assert.assertNotEquals("Gone to second page", pageOneUrl, pageTwoUrl);
    // Act - submit edited text
    postEditPo.pressEditPost(0).editAsContent("hiyaXQQ é");
    addpostPo.hasPost(0, "hiyaXQQ é");
    addpostPo.hasAuthor(0, "aaron");
    // Are on second page still
		String currentUrl = driver.getCurrentUrl();
		org.junit.Assert.assertEquals("Gone to second page", currentUrl, pageTwoUrl);
	}

	@Test
	public void shouldEditPostAndSeeMarkdown() throws InterruptedException {
    // Add a page and two posts of data
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("**hi**");
    addpostPo.hasPost(1, "hi", true);
    addpostPo.hasPost(1, "**hi**", false);
    // Act - submit edited text
    postEditPo.pressEditPost(1).hasEditContent("**hi**", true).editAsContent("**there**");
    addpostPo.hasPost(1, "there", true);
    addpostPo.hasPost(1, "**there**", false);
	}
	
	@Test
	public void shouldFailThenSucceedToEditPost() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    postEditPo.pressEditPost(1).editAsContent("").shouldSeeErrorValue(true);
    postEditPo.editAsContent("hiya33");
    addpostPo.hasPost(1, "hiya33");
    addpostPo.hasAuthor(1, "aaron");
	}

	@Test
	public void shouldEditAsAdminAndSeeText() throws InterruptedException {
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    driver.get(URLs.HOMEPAGE);
    loginPo.logout().login("aaron", "aaron");
    addthreadPo.gotoThread(0);
    postEditPo.pressEditPost(1).editAsContent("aaaadddmmmiin");
    addpostPo.hasPost(1, "aaaadddmmmiin").hasAuthor(1, "aaron2");
    addpostPo.hasEditedByAdminFlag(1);
	}	
	
	@Test
	public void shouldntBeAbleToEditWhenNotLoggedIn() throws InterruptedException {
    loginPo.login("aaron2", "aaron2");
    addthreadPo.add("s", "b", "c").gotoThread(0);
    addpostPo.add("hiya");
    postEditPo.pressEditPost(1);
    String url = driver.getCurrentUrl();
    driver.get(URLs.HOMEPAGE);
    loginPo.logout();
    driver.get(url);
    postEditPo.submitButtonDisabled(true);
	}		

}
