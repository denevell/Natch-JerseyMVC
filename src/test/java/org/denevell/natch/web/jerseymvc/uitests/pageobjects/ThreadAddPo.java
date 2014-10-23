package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.denevell.natch.jerseymvc.app.utils.Strings;
import org.denevell.natch.web.jerseymvc.uitests.utils.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ThreadAddPo {
	
	private WebDriver driver;

	public ThreadAddPo(WebDriver driver) {
		this.driver = driver;
	}
	
	public ThreadAddPo add(String subject, String content, String tags) {
		WebElement subject_input = driver.findElement(By.id("threads_subject_input"));
        WebElement content_input = driver.findElement(By.id("threads_content_input"));
        WebElement tags_input = driver.findElement(By.id("threads_tags_input"));
        WebElement addinput_input = driver.findElement(By.id("addthread_form_submit"));
		subject_input.clear();
        subject_input.sendKeys(subject);
        content_input.clear();
        content_input.sendKeys(content);
        tags_input.clear();
        tags_input.sendKeys(tags);
        addinput_input.click();
        return this;
	}

	public ThreadAddPo hasTitle(int i, String string) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement title = thread.findElement(By.id("thread_subject"));
        org.junit.Assert.assertTrue(title.getText().equals(string));
		return this;
	}

	public ThreadAddPo hasAuthor(int i, String string) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement author = thread.findElement(By.id("thread_author"));
        org.junit.Assert.assertTrue(author.getText().equals(string));
		return this;
	}

	public ThreadAddPo showsInputError() {
        boolean inputError= driver.getPageSource().contains(Strings.getPostFieldsCannotBeBlank());
        org.junit.Assert.assertTrue(inputError);
		return this;
	}

	public ThreadAddPo gotoThread(int i) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement link = thread.findElement(By.id("thread_subject"));
        link.click();
        return this;
	}

	public ThreadAddPo hasPost(int i, String string) {
		return this;
	}

	public ThreadAddPo addPagePlusOneOfThreads() {
		add("suby0", "cont0", "tagx");
		add("suby1", "cont1", "tagx");
		add("suby2", "cont2", "tagx");
		add("suby3", "cont3", "tagx");
		add("suby4", "cont4", "tagx");
		add("suby5", "cont5", "tagx");
		add("suby6", "cont5", "tagx");
		add("suby7", "cont5", "tagx");
		add("suby8", "cont5", "tagx");
		add("suby9", "cont5", "tagx");
		add("suby10", "cont5", "tagx");
		return this;
		
	}

	public ThreadAddPo hasDate(int i, String dateString) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement date= thread.findElement(By.id("thread_date"));
        org.junit.Assert.assertTrue("Should see date", date.getText().contains(dateString));
        return this;
	}

	public ThreadAddPo hasTodaysDate(int i) {
        Calendar c = Calendar.getInstance();
        int dom = c.get(Calendar.DAY_OF_MONTH);
        String month = new SimpleDateFormat("MMM").format(c.getTime());
        int year = c.get(Calendar.YEAR);
        String dateString = dom + " " + month + " " + year;
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement author = thread.findElement(By.id("thread_date"));
        String text = author.getText();
        org.junit.Assert.assertTrue("Has today's date", text.contains(dateString)); 
        return this;
	}

	public ThreadAddPo clickOnPrev() {
        driver.findElement(By.id("prev")).click();;
		return this;
	}

	public ThreadAddPo clickOnNext() {
        driver.findElement(By.id("next")).click();;
		return this;
	}

	public ThreadAddPo amOnPageByUrl(int page) {
		String uri = driver.getCurrentUrl();
		page--;
		org.junit.Assert.assertTrue("Url is for correct page", uri.contains("start="+(page*10)));
		return this;
	}

	public ThreadAddPo amOnHomepage() {
		String uri = driver.getCurrentUrl();
		org.junit.Assert.assertTrue("Url is for correct page", uri.equals(URLs.HOMEPAGE+"/"));
		return this;
	}

	public ThreadAddPo clickOnPage(int page) {
        driver.findElement(By.id("page"+page)).click();
		return this;
	}

  public ThreadAddPo seeEmptyThreadsMessage() {
    boolean emptyText = driver.getPageSource().contains("No content as yet!");
    org.junit.Assert.assertTrue(emptyText);
    return this;
  }

  public ThreadAddPo hasTag(int i, String string, boolean b) {
    WebElement thread = driver.findElement(By.id("thread_" + i));
    WebElement tags = thread.findElement(By.id("tags"));
    if (b) {
      org.junit.Assert.assertTrue("Should see tag", tags.getText().contains(string));
    } else {
      org.junit.Assert.assertFalse("Shouldn't see tag", tags.getText().contains(string));
    }
    return this;
  }

  public ThreadAddPo numberOfTags(int threadNum, int tagsNum) {
    WebElement thread = driver.findElement(By.id("thread_" + threadNum));
    WebElement tags = thread.findElement(By.id("tags"));
    org.junit.Assert.assertEquals(tagsNum, tags.findElements(By.className("tag")).size());
    return this;
  }

  public ThreadAddPo hasTagInThreadPage(String string, boolean b) {
    WebElement tags = driver.findElement(By.id("tags"));
    if (b) {
      org.junit.Assert.assertTrue("Should see tag", tags.getText().contains(string));
    } else {
      org.junit.Assert.assertFalse("Shouldn't see tag", tags.getText().contains(string));
    }
    return this;
  }

  public ThreadAddPo numberOfTagsInThreadPage(int tagsNum) {
    WebElement tags = driver.findElement(By.id("tags"));
    org.junit.Assert.assertEquals(tagsNum, tags.findElements(By.className("tag")).size());
    return this;
  }

  public ThreadAddPo clickOnTag(String tagName) {
    driver.findElement(By.linkText(tagName)).click();
    return this;
  }

  public ThreadAddPo clickOnHeader() {
    driver.findElement(By.id("homelink")).click();
    return this;
    
  }

}
