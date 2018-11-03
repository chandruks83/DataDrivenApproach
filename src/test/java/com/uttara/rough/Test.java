package com.uttara.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test {
	public static WebDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException {
		Properties config = new Properties();
		Properties OR = new Properties();
		String userDir = System.getProperty("user.dir");
		System.setProperty("webdriver.gecko.driver", userDir + "\\src\\test\\resources\\executables\\geckodriver.exe");
		FileInputStream fis = new FileInputStream(userDir + "\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis);
		String url = config.getProperty("url");
		fis = new FileInputStream(userDir + "\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		driver = new FirefoxDriver();
		driver.get(url);
		String bmlBtn = OR.getProperty("bmlBtn");
		driver.findElement(By.cssSelector(bmlBtn)).click();
		driver.findElement(By.cssSelector("[ng-click='addCust()']")).click();
		driver.findElement(By.cssSelector("[ng-model='fName']")).sendKeys("fname");
		driver.findElement(By.cssSelector("[ng-model='lName']")).sendKeys("lname");
		driver.findElement(By.cssSelector("[ng-model='postCd']")).sendKeys("123");
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("[type='submit']")).click();
		driver.quit();
	}
}
