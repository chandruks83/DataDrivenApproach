package com.uttara.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.uttara.utilities.ExcelReader;

public class TestBase {
	/*
	 * WebDriver Properties Logs Log4j jar, .log, Logger, log4j.properties
	 * ExtentReports DB Excel Mail
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;

	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;

	@BeforeMethod
	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			String userDir = System.getProperty("user.dir");
			String browser = "";
			String url = "";
			String implicitWait = "";
			try {
				fis = new FileInputStream(userDir + "\\src\\test\\resources\\properties\\Config.properties");
				log.debug("Load config file");
				config.load(fis);
				browser = config.getProperty("Browser");
				url = config.getProperty("url");
				fis = new FileInputStream(userDir + "\\src\\test\\resources\\properties\\OR.properties");
				log.debug("Load OR page");
				OR.load(fis);
				implicitWait = config.getProperty("implicitwait");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (browser.equalsIgnoreCase("firefox")) {
				log.debug("Navigate to firfox browser");
				System.setProperty("webdriver.gecko.driver",
						userDir + "//src//test//resources//executables//geckodriver.exe");
				driver = new FirefoxDriver();
			}
			if (browser.equalsIgnoreCase("ie")) {
				log.debug("Navigate to ie browser");
				System.setProperty("webdriver.gecko.driver",
						userDir + "//src//test//resources//executables//MicrosoftWebDriver.exe");
				driver = new InternetExplorerDriver();
			}
			if (browser.equalsIgnoreCase("chrome")) {
				log.debug("Navigate to chrome browser");
				System.setProperty("webdriver.chrome.driver",
						userDir + "//src//test//resources//executables//chromedriver.exe");
				driver = new ChromeDriver();
			}

			log.debug("Load url");
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(implicitWait), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}
	}
	
	static WebElement dropDown;
	
	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")){
			dropDown = driver.findElement(By.cssSelector(OR.getProperty(locator)));	
		}
		if (locator.endsWith("_XPATH")){
			dropDown = driver.findElement(By.xpath(OR.getProperty(locator)));	
		}
		if (locator.endsWith("_ID")){
			dropDown = driver.findElement(By.id(OR.getProperty(locator)));	
		}
		
		Select select = new Select(dropDown);
		select.selectByVisibleText(value);
	}

	public boolean isElementExists(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@AfterMethod
	@AfterSuite
	public void tearDown() {
		log.debug("Close browser");
		driver.quit();
	}
}
