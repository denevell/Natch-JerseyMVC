package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

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
		org.junit.Assert.assertTrue(text.contains(string));
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
       	add("xxx");
      	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("xxx");
       	add("second page");
       	return this;
	}

}
