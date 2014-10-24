package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PostEditPo {

	private WebDriver driver;

	public PostEditPo(WebDriver driver) {
		this.driver = driver;
	}

  public PostEditPo hasEditPostMarker(int num, boolean b) {
		if(!b) {
		    WebElement view = driver.findElement(By.id("post_"+num));
				org.junit.Assert.assertFalse(view.getText().contains("edit post"));
		} else {
				WebElement view = driver.findElement(By.id("post_"+num));
				org.junit.Assert.assertTrue(view.getText().contains("edit post"));
		}
		return this;
    
  }

  public PostEditPo pressEditPost(int num) {
    WebElement view = driver.findElement(By.id("post_"+num));
    view.findElement(By.id("post_edit")).click();
    return this;
  }

  public PostEditPo editAsContent(String content) {
    WebElement cont = driver.findElement(By.name("content"));
    cont.clear();
    cont.sendKeys(content);
    driver.findElement(By.id("editpost_submit_input")).click();
    return this;
  }

  public PostEditPo shouldSeeErrorValue(boolean b) {
    if(b) {
      WebElement error = driver.findElement(By.id("error"));
      boolean errorExists = error.getText().trim().length()>0;
      org.junit.Assert.assertTrue(errorExists);
    } else {
      try {
        WebElement error = driver.findElement(By.id("error"));
        boolean errorExists = error.getText().trim().length()>0;
        org.junit.Assert.assertFalse(errorExists);
      } catch (Exception e) {
        // Fine
      }
    }
    return this;
  }

  public PostEditPo hasEditContent(String content, boolean b) {
    WebElement contentInput = null; 
		try {
      contentInput = driver.findElement(By.name("content"));
    } catch (Exception e) {
      org.junit.Assert.assertTrue("Can't find content element", false);
      return this;
    }
		if(b) {
		  org.junit.Assert.assertTrue("Can see content", contentInput.getText().contains(content));
		} else {
		  org.junit.Assert.assertFalse("Can see content", contentInput.getText().contains(content));
		}
    return this;
  }

  public PostEditPo submitButtonDisabled(boolean b) {
    WebElement submit = driver.findElement(By.id("editpost_submit_input"));
    String attribute = submit.getAttribute("disabled");
    if(b) {
      org.junit.Assert.assertTrue("Submit button should be disabled", attribute.equals("true"));
    } else {
      org.junit.Assert.assertFalse("Submit button should be disabled", attribute.equals("true"));
    }
    return this;
  }

}
