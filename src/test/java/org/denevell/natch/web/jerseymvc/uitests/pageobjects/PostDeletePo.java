package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PostDeletePo {

	private WebDriver driver;

	public PostDeletePo(WebDriver driver) {
		this.driver = driver;
	}

	public PostDeletePo pressDeleteLink(int num) {
        driver.findElement(By.id("delete_"+num)).click();
		return this;
	}

	public PostDeletePo pressConfirmDelete() {
        driver.findElement(By.id("delete_post_form_submit")).click();
		return this;
	}

	public PostDeletePo canSeeDeleteLink(int num, boolean b) {
		if(!b) {
			try {
				driver.findElement(By.id("delete_"+num));
				org.junit.Assert.assertTrue("Found unexpected delete link", false);
			} catch (Exception e) {
				// All good
			}
		} else {
			try {
				driver.findElement(By.id("delete_"+num));
				// All good
			} catch (Exception e) {
				org.junit.Assert.assertTrue("Didn't find unexpected delete link", false);
			}
		}
		return this;
	}

  public PostDeletePo canSeeErrorText() {
    WebElement element = driver.findElement(By.id("error_message"));
    org.junit.Assert.assertTrue("Can see error text", element.getText().trim().length()>0);
    return this;
  }

	public PostDeletePo canPressConfirmLink(boolean b) {
        boolean enabled = driver.findElement(By.id("delete_post_form_submit")).isEnabled();
        String s = "you must be logged in";
		if(b) {
        	org.junit.Assert.assertFalse("Can't see please login text", driver.getPageSource().contains(s));
        	org.junit.Assert.assertTrue(enabled);
        } else {
        	org.junit.Assert.assertTrue("Can see please login text", driver.getPageSource().contains(s));
        	org.junit.Assert.assertFalse(enabled);
        }
        return this;
	}

}
