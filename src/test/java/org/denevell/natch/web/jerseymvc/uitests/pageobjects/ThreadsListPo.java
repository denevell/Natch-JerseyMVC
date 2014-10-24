package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.WebDriver;

public class ThreadsListPo {

	private WebDriver driver;

	public ThreadsListPo(WebDriver driver) {
		this.driver = driver;
	}

	public ThreadsListPo hasText(String content, boolean b) {
        String pageText = driver.getPageSource();
        boolean title = pageText.toLowerCase().contains(content);
        if(b) {
        	org.junit.Assert.assertTrue("Expected text", title);
        } else {
        	org.junit.Assert.assertFalse("Not expected text", title);
        }
		return this;
	}
	
	public ThreadsListPo hasContent(String content, boolean b) {
		return hasText(content, b);
	}

	public ThreadsListPo hasSubject(String subject, boolean b) {
		return hasText(subject, b);
	}

	public ThreadsListPo hasAuthor(String author, boolean b) {
		return hasText(author, b);
	}
	

}
