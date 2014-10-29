package org.denevell.natch.web.jerseymvc.uitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.denevell.natch.web.jerseymvc.uitests.utils.SeleniumDriverUtils;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class A404Tests {

  private WebDriver driver;

  @Before
  public void setup() throws Exception {
    driver = SeleniumDriverUtils.getDriver();
    driver.get(URLs.HOMEPAGE);
  }

  @After
  public void destroy() {
    driver.quit();
  }

  // Not an useless test since we've got the server got into a stackoverflow
  // before...
  @Test
  public void shouldSee404() throws InterruptedException {
    try {
      HttpURLConnection.setFollowRedirects(false);
      HttpURLConnection con = (HttpURLConnection) new URL(URLs.HOMEPAGE
          + "/dsfsdf").openConnection();
      con.setRequestMethod("HEAD");
      int responseCode = con.getResponseCode();
      boolean a404 = (responseCode == HttpURLConnection.HTTP_NOT_FOUND);
      assertTrue("We get a 404 on a bad url", a404);
    } catch (Exception e) {
      e.printStackTrace();
      assertFalse("Couldn't check 404", true);
    }
  }

}
