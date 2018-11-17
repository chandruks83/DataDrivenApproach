package com.uttara.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uttara.base.TestBase;

public class AddCustomerTest extends TestBase{
	
	@Test(dataProvider="getData")
	public void addCustomer(String firstName, String lastName, String postCode, String alertText){
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("addCustomer"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("firstName"))).sendKeys(firstName);
		driver.findElement(By.cssSelector(OR.getProperty("lastName"))).sendKeys(lastName);
		driver.findElement(By.cssSelector(OR.getProperty("postalCode"))).sendKeys(postCode);
		driver.findElement(By.cssSelector(OR.getProperty("submitButton"))).click();
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(alertText));
		alert.accept();
		Reporter.log("Customer " + firstName + " " + lastName + " created successfully");
	}
	
	@DataProvider
	public Object[][] getData(){
		String sheetName = "AddCustomerTest";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		log.debug("rows = " + rows);
		log.debug("cols = " + cols);
		Object[][] data = new Object[rows-1][cols];
		for(int rowNum = 2; rowNum<=rows; rowNum++ ) {
			for(int colNum = 0; colNum<cols; colNum++ ) {
				data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}
}
