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
				org.junit.Assert.assertFalse("hasn't edit thread text", view.getText().toLowerCase().contains("edit thread"));
		} else {
				WebElement view = driver.findElement(By.id("post_"+num));
				org.junit.Assert.assertTrue("has edit thread text", view.getText().toLowerCase().contains("edit thread"));
		}
		return this;
    
  }

  public ThreadEditPo pressEditThread() {
    WebElement view = driver.findElement(By.id("post_0"));
    view.findElement(By.id("thread_edit")).click();
    return this;
  }

  public ThreadEditPo editAsContent(String subject, String content) {
    return editAsContent(subject, content, null);
  }

  public ThreadEditPo editAsContent(String subject, String content, String tags) {
    WebElement sub = driver.findElement(By.name("subject"));
    sub.clear();
    sub.sendKeys(subject);
    WebElement cont = driver.findElement(By.name("content"));
    cont.clear();
    cont.sendKeys(content);
    if(tags!=null) {
      WebElement tagsEle = driver.findElement(By.name("tags"));
      tagsEle.clear();
      tagsEle.sendKeys(tags);
    }
    driver.findElement(By.id("editpost_submit_input")).click();
    return this;
  }

  public ThreadEditPo shouldSeeErrorValue(boolean b) {
    if(b) {
      WebElement error = driver.findElement(By.id("error"));
      boolean errorExists = error.getText().trim().length()>0;
      org.junit.Assert.assertTrue("Should see erorr value", errorExists);
    } else {
      try {
        WebElement error = driver.findElement(By.id("error"));
        boolean errorExists = error.getText().trim().length()>0;
        org.junit.Assert.assertFalse("Shouldn't see error value", errorExists);
      } catch (Exception e) {
        // Fine
      }
    }
    return this;
  }

  public ThreadEditPo shouldSee(String string, boolean b) {
    boolean contains = driver.getPageSource().contains(string);
    if(b && contains) {
      return this; //yay
    } else if(!b && !contains) {
      return this; //yay
    } else if(b && !contains) {
        org.junit.Assert.assertFalse("Wanted to see text", true);
    } else if(!b && contains) {
        org.junit.Assert.assertFalse("Didn't want to see text", true);
    }
    return this;
  }


}
