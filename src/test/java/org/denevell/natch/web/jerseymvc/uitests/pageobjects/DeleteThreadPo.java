package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteThreadPo {

	private WebDriver driver;

	public DeleteThreadPo(WebDriver driver) {
		this.driver = driver;
	}

	public DeleteThreadPo pressDeleteLink() {
        driver.findElement(By.id("delete_thread_form_submit")).click();
		return this;
	}

	public DeleteThreadPo pressConfirmDelete() {
        driver.findElement(By.id("delete_thread_form_submit")).click();
		return this;
	}

	public DeleteThreadPo canSeeDeleteLink(boolean b) {
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
