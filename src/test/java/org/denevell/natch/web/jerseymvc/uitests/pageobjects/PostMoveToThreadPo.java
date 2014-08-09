package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PostMoveToThreadPo {

	private WebDriver driver;

	public PostMoveToThreadPo(WebDriver driver) {
		this.driver = driver;
	}

	public PostMoveToThreadPo pressMoveLink(int num) {
        driver.findElement(By.id("move_to_thread_"+num)).click();
		return this;
	}

	public PostMoveToThreadPo cantSeeMoveLink(int num) {
		try {
			driver.findElement(By.id("move_to_thread_"+num));
			org.junit.Assert.assertTrue("Shouldn't see move link", false);
		} catch(Exception e) {
			// Good
		}
		return this;
	}

	public PostMoveToThreadPo addSubject(String subject) {
        driver.findElement(By.id("move_to_thread_subject")).sendKeys(subject);
		return this;
	}

	public PostMoveToThreadPo pressConfirm() {
        driver.findElement(By.id("move_to_thread_submit")).click();
		return this;
	}

}
