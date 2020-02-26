package com.filta.qa.login_page;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.filta.qa.util.Global;

public class Login
{
	private WebDriver driver;
	private Global globalObj;
	private Properties prop;
	private String actualMsg;
	private List<WebElement> list;
	private List<WebElement> elementList;
	private List<WebElement> finalList;
	private List<WebElement> allLinks;

	@FindBy(how = How.XPATH, using = ".//input[@id='user_name']")
	private WebElement userName;

	@FindBy(how = How.XPATH, using = ".//input[@id='username_password']")
	private WebElement passWord;

	@FindBy(how = How.XPATH, using = ".//input[@id='bigbutton']")
	private WebElement logIn;

	@FindBy(how = How.XPATH, using = ".//span[@class='error' and text()='You must specify a valid username and password.']")
	private WebElement errorMessage;

	public Login(WebDriver driver)
	{
		globalObj = new Global();
		this.driver = driver;
		PageFactory.initElements(driver, this);
		prop = globalObj.readProperties();
	}


	public void availableLinks()
	{
		list = driver.findElements(By.tagName("a"));
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println("URL Name => " + list.get(i).getText());
		}
	}


	public List<WebElement> findAllLinks(WebDriver driver)

	{

		elementList = driver.findElements(By.tagName("a"));

		elementList.addAll(driver.findElements(By.tagName("img")));

		finalList = new ArrayList<WebElement>();

		for (WebElement element : elementList)

		{

			if (element.getAttribute("href") != null)

			{

				finalList.add(element);

			}

		}

		return finalList;

	}


	public static String isLinkBroken(URL url) throws Exception

	{

		String response = "";

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try

		{

			connection.connect();

			response = connection.getResponseMessage();

			connection.disconnect();

			return response;

		}

		catch (Exception exp)

		{

			return exp.getMessage();

		}

	}


	public void brokenLink()
	{
		allLinks = findAllLinks(driver);

		System.out.println("Total Number Of Elements Found " + allLinks.size());

		for (WebElement element : allLinks)
		{

			try

			{

				System.out.println("URL: " + element.getAttribute("href") + " Returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))));

				// System.out.println("URL: " + element.getAttribute("outerhtml")+ " returned "
				// + isLinkBroken(new URL(element.getAttribute("href"))));

			}

			catch (Exception exp)

			{

				System.out.println("At " + element.getAttribute("innerHTML") + " Exception Occured -&gt; "
						+ exp.getMessage());

			}

		}

	}


	public void enterUserName(String userName)
	{
		this.userName.sendKeys(userName);
	}


	public void enterPassword(String password_String)
	{
		passWord.sendKeys(password_String);
	}


	public void clickSubmit()
	{
		logIn.click();
	}


	public void assertCheck()
	{
		actualMsg = driver.getCurrentUrl();
		Assert.assertEquals(actualMsg, prop.getProperty("URLAssert"));
	}


	public void assertCheck2()
	{
		globalObj.wait(driver).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(".//span[@class='error' and text()='You must specify a valid username and password.']")));
		actualMsg = errorMessage.getText();
		Assert.assertEquals(actualMsg, prop.getProperty("Validation1"));
	}


	public void clearText()
	{
		userName.clear();
		passWord.clear();
	}


	public void driverClose()
	{
		driver.close();
	}
}
