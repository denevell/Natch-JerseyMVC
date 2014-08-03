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
import org.openqa.selenium.WebDriver;

public class PostReplyUITests {
	
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
		} catch (Exception e) { } 
		driver.quit();
	}
	
	/*
	
	@Test
	public void shouldReplyToPost() throws InterruptedException {
		// Arrange
		LogonUITests.logonCorrectly(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sub", "ccc", "");
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("xxx", driver);
        AddPostToThreadUITests.shouldAddPost("**secondpage**", driver);
        AddPostToThreadUITests.shouldAddPost("secondpage1", driver);

		// Act
	    java.util.List<WebElement> reply = driver.findElements(By.linkText("reply"));
	    reply.get(0).click();

		// Assert see post in reply box
        Pattern p = Pattern.compile(".*&gt;.*aaron.*&gt;.*secondpage.*", Pattern.DOTALL);
        String pageSource = driver.getPageSource();
		Matcher m = p.matcher(pageSource);
        boolean b = m.matches();
        assertTrue("Can see text in reply box", b);
        
		// Assert - can see text to edit
        p = Pattern.compile(".*\\*\\*secondpage\\*\\*.*", Pattern.DOTALL);
        pageSource = driver.getPageSource();
		m = p.matcher(pageSource);
        b = m.matches();
        assertTrue("Can see markdownshouldReplyToPost in edit box, not html", b);        
        
        // Act add most text and reply
		WebElement content = driver.findElement(By.name("content"));
		content.sendKeys("\n\nsome more text");
        WebElement submit= driver.findElement(By.name("submit"));
        submit.click();        
        
        // Assert see new reply
        p = Pattern.compile(".*aaron.*secondpage.*some more text.*", Pattern.DOTALL);
        pageSource = driver.getPageSource();
		m = p.matcher(pageSource);
        b = m.matches();
        assertTrue("Can see reply", b);
	}	
	
	@Test
	public void shouldSeeReplyDisabledOnNotLoggedIn() throws InterruptedException {
		// Arrange
		LogonUITests.logonCorrectly(driver);
        AddThreadUITests.fromHomepageAddAndGotoThread(driver, "sub", "ccc", "");
        String currentUrl = driver.getCurrentUrl();
        LogonUITests.logout(driver);
        driver.get(currentUrl);

		// Act
	    WebElement reply = driver.findElement(By.linkText("reply"));
	    reply.click();

		// Assert 
        WebElement submit= driver.findElement(By.name("submit"));
        String attribute = submit.getAttribute("disabled");
		assertTrue("Submit button should be disabled", attribute.equals("true"));
        
        // Assert
        String source = driver.getPageSource();
        assertTrue("Should see please logon text", source.toLowerCase().contains("please login"));	
   }		
	*/
				
}
