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

public class TagsUITests {
	
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
    } catch (Exception e) {}
    driver.quit();
  }
	
	@Test
	public void shouldSeeAddedTagsOnMainPage() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo.add("s", "b", "omgatag,andanotheromgomg");
    addthreadPo.hasTag(0, "omgatag", true).hasTag(0, "andanotheromgomg", true);
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

	/*

	@Test
	public void shouldClickOnTagOnSingleThreadPage() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo
    .add("s", "b", "omgatag1")
    .add("s", "b", "tag2")
    .gotoThread(1);
		
        // Act - click on tag
        driver.findElement(By.linkText("tag2")).click();
        
        // Assert
        String currentUrl = driver.getCurrentUrl();
        assertTrue("Am now on homepage", currentUrl.startsWith(URLs.HOMEPAGE));
        String pageText = driver.getPageSource();
        boolean tag1 = pageText.contains("tag2");
        boolean subjectIsThere = pageText.contains("othersubject");
        boolean otherSubjectIsThere = pageText.contains("subjy");
        assertTrue("Expected tag", tag1);
        assertTrue("Expected subject", subjectIsThere);
        assertFalse("Expected other subjet isn't there", otherSubjectIsThere);
	}		
	
	@Test
	public void shouldClickOnTagOnThreadsList() throws InterruptedException {
    loginPo.login("aaron", "aaron");
    addthreadPo
    .add("s", "b", "tag1")
    .add("s", "b", "tag2").gotoThread(1);
		
        // Act - click on tag
        driver.findElement(By.linkText("tag2")).click();
        
        // Assert - See only that thread
        String pageText = driver.getPageSource();
        boolean tag1 = pageText.contains("tag2");
        boolean subjectIsThere = pageText.contains("othersubject");
        boolean otherSubjectIsThere = pageText.contains("subjy");
        assertTrue("Expected tag", tag1);
        assertTrue("Expected subject", subjectIsThere);
        assertFalse("Expected other subjet isn't there", otherSubjectIsThere);

        // Act - click on other tag
        driver.get(URLs.HOMEPAGE);
        driver.findElement(By.linkText("tag1")).click();
        
        // Assert - See only that thread
        pageText = driver.getPageSource();
        tag1 = pageText.contains("tag1");
        subjectIsThere = pageText.contains("subjy");
        otherSubjectIsThere = pageText.contains("othersubject");
        assertTrue("Expected tag", tag1);
        assertTrue("Expected subject", subjectIsThere);
        assertFalse("Expected other subjet isn't there", otherSubjectIsThere);
	}		
	*/
}
