package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ThreadEditPo {

	private WebDriver driver;

	public ThreadEditPo(WebDriver driver) {
		this.driver = driver;
	}

  public ThreadEditPo hasEditThreadMarker(int num, boolean b) {
		if(!b) {
		    WebElement view = driver.findElement(By.id("post_"+num));
				org.junit.Assert.assertFalse(view.getText().contains("edit thread"));
		} else {
				WebElement view = driver.findElement(By.id("post_"+num));
				org.junit.Assert.assertTrue(view.getText().contains("edit thread"));
		}
		return this;
    
  }

}
