package com.uttara.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
	/*
	 * WebDriver
	 * Properties
	 * Logs Log4j jar, .log, Logger, log4j.properties
	 * ExtentReports
	 * DB
	 * Excel
	 * Mail
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	
	public static Logger log = Logger.getLogger("devpinoyLogger");

	@BeforeMethod
	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			String userDir = System.getProperty("user.dir");
			String browser="";
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
				System.setProperty("webdriver.gecko.driver", userDir + "//src//test//resources//executables//geckodriver.exe");
				driver = new FirefoxDriver();
			}
			if (browser.equalsIgnoreCase("ie")) {
				log.debug("Navigate to ie browser");
				System.setProperty("webdriver.gecko.driver", userDir + "//src//test//resources//executables//MicrosoftWebDriver.exe");
				driver = new InternetExplorerDriver();
			}
			if (browser.equalsIgnoreCase("chrome")) {
				log.debug("Navigate to chrome browser");
				System.setProperty("webdriver.chrome.driver", userDir + "//src//test//resources//executables//chromedriver.exe");
				driver = new ChromeDriver();
			}
			
			log.debug("Load url");
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(implicitWait), TimeUnit.SECONDS);
		}
	}

	@AfterMethod
	@AfterSuite
	public void tearDown() {
		log.debug("Close browser");
		driver.quit();
	}
}
