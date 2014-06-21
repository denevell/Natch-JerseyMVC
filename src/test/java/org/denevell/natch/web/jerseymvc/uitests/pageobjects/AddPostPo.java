package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.denevell.natch.web.jerseymvc.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddPostPo {

	private WebDriver driver;

	public AddPostPo(WebDriver driver) {
		this.driver = driver;
	}

	public AddPostPo add(String content) {
        WebElement content_input = driver.findElement(By.id("post_content_input"));
        WebElement addinput_input = driver.findElement(By.id("addpost_form_submit"));
        content_input.clear();
        content_input.sendKeys(content);
        addinput_input.click();
        return this;
	}

	public AddPostPo hasPost(int i, String string) {
		WebElement thread = driver.findElement(By.id("post_" + i));
        WebElement title = thread.findElement(By.id("post_content"));
        String text = title.getText();
		org.junit.Assert.assertTrue("Should have post", text.contains(string));
		return this;
	}

	public AddPostPo hasAuthor(int i, String string) {
		WebElement thread = driver.findElement(By.id("post_" + i));
        WebElement author = thread.findElement(By.id("post_author"));
        String text = author.getText();
		org.junit.Assert.assertTrue(text.contains(string));
		return this;
	}

	public AddPostPo hasInputError() {
        boolean inputError= driver.getPageSource().contains(Strings.getPostFieldsCannotBeBlank());
        org.junit.Assert.assertTrue(inputError);
		return this;
	}

	public AddPostPo  submitButtonIsDisabled() {
        WebElement submit = driver.findElement(By.id("addpost_form_submit"));
        String attribute = submit.getAttribute("disabled");
		org.junit.Assert.assertTrue("Submit button should be disabled", attribute.equals("true"));
		return this;
	}

	public AddPostPo hasPleaseLogonToAddPost() {
        String source = driver.getPageSource();
        org.junit.Assert.assertTrue("please login to add post text", source.contains("please login"));
        return this;
	}

	public AddPostPo addPagePlusOneOfPosts() {
       	add("c0");
      	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("c10");
       	return this;
	}

	public AddPostPo cantSeePost(String string) {
		org.junit.Assert.assertFalse(driver.getPageSource().contains(string));
		return this;
	}

	public AddPostPo hasCorrectPageTitle(String string) {
        WebElement subject = driver.findElement(By.id("thread_subject"));
        org.junit.Assert.assertEquals("Has title", string, subject.getText().trim()); 
		return this;
	}

	public AddPostPo clickPrev() {
        driver.findElement(By.id("prev")).click();;
		return this;
	}

	public AddPostPo hasTodaysDate(int i) {
        Calendar c = Calendar.getInstance();
        int dom = c.get(Calendar.DAY_OF_MONTH);
        String month = new SimpleDateFormat("MMM").format(c.getTime());
        int year = c.get(Calendar.YEAR);
        String dateString = dom + " " + month + " " + year;
		WebElement thread = driver.findElement(By.id("post_" + i));
        WebElement author = thread.findElement(By.id("post_date"));
        String text = author.getText();
        org.junit.Assert.assertTrue("Has today's date", text.contains(dateString)); 
		return this;
	}

	public AddPostPo clickOnPageLink(String string) {
        driver.findElement(By.id("page"+string)).click();;
		return this;
	}

	public AddPostPo isOnPageViaUrl(int page) {
		String uri = driver.getCurrentUrl();
		page--;
		org.junit.Assert.assertTrue("Url is for correct page", uri.contains("start="+(page*10)));
		return this;
	}

	public AddPostPo clickNext() {
        driver.findElement(By.id("next")).click();;
		return this;
	}

	public AddPostPo clickOnSinglePost(int i) {
		List<WebElement> singlePostLink = driver.findElements(By.linkText("link"));
		singlePostLink.get(1).click();
		return this;
	}

	public AddPostPo hasPostInSinglePage(String string) {
        Pattern p = Pattern.compile("<em>.*cont1.*</em>", Pattern.DOTALL);
        Matcher m = p.matcher(driver.getPageSource());
        org.junit.Assert.assertTrue("Can see single post with markdown", m.find());		
        return null;
		
	}

	public AddPostPo clickOnParentThreadFromSinglePostPage() {
        WebElement parentThreadLink = driver.findElement(By.id("parent-thread-link"));
        parentThreadLink.click();
		return null;
	}

	public AddPostPo hasPostWithMarkdown(int i, String string) {
		WebElement thread = driver.findElement(By.id("post_" + i));
        WebElement title = thread.findElement(By.id("post_content"));
        String text = title.getText();
        Pattern p = Pattern.compile("<em>.*cont1.*</em>", Pattern.DOTALL);
        Matcher m = p.matcher(text);
        org.junit.Assert.assertTrue("Can see single post in larger thread with markdown", m.find());		
        return null;
	}

}
