package com.learn.commonactions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class CommonActions {
	
	public WebDriver driver;
	public ExtentReports extentReport;
	public ExtentSparkReporter sparkReporter;
	
	public CommonActions(WebDriver driver, ExtentReports extentReport){
		this.driver = driver;
		this.extentReport = extentReport;
	}
	
	@SuppressWarnings("deprecation")
	public WebDriver getFirefoxDriver() {
		try {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.manager.focusWhenStarting", false);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", true);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.helperApps.neverAsk.openFile", "application/xml,application/msword,application/csv,application/pdf,application/zip,application/x-x509-ca-cert,application/octet-stream,application/vnd.ms-excel,text/xml,text/csv,text/html,text/plain,image/png,image/jpeg");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xml,application/msword,application/csv,application/pdf,application/zip,application/x-x509-ca-cert,application/octet-stream,application/vnd.ms-excel,text/xml,text/csv,text/html,text/plain,image/png,image/jpeg");

			FirefoxOptions capabilities = new FirefoxOptions();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, capabilities.getBrowserName());
			capabilities.setCapability(CapabilityType.BROWSER_VERSION, capabilities.getBrowserVersion());
			capabilities.setProfile(profile);

			driver = new RemoteWebDriver(new URL(Constants.URL), capabilities);

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			driver.get(Constants.server_URL);
			
			Thread.sleep(5000);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	@SuppressWarnings("deprecation")
	public WebDriver getChromeDriver() {
		try {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("start-maximized");
			chromeOptions.setCapability(CapabilityType.BROWSER_NAME, chromeOptions.getBrowserName());
			chromeOptions.setCapability(CapabilityType.BROWSER_VERSION, chromeOptions.getBrowserVersion());
			
			driver = new RemoteWebDriver(new URL(Constants.URL), chromeOptions);
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			driver.get(Constants.server_URL);
			
			Thread.sleep(5000);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public void quitDriver() {
		try {
			driver.quit();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ExtentReports launchExtentReport() {
		sparkReporter = new ExtentSparkReporter(Constants.reportPath);
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Automation Report");
		
		extentReport = new ExtentReports();
		extentReport.attachReporter(sparkReporter);
		
		return extentReport;
	}
	
	public String getScreenshotPath(String testcaseName, WebDriver driver) throws IOException {
		String imageName = testcaseName + getCurrentDateAndTime();
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(Constants.screenshotPath + imageName + ".png"));
		return Constants.serverScreenshotPath + imageName + ".png";
	}
	
	public String getCurrentDateAndTime() {
		DateFormat dateFormat = null;
		Date date = null;
		try {
			dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
			date = new Date();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return dateFormat.format(date);
	}
	
	public ExtentTest createTest(String testCaseName, String testCaseDesc, String author) {
		ExtentTest testCase = null;
		try {
			testCase = extentReport.createTest(testCaseName, testCaseDesc);
			testCase.assignAuthor(author);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return testCase;
	}
	
	public void testCaseFailed(String details, ExtentTest extentTest) {
		try {
			String screenShotPath = takeScreenshot();
			extentTest.fail(details);
			extentTest.addScreenCaptureFromPath(screenShotPath);
			extentReport.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testCasePassed(String details, ExtentTest extentTest) {
		try {
			extentTest.pass(details);
			extentReport.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String takeScreenshot() {
		try {
			String dateTime = getCurrentDateAndTime();
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(Constants.screenshotPath + "\\" + dateTime + ".png"));
			return Constants.screenshotPath + "\\" + dateTime + ".png";
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void flushExtent() {
		try {
			System.out.println("extent report flushed.");
			extentReport.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void click(Locator locator, ExtentTest extentTest) {
		try {
			Thread.sleep(500);
			extentTest.info("click on " + locator.details);
			driver.findElement(locator.by).click();
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in click method.");
		}
	}
	
	public void sendKeys(Locator locator, String value, ExtentTest extentTest) {
		try {
			Thread.sleep(500);
			extentTest.info("send values to the " + locator.details);
			driver.findElement(locator.by).clear();
			driver.findElement(locator.by).sendKeys(value);
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in sendkeys method.");
		}
	}
	
	public boolean isAlertPresent(ExtentTest extentTest) {
		boolean foundAlert = false;
		try {
			extentTest.info("Check alert present or not.");
			Thread.sleep(500);
			driver.switchTo().alert();
			foundAlert = true;
		} 
		catch (NoAlertPresentException e) {
			foundAlert = false;
		}
		catch (InterruptedException e) {
			foundAlert = false;
		}
		return foundAlert;
	}
	
	public boolean isAlertPresentWait() {
		boolean foundAlert = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} 
		catch (TimeoutException e) {
			foundAlert = false;
		}
		return foundAlert;
	}
	
	public void acceptAlert(ExtentTest extentTest) {
		try {
			if(isAlertPresent(extentTest)) {
				extentTest.info("accept the alert.");
				driver.switchTo().alert().accept();
			}
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in accept alert method.");
		}
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
            return true;
		} 
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementPresent(Locator locator) {
		try {
			driver.findElement(locator.by);
            return true;
		} 
		catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementPresent(WebElement element) {
		boolean displayed = false;
		try {
			displayed = element.isDisplayed();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return displayed;
	}
	
	public boolean isTextPresent(String text) {
		boolean result = false;
		try {
			String pageText = driver.findElement(By.tagName("body")).getText();
			result = pageText.contains(text) ? true : false;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void addExceptionDetails(Exception exception, ExtentTest extentTest, String details) {
		try {
			extentTest.fail(exception);
			testCaseFailed(details, extentTest);
			exception.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
