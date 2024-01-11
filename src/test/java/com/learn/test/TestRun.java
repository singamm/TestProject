package com.learn.test;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.learn.commonactions.CommonActions;
import com.learn.commonactions.Constants;

public class TestRun {
	
	public static WebDriver driver = null;
	public static ExtentReports extentReport;
	
	public static void main(String[] args) {
		
		Constants.reportPath = "C:\\Users\\NarasingamMPK\\Documents\\Automation\\Local_Automation_Report\\Report.html";
		Constants.browserName = "firefox";
		Constants.server_URL = "https://www.amazon.in/";
		
		CommonActions ca = new CommonActions(driver, extentReport);
		
		driver = ca.getFirefoxDriver();
		extentReport = ca.launchExtentReport();
		
		
		extentReport.flush();
		
		driver.quit();
	}

}
