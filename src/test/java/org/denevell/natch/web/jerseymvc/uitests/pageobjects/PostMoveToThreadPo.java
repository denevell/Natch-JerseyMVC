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

	public PostMoveToThreadPo canSeeMoveLink(int num, boolean t) {
		try {
			driver.findElement(By.id("move_to_thread_"+num));
		} catch(Exception e) {
			if(t) {
				org.junit.Assert.assertTrue("Should see move link", false);
			}
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
