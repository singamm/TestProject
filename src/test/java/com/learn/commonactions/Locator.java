package com.learn.commonactions;

import org.openqa.selenium.By;

public class Locator {
	
	public By by;
	public String details;
	
	public Locator(By by, String details) {
		this.by = by;
		this.details = details;
	}
}
