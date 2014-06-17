package org.denevell.natch.web.jerseymvc.uitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ListThreadsUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private AddThreadPo addthreadPo;
	private RegisterPo registerPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		loginPo = new LoginoutPo(driver);
		addthreadPo = new AddThreadPo(driver);
		registerPo = new RegisterPo(driver);
		registerPo.registerFromHomepage("aaron", "aaron", "");
		loginPo.logout();
	}

	@After
	public void destroy() {
		try {
			loginPo.logout();
		} catch (Exception e) { } 
        driver.quit();
	}
	
	@Test
	public void shouldSeeThreads() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo.addPagePlusOneOfThreads();
		addthreadPo.add("Hello", "cont", "tag");
		
		// Act 
        driver.get(URLs.HOMEPAGE);
        // Get today's date
        Calendar c = Calendar.getInstance();
        int dom = c.get(Calendar.DAY_OF_MONTH);
        String month = new SimpleDateFormat("MMM").format(c.getTime());
        int year = c.get(Calendar.YEAR);
        String dateString = dom + " " + month + " " + year;
        
        // Assert
        addthreadPo
        	.hasPost(0, "Hello")
        	.hasAuthor(0, "aaron")
        	.hasDate(0, dateString);
	}

	@Test
	public void shouldClickOnNextButton() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo.addPagePlusOneOfThreads();
        driver.get(URLs.HOMEPAGE);
        WebElement nextText = driver.findElement(By.id("next"));
		
		// Act 
        nextText.click();
        
        // Assert
        assertTrue(driver.getCurrentUrl().contains("page=2"));
        addthreadPo
        	.hasPost(0, "suby5");
	}
	
	@Test
	public void shouldClickOnPageTwoLinkThenPageOneLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo.addPagePlusOneOfThreads();
        driver.get(URLs.HOMEPAGE);
        WebElement page2 = driver.findElement(By.id("page2"));
		
		// Act - go to page 2
        page2.click();
        
        // Assert
        assertTrue(driver.getCurrentUrl().contains("page=2"));
        addthreadPo
        	.hasPost(0, "suby5");

		// Act - go to page 1
        WebElement page1 = driver.findElement(By.id("page1"));
        page1.click();

        // Assert
        assertTrue(driver.getCurrentUrl().contains("page=1"));
        addthreadPo
        	.hasPost(0, "suby0");
	}	
	
	@Test
	public void shouldClickOnNextLinkThenPageOneLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo.addPagePlusOneOfThreads();
        driver.get(URLs.HOMEPAGE);
        WebElement next = driver.findElement(By.id("next"));
		
		// Act - go to page 2
        next.click();
        assertTrue("Should see page 2 text", driver.getPageSource().contains("suby0"));
        assertFalse("Shouldn't see page 1 text", driver.getPageSource().contains("suby5"));

		// Act - go to page 1
        WebElement page1 = driver.findElement(By.id("page1"));
        page1.click();

        // Assert
        assertFalse("Shouldnt see page 2 text", driver.getPageSource().contains("suby0"));
        assertTrue("Should see page 1 text", driver.getPageSource().contains("suby5"));
	}		
	
	@Test
	public void shouldClickOnPrevButton() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo.addPagePlusOneOfThreads();
        driver.get(URLs.HOMEPAGE);
        WebElement nextText = driver.findElement(By.id("next"));
        nextText.click();
        WebElement prevText = driver.findElement(By.id("prev"));
		
		// Act 
        prevText.click();
        
        // Assert
        assertTrue(driver.getCurrentUrl().contains("page=1"));
        addthreadPo
        	.hasPost(0, "suby0");
	}
	
	@Test
	public void shouldSeeNoThreadsMessage() {
		// Arrange
		
		// Act 
        driver.get(URLs.HOMEPAGE);
        
        // Assert
        boolean emptyText = driver.getPageSource().contains("No content as yet!");
        assertTrue(emptyText);
	}	
	
}
