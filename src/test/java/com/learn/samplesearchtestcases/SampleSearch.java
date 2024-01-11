package com.learn.samplesearchtestcases;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.learn.commonactions.CommonActions;

public class SampleSearch extends CommonActions implements SampleSearchLocators {
	
	public WebDriver driver;
	public ExtentReports extentReport;
	
	public SampleSearch(WebDriver driver, ExtentReports extentReport) {
		super(driver, extentReport);
		this.driver = driver;
		this.extentReport = extentReport;
	}
	
	public void searchSmartTV() {
		ExtentTest extentTest = createTest("search smart TV", "search smart TV and check the results", "Narasingam");
		try {
			sendKeys(searchBox, "smart tv", extentTest);
			click(submitSearch, extentTest);
			
			if(isTextPresent("Smart TV") || isTextPresent("TV")) {
				testCasePassed("smart tv's are listed after seaching.", extentTest);
			}
			else {
				testCaseFailed("smart tv's are not listed.", extentTest);
			}
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in search smartTV method");
		}
	}
	
	public void searchSamsungMobilePhones() {
		ExtentTest extentTest = createTest("search samsung mobile phones", "search samsung mobile phones and check the results", "Narasingam");
		try {
			sendKeys(searchBox, "Samsung mobile phones", extentTest);
			click(submitSearch, extentTest);
			
			if(isTextPresent("Samsung") || isTextPresent("samsung")) {
				testCasePassed("samsung mobiles are listed after seaching.", extentTest);
			}
			else {
				testCaseFailed("samsung mobiles are not listed.", extentTest);
			}
		} 
		catch (Exception e) {
			addExceptionDetails(e, extentTest, "There is an issue in search samsung mobile method");
		}
	}

}
