package com.learn.samplesearchtestcases;

import org.openqa.selenium.By;

import com.learn.commonactions.Locator;

public interface SampleSearchLocators {
	
	Locator searchBox = new Locator(By.id("twotabsearchtextbox"), "main search box");
	Locator submitSearch = new Locator(By.id("nav-search-submit-button"), "submit search icon");

}
