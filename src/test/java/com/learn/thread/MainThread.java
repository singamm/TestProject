package com.learn.thread;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.learn.commonactions.CommonActions;
import com.learn.jdbc.DatabaseActions;

public class MainThread extends Thread {
	
	CommonActions ca;
	
	public WebDriver driver;
	public ExtentReports extentReport;
	
	public MainThread(String threadName, ExtentReports extentReport) {
		super(threadName);
		this.extentReport = extentReport;
		ca = new CommonActions(driver, extentReport);
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			driver = ca.getFirefoxDriver();
			
			DatabaseActions da = new DatabaseActions(driver, extentReport);
			da.runAutomationFromDatabase();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ca.quitDriver();
		}
	}

}
