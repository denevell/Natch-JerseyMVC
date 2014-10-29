package org.denevell.natch.web.jerseymvc.uitests;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadAddPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadEditPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.ThreadsListPo;
import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.TestUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class TagsUITests {
	
  private WebDriver driver;

  private RegisterPo registerPo;
  private LoginoutPo loginPo;
  private ThreadAddPo addthreadPo;
  private ThreadEditPo threadEditPo;
  private ThreadsListPo threadsListPo;

  @Before
  public void setup() throws Exception {
    TestUtils.deleteTestDb();
    driver = SeleniumDriverUtils.getDriver();
    driver.get(URLs.HOMEPAGE);
    registerPo = new RegisterPo(driver);
    loginPo = new LoginoutPo(driver);
    addthreadPo = new ThreadAddPo(driver);
    threadEditPo = new ThreadEditPo(driver);
    threadsListPo = new ThreadsListPo(driver);
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
	public void shouldSeeAddedTagsOnMainPage() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "omgatag,andanother");
    addthreadPo.hasTag(0, "omgatag", true).hasTag(0, "andanother", true);
    addthreadPo.numberOfTags(0, 2);
	}

	@Test
	public void shouldSeeTagsInThread () throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "xomgatag,xandanotheromgomg").gotoThread(0);
		
    addthreadPo.hasTagInThreadPage("xomgatag", true).hasTagInThreadPage("xandanotheromgomg", true);
    addthreadPo.numberOfTagsInThreadPage(2);
	}		
	
	@Test
	public void shouldOnlySeeThreeTagsOnMainPage() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "tag1,tag2,tag3,tag4,x");
    addthreadPo.hasTag(0, "tag1", true);
    addthreadPo.hasTag(0, "tag2", true);
    addthreadPo.hasTag(0, "tag3", true);
    addthreadPo.numberOfTags(0, 3);
	}	
	
	@Test
	public void shouldOnlySee15charactersPerTagOnMainPage() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "#####|||||!!!!!$");
    addthreadPo.hasTag(0, "#####|||||!!!!!...", true);
    addthreadPo.numberOfTags(0, 1);
	}			

	@Test
	public void shouldSeeEditedTagsDelete() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "omgatag,andanotheromgomg").gotoThread(0);
    threadEditPo.pressEditThread();
    threadEditPo.editAsContent("Sub", "Cont", "omgatag");
    addthreadPo.hasTagInThreadPage("omgatag", true);
    addthreadPo.numberOfTagsInThreadPage(1);
    driver.get(URLs.HOMEPAGE);
    addthreadPo.hasTag(0, "omgatag", true);
    addthreadPo.numberOfTags(0, 1);   
	}
	
	@Test
	public void shouldSeeEditedTagsAdd() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "omgatag,andanotheromgomg").gotoThread(0);
    threadEditPo.pressEditThread();
    threadEditPo.editAsContent("Sub", "Cont", "omgatag,andanotheromgomg,yeah");
    addthreadPo.hasTagInThreadPage("yeah", true);
    addthreadPo.numberOfTagsInThreadPage(3);
    driver.get(URLs.HOMEPAGE);
    addthreadPo.hasTag(0, "yeah", true);
    addthreadPo.numberOfTags(0, 3);   
	}	

	@Test
	public void shouldClickOnTagOnSingleThreadPage() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo
    .add("s1", "b", "omgatag1")
    .add("s2", "b", "tag2").gotoThread(0)
    .clickOnTag("tag2");
        
    String currentUrl = driver.getCurrentUrl();
    org.junit.Assert.assertTrue("Am now on homepage", currentUrl.startsWith(URLs.HOMEPAGE));

    addthreadPo.hasTag(0, "tag2", true);
    addthreadPo.hasTitle(0, "s2");
    threadsListPo.hasText("s1", false);
	}		

	@Test
	public void shouldClickOnTagOnThreadsList() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo
    .add("s1", "b", "omgatag1")
    .add("s2", "b", "tag2")
    .clickOnTag("tag2");
        
    String currentUrl = driver.getCurrentUrl();
    org.junit.Assert.assertTrue("Am now on homepage", currentUrl.startsWith(URLs.HOMEPAGE));

    addthreadPo.hasTag(0, "tag2", true);
    addthreadPo.hasTitle(0, "s2");
    threadsListPo.hasText("s1", false);
	}		
}
