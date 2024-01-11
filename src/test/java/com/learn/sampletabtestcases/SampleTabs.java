package com.learn.sampletabtestcases;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.learn.commonactions.CommonActions;

public class SampleTabs extends CommonActions implements SampleTabLocators {
	
	public WebDriver driver;
	public ExtentReports extentReport;
	
	public SampleTabs(WebDriver driver, ExtentReports extentReport) {
		super(driver, extentReport);
		this.driver = driver;
		this.extentReport = extentReport;
	}
	
	public void openMobilesTab() {
		ExtentTest extentTest = createTest("open mobiles tab", "open mobiles tab and check", "Narasingam");
		try {
			click(mobileTab, extentTest);
			
			if(isElementPresent(mobileTabCheck)) {
				testCasePassed("mobile tab open successfully.", extentTest);
			}
			else {
				testCaseFailed("mobile tab not opened, kindly check.", extentTest);
			}
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in open mobiles tab method");
		}
	}
	
	public void openElectronicsTab() {
		ExtentTest extentTest = createTest("open electronics tab", "open electronics tab and check", "Narasingam");
		try {
			click(electronicsTab, extentTest);
			
			if(isElementPresent(electronicsTabCheck)) {
				testCasePassed("electronics tab open successfully.", extentTest);
			}
			else {
				testCaseFailed("electronics tab not opened, kindly check.", extentTest);
			}
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in open electronics tab method");
		}
	}

}
