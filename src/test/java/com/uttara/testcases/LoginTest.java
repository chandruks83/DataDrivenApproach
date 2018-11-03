package com.uttara.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.uttara.base.TestBase;

import org.openqa.selenium.By;

public class LoginTest extends TestBase{
	@Test
	public void loginAsBankManager() throws InterruptedException {
		log.debug("Inside manager login page");
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		Assert.assertTrue(isElementExists(By.cssSelector(OR.getProperty("addCustomer"))), "Login not successful");
	}
}
