package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PostReplyPo {

	private WebDriver driver;

	public PostReplyPo(WebDriver driver) {
		this.driver = driver;
	}

	public PostReplyPo add(String content) {
        WebElement content_input = driver.findElement(By.id("post_content_input"));
        WebElement addinput_input = driver.findElement(By.id("addpost_form_submit"));
        content_input.clear();
        content_input.sendKeys(content);
        addinput_input.click();
        return this;
	}

	public PostReplyPo clickReply(int i) {
		List<WebElement> singlePostLink = driver.findElements(By.linkText("reply"));
		singlePostLink.get(i).click();
		return this;
	}

	public PostReplyPo canSeeRegexText(String string, boolean b) {
        Pattern p = Pattern.compile(string, Pattern.DOTALL);
        String pageSource = driver.getPageSource();
        Matcher m = p.matcher(pageSource);
        boolean matches = m.matches();
        if(b) {
        	org.junit.Assert.assertTrue("Can see regex", matches);
        } else {
        	org.junit.Assert.assertFalse("Can't see regex", matches);
        }
		return this;
	}

	public PostReplyPo enterReplyText(String string) {
		WebElement content = driver.findElement(By.name("content"));
		content.sendKeys(string);
    WebElement submit= driver.findElement(By.id("post_reply_submit"));
    submit.click();        
    return this;
	}

	public PostReplyPo submitIsDisabled(boolean b) {
        WebElement submit= driver.findElement(By.name("submit"));
        String attribute = submit.getAttribute("disabled");
        if(b) {
        	org.junit.Assert.assertTrue("Submit button should be disabled", attribute.equals("true"));
        } else {
        	org.junit.Assert.assertFalse("Submit button should not be disabled", attribute.equals("true"));
        }
        return this;
	}

	public PostReplyPo shouldSeeLoginText(boolean b) {
        String source = driver.getPageSource();
        if(b) {
        	org.junit.Assert.assertTrue("Should see please logon text", source.toLowerCase().contains("please login"));	
        } else {
        	org.junit.Assert.assertFalse("Shouldn't see please logon text", source.toLowerCase().contains("please login"));	
        }
        return this;
	}

}
