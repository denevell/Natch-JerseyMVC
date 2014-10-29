package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPo {

  private WebDriver driver;

  public RegisterPo(WebDriver driver) {
    this.driver = driver;
  }

  public RegisterPo registerFromElsewhere(String username, String password, String recoveryEmail) {
    driver.findElement(By.id("reg_link")).click();
    register(username, password, recoveryEmail);
    return this;
  }

  public RegisterPo registerFromHomepage(String username, String password, String recoveryEmail) {
    driver.get(URLs.HOMEPAGE);
    driver.findElement(By.id("reg_link")).click();
    register(username, password, recoveryEmail);
    return this;
  }

  public RegisterPo register(String username, String password, String recoveryEmail) {
    WebElement username_input = driver.findElement(By
        .id("register_username_input"));
    WebElement password_input = driver.findElement(By
        .id("register_password_input"));
    WebElement recovery_input = driver.findElement(By
        .id("register_recovery_input"));
    WebElement submit_input = driver.findElement(By.id("register_form_submit"));
    username_input.clear();
    username_input.sendKeys(username);
    password_input.clear();
    password_input.sendKeys(password);
    recovery_input.clear();
    recovery_input.sendKeys(recoveryEmail);
    submit_input.click();
    return this;
  }

  public RegisterPo shouldBeOnHomepage() {
    String currentUrl = driver.getCurrentUrl();
    org.junit.Assert.assertTrue(currentUrl.startsWith(URLs.HOMEPAGE));
    return this;
  }

  public RegisterPo shouldSeeRegisterFormSubmit() {
    org.junit.Assert.assertTrue("Should see register link", driver
        .getPageSource().toLowerCase().contains("register_form_submit"));
    return this;
  }

  public RegisterPo shouldntSeeRegisterFormSubmit() {
    org.junit.Assert.assertFalse("Shouldn't see register link", driver
        .getPageSource().toLowerCase().contains("register_form_submit"));
    return this;
  }

  public RegisterPo shouldSeeUsernameAlreadyExists() {
    String pageSource = driver.getPageSource();
    org.junit.Assert.assertTrue("Shouldnt see reg error",
        pageSource.contains("Username already exists"));
    return this;
  }

  public RegisterPo shouldSeeBlanksError(boolean b) {
    String pageSource = driver.getPageSource();
    if(b) {
      org.junit.Assert.assertTrue("Should see reg error",
          pageSource.contains("cannot be blank"));
    } else {
      org.junit.Assert.assertFalse("Shouldn't see reg error",
          pageSource.contains("cannot be blank"));
    }
    return this;
  }

  public RegisterPo shouldSeeRegisterLinkText(boolean b) {
    try {
      driver.findElement(By.id("reg_link")).click();
      if(!b) {
        org.junit.Assert.assertFalse("Didn't want to see register link text", true);
      }
    } catch (Exception e) {
      if(b) {
        org.junit.Assert.assertFalse("Wanted to see register link text", true);
      }
    }
    return this;
  }

}
