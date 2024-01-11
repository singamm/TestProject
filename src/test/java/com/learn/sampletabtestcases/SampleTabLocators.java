package com.learn.sampletabtestcases;

import org.openqa.selenium.By;

import com.learn.commonactions.Locator;

public interface SampleTabLocators {
	
	Locator mobileTab = new Locator(By.xpath("//a[text()='Mobiles'][@class='nav-a  ']"), "mobiles tab");
	Locator mobileTabCheck = new Locator(By.cssSelector("[class='a-tab-heading a-active htw-tab-heading htw-tab-active']"), "check mobiles tab");
	
	Locator electronicsTab = new Locator(By.xpath("//a[text()=' Electronics '][@class='nav-a  ']"), "electronics tab");
	Locator electronicsTabCheck = new Locator(By.xpath("//span[contains(@class,'a-text-bold')][text()='Electronics']"), "check electronics tab");

}
