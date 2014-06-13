package org.denevell.natch.web.jerseymvc.uitests.pageobjects;

import org.denevell.natch.web.jerseymvc.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddThreadPo {
	
	private WebDriver driver;

	public AddThreadPo(WebDriver driver) {
		this.driver = driver;
	}
	
	public AddThreadPo add(String subject, String content, String tags) {
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

	public AddThreadPo hasTitle(int i, String string) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement title = thread.findElement(By.id("thread_subject"));
        org.junit.Assert.assertTrue(title.getText().equals(string));
		return this;
	}

	public AddThreadPo hasAuthor(int i, String string) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement author = thread.findElement(By.id("thread_author"));
        org.junit.Assert.assertTrue(author.getText().equals(string));
		return this;
	}

	public AddThreadPo showsInputError() {
        boolean inputError= driver.getPageSource().contains(Strings.getPostFieldsCannotBeBlank());
        org.junit.Assert.assertTrue(inputError);
		return this;
	}

	public void gotoThread(int i) {
		WebElement thread = driver.findElement(By.id("thread_" + i));
        WebElement link = thread.findElement(By.id("thread_subject"));
        link.click();
		
	}

}
