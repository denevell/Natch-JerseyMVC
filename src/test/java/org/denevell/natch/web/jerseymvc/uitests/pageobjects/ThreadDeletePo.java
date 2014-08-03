package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ThreadDeletePo {

	private WebDriver driver;

	public ThreadDeletePo(WebDriver driver) {
		this.driver = driver;
	}

	public ThreadDeletePo pressDeleteLink() {
        driver.findElement(By.id("delete_thread_form_submit")).click();
		return this;
	}

	public ThreadDeletePo pressConfirmDelete() {
        driver.findElement(By.id("delete_thread_form_submit")).click();
		return this;
	}

	public ThreadDeletePo canSeeDeleteLink(boolean b) {
		if(!b) {
			try {
				driver.findElement(By.id("delete_thread_form_submit"));
				org.junit.Assert.assertTrue("Found unexpected delete link", false);
			} catch (Exception e) {
				// All good
			}
		} else {
			try {
				driver.findElement(By.id("delete_thread_form_submit"));
				// All good
			} catch (Exception e) {
				org.junit.Assert.assertTrue("Didn't find unexpected delete link", false);
			}
		}
		return this;
	}

}
