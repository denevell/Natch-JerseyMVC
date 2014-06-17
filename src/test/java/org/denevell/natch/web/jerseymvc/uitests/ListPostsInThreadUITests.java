package org.denevell.natch.web.jerseymvc.uitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddPostPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ListPostsInThreadUITests {
	
	private WebDriver driver;
	private LoginoutPo loginPo;
	private AddThreadPo addthreadPo;
	private RegisterPo registerPo;
	private AddPostPo addpostPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		loginPo = new LoginoutPo(driver);
		addthreadPo = new AddThreadPo(driver);
		addpostPo = new AddPostPo(driver);
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
	public void shouldSeePaginationAndTitlesAndMarkdown() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.add("c0")
			.add("c1")
			.add("c2")
			.add("c3")
			.add("c4");
		// Pre act assert
        String pageText = driver.getPageSource();
        assertFalse("Can't see first post", pageText.contains("c0"));
        assertTrue("Can see final post", pageText.contains("c4"));
        assertTrue("Get correct page title", driver.getTitle().startsWith("suby0"));
		
        // Get today's date
        Calendar c = Calendar.getInstance();
        int dom = c.get(Calendar.DAY_OF_MONTH);
        String month = new SimpleDateFormat("MMM").format(c.getTime());
        int year = c.get(Calendar.YEAR);
        String dateString = dom + " " + month + " " + year;        
        
        // Act
        WebElement nextText = driver.findElement(By.id("prev"));
        nextText.click();

        // Assert text there
        pageText = driver.getPageSource();
        assertTrue("Get correct page title", driver.getTitle().startsWith("suby0"));
        assertTrue("Can see first post", pageText.contains("c0"));
        assertFalse("Can't final post", pageText.contains("c4"));
        assertTrue("Can see first post author", pageText.contains("aaron"));
        assertTrue("Can see today's date", pageText.contains(dateString));
	}
	
	@Test
	public void shouldClickOnPageTwoLinkThenPageOneLink() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.add("c0")
			.add("c1")
			.add("c2")
			.add("c3")
			.add("c4");

        WebElement page2 = driver.findElement(By.id("page2"));
		// Act - go to page 2
        page2.click();
        
        // Assert
        assertTrue(driver.getCurrentUrl().contains("page=2"));

		// Act - go to page 1
        WebElement page1 = driver.findElement(By.id("page1"));
        page1.click();

        // Assert
        assertTrue(driver.getCurrentUrl().contains("page=1"));
	}		
	
	@Test
	public void shouldClickOnNextAndOnFirstPage() throws InterruptedException {
		loginPo.loginFromHomepage("aaron", "aaron");
		addthreadPo
			.add("suby0", "cont0", "tagx")
			.gotoThread(0);
		addpostPo
			.add("c0")
			.add("c1")
			.add("c2")
			.add("c3")
			.add("c4");

        WebElement next = driver.findElement(By.id("next"));
		// Act - go to page 2
        next.click();
        assertTrue("Can final post", driver.getPageSource().contains("c4"));

		// Act - go to page 1
        WebElement page1 = driver.findElement(By.id("page1"));
        page1.click();

        // Assert
        assertFalse("Can't final post", driver.getPageSource().contains("c4"));
        assertTrue("Can first post", driver.getPageSource().contains("c0"));
	}			
	
}
