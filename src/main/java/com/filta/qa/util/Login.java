package com.filta.qa.util;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Login
{
	public WebDriver driver;
	private Global globalObj;
	private Properties prop;

	@FindBy(how = How.XPATH, using = ".//input[@id='user_name']")
	private WebElement userName;

	@FindBy(how = How.XPATH, using = ".//input[@id='user_password']")
	private WebElement passWord;

	@FindBy(how = How.XPATH, using = ".//input[@id='login_button']")
	private WebElement logIn;


	public Login(WebDriver driver)
	{
		this.driver = driver;
		globalObj = new Global();
		prop = globalObj.readProperties();
		PageFactory.initElements(driver, this);

	}


	public void credentials()
	{
		userName.sendKeys(prop.getProperty("uname"));
		passWord.sendKeys(prop.getProperty("password"));
		logIn.click();
	}


	public void adminCredentials()
	{
		userName.sendKeys(prop.getProperty("adminuname"));
		passWord.sendKeys(prop.getProperty("adminpassword"));
		logIn.click();
	}
}
