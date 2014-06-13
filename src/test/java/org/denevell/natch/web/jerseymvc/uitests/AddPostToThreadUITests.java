package org.denevell.natch.web.jerseymvc.uitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.denevell.natch.web.jerseymvc.uitests.pageobjects.AddThreadPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.LoginoutPo;
import org.denevell.natch.web.jerseymvc.uitests.pageobjects.RegisterPo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddPostToThreadUITests {
	
	private WebDriver driver;
	private RegisterPo registerPo;
	private LoginoutPo loginPo;
	private AddThreadPo addthreadPo;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();;
		driver.get(URLs.HOMEPAGE);
		registerPo = new RegisterPo(driver);
		loginPo = new LoginoutPo(driver);
		addthreadPo = new AddThreadPo(driver);
		registerPo.register("aaron", "aaron", "");
		loginPo.logout();
		registerPo.register("aaron2", "aaron2", "");
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
	public void shouldAddThread() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
		
        shouldAddPost("xxx", driver);
        String url = driver.getCurrentUrl();
		loginPo.logout();
		loginPo.login("aaron2", "aaron2");
		driver.get(url);
        shouldAddPost("yyy", driver);
        
        // Assert
        String source = driver.getPageSource();
        assertTrue(source.contains("yyy"));
        assertTrue(source.contains("xxx"));
        assertTrue(source.contains("aaron2"));
	}
	
	@Test
	public void shouldSeeErrorPageOnBlankInput() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
		
        WebElement content = driver.findElement(By.name("content"));
        content.clear();
        content.sendKeys("  ");
        WebElement submit= driver.findElement(By.name("submit"));
        submit.click();
        
        // Assert
        String source = driver.getPageSource();
        assertTrue(source.toLowerCase().contains("problem adding post"));
	}	
	
	@Test
	public void shouldSeePleaseLogon() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);

        String oldUrl = driver.getCurrentUrl();
		loginPo.logout();
        driver.get(oldUrl);
		
		// Act 
        WebElement submit= driver.findElement(By.name("submit"));
        String attribute = submit.getAttribute("disabled");
		assertTrue("Submit button should be disabled", attribute.equals("true"));
        
        // Assert
        String source = driver.getPageSource();
        assertTrue("please logon to add post text", source.contains("please logon to add post"));
	}	
	
	@Test
	public void shouldGoBackToPaginatedPage() throws InterruptedException {
		loginPo
			.login("aaron", "aaron");
		addthreadPo
			.add("sub", "ccc", "")
			.gotoThread(0);
		
		// Act 
        String originalUrl = driver.getCurrentUrl();
        shouldAddPost("xxx", driver);
        shouldAddPost("xxx", driver);
        shouldAddPost("xxx", driver);
        shouldAddPost("xxx", driver);
        shouldAddPost("xxx", driver);
        shouldAddPost("seconpage", driver);
        String url = driver.getCurrentUrl();
        // Assert new page
        assertNotEquals("On new page", originalUrl, url);
        // Add final post
        shouldAddPost("yyy", driver);
        String newUrl = driver.getCurrentUrl();
        
        // Assert
        assertEquals("Added post and returned to page two", url, newUrl);
	}	

	static public void shouldAddPost(CharSequence textContent, WebDriver dr) {
		WebElement content = dr.findElement(By.id("add_post_content"));
		content.sendKeys(textContent);
        WebElement submit= dr.findElement(By.name("submit"));
        submit.click();
	}
}
