package com.uttara.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uttara.base.TestBase;
import com.uttara.utilities.TestUtil;

public class AddCustomerTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alertText){
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("addCustomer_CSS"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("firstName_CSS"))).sendKeys(firstName);
		driver.findElement(By.cssSelector(OR.getProperty("lastName_CSS"))).sendKeys(lastName);
		driver.findElement(By.cssSelector(OR.getProperty("postalCode_CSS"))).sendKeys(postCode);
		driver.findElement(By.cssSelector(OR.getProperty("submitButton_CSS"))).click();
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertText));
		alert.accept();
		Reporter.log("Customer " + firstName + " " + lastName + " created successfully");
	}
}
