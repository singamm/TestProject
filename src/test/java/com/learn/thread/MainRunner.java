package com.learn.thread;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.learn.commonactions.CommonActions;
import com.learn.commonactions.Constants;
import com.learn.jdbc.DatabaseActions;

public class MainRunner {
	
	public static ExtentReports extentReport;
	public static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		
		DatabaseActions da = new DatabaseActions();
		da.beforeDatabaseActions();
		
		CommonActions ca = new CommonActions(driver, extentReport);
		extentReport = ca.launchExtentReport();
		
		Thread thread = null;
		
		for(int i = 1; i <= Constants.threadCount; i++) {
			thread = new MainThread(Constants.browserName + i, extentReport);
			System.out.println("Thread Going to Start..");
			thread.start();
			Thread.sleep(5000);
		}
	}
}
