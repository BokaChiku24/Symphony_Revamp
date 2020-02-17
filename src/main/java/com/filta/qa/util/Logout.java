package com.filta.qa.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Logout
{
	public WebDriver driver;

	@FindBy(how = How.XPATH, using = ".//div[@class='user']//a")
	private WebElement logOut;

	public Logout(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	public void logout()
	{
		logOut.click();
	}
}
