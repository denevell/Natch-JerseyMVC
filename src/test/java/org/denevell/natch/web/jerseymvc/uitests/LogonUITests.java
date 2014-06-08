package org.denevell.natch.web.jerseymvc.uitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.denevell.natch.web.jerseymvc.Strings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogonUITests {
	
	private WebDriver driver;

	@Before
	public void setup() throws Exception {
		TestUtils.deleteTestDb();
		driver = SeleniumDriverUtils.getDriver();
        driver.get(URLs.HOMEPAGE);
		addTestUserToDb(driver);
		logout(driver);
	}
	
	@After
	public void destroy() {
        LogonUITests.logout(driver);
        driver.quit();
	}
	
	@Test
	public void shouldLogonCorrectly() throws InterruptedException {
		// Arrange / Act
        logonCorrectly(driver);
        
        // Assert
        assertTrue(driver.getCurrentUrl().equals(URLs.HOMEPAGE));
        //assertFalse(driver.getPageSource().contains("Logon"));
        boolean source = driver.getPageSource().contains(Strings.getBlankUsernameOrPassword());
        assertFalse(source);
	}

/**
	@Test
	public void shouldntLogonCorrectly() {
		// Arrange 
        driver.get(URLs.HOMEPAGE);
        
        // Act
        WebElement username_input = driver.findElement(By.name("username"));
        WebElement password_input = driver.findElement(By.name("password"));
        WebElement submit_input = driver.findElement(By.id("logon_submit_input"));
        username_input.sendKeys("notreeeeeal");
        password_input.sendKeys("sup");
        submit_input.click();
        
        // Assert
        boolean source = driver.getPageSource().contains(Strings.getLogonError());
        assertTrue(source);
	}
	
	@Test
	public void shouldntLogonWithBlankUsername() {
		// Arrange 
        driver.get(URLs.HOMEPAGE);
        
        // Act
        WebElement username_input = driver.findElement(By.name("username"));
        WebElement password_input = driver.findElement(By.name("password"));
        WebElement submit_input = driver.findElement(By.id("logon_submit_input"));
        username_input.sendKeys(" ");
        password_input.sendKeys("xxx");
        submit_input.click();
        
        // Assert
        boolean source = driver.getPageSource().contains(Strings.getBlankUsernameOrPassword());
        assertTrue(source);
	}	
	
	@Test
	public void shouldntLogonWithBlankPassword() {
		// Arrange 
        driver.get(URLs.HOMEPAGE);
        
        // Act
        WebElement username_input = driver.findElement(By.name("username"));
        WebElement password_input = driver.findElement(By.name("password"));
        WebElement submit_input = driver.findElement(By.id("logon_submit_input"));
        username_input.sendKeys("xxx");
        password_input.sendKeys(" ");
        submit_input.click();
        
        // Assert
        boolean source = driver.getPageSource().contains(Strings.getBlankUsernameOrPassword());
        assertTrue(source);
	}		
	
	@Test
	public void shouldntLogonWithBlanks() {
		// Arrange 
        driver.get(URLs.HOMEPAGE);
        
        // Act
        WebElement username_input = driver.findElement(By.name("username"));
        WebElement password_input = driver.findElement(By.name("password"));
        WebElement submit_input = driver.findElement(By.id("logon_submit_input"));
        username_input.sendKeys(" ");
        password_input.sendKeys(" ");
        submit_input.click();
        
        // Assert
        boolean source = driver.getPageSource().contains(Strings.getBlankUsernameOrPassword());
        assertTrue(source);
	}			
**/	
	public static void addTestUserToDb(WebDriver driver) {
        driver.get(URLs.HOMEPAGE);
		//driver.findElement(By.id("reg_link")).click();
		
        // Act 
		WebElement username_input = driver.findElement(By.id("register_username_input"));
        WebElement password_input = driver.findElement(By.id("register_password_input"));
        WebElement submit_input = driver.findElement(By.id("register_form_submit"));
		username_input.clear();
        username_input.sendKeys("aaron");
        password_input.clear();
        password_input.sendKeys("aaron");
        submit_input.click();
	    logout(driver);
	}
	
	public static void logonCorrectly(WebDriver d) throws InterruptedException {
		logonCorrectlyAs(d, "aaron", "aaron");
	}
	
	public static void logonCorrectlyAsUserTwo(WebDriver d) throws InterruptedException {
		logonCorrectlyAs(d, "aaron2", "aaron2");
	}
	
	public static void logonCorrectlyAs(WebDriver d, String user, String pass) throws InterruptedException {
		WebElement username_input = d.findElement(By.id("login_username_input"));
        WebElement password_input = d.findElement(By.id("login_password_input"));
        WebElement submit_input = d.findElement(By.id("login_form_submit"));
		username_input.clear();
        username_input.sendKeys(user);
        password_input.clear();
        password_input.sendKeys(pass);
        submit_input.click();
	}

	public static void logout(WebDriver driver) {
		try {
			WebElement findElement = driver.findElement(By.id("logout"));
			findElement.click();
		} catch (Exception e) {
		}
	}
	
}
