package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.WebDriver;

public class ListThreadsPo {

	private WebDriver driver;

	public ListThreadsPo(WebDriver driver) {
		this.driver = driver;
	}

	public ListThreadsPo hasText(String content, boolean b) {
        String pageText = driver.getPageSource();
        boolean title = pageText.contains(content);
        if(b) {
        	org.junit.Assert.assertTrue("Not expected", title);
        } else {
        	org.junit.Assert.assertFalse("Not expected", title);
        }
		return this;
	}
	
	public ListThreadsPo hasContent(String content, boolean b) {
		return hasText(content, b);
	}

	public ListThreadsPo hasSubject(String subject, boolean b) {
		return hasText(subject, b);
	}

	public ListThreadsPo hasAuthor(String author, boolean b) {
		return hasText(author, b);
	}
	

}
