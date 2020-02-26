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

public class ForgetPassword
{
	private WebDriver driver;
	private Global globalObj;
	private Properties prop;
	private boolean uName;
	private boolean email;
	private List<WebElement> list;
	private List<WebElement> elementList;
	private List<WebElement> finalList;
	private List<WebElement> allLinks;

	@FindBy(how = How.LINK_TEXT, using = "Forgot Password?")
	private WebElement forgotPassword;

	@FindBy(how = How.XPATH, using = ".//div[@id='forgot_password_dialog']//input[@id='fp_user_name']")
	private WebElement forgotPasswordUsername;

	@FindBy(how = How.XPATH, using = ".//div[@id='forgot_password_dialog']//input[@id='fp_user_mail']")
	private WebElement forgotPasswordEmail;

	@FindBy(how = How.XPATH, using = ".//div[@id='forgot_password_dialog']//input[@id='generate_pwd_button']")
	private WebElement forgotPasswordSubmit;

	@FindBy(how = How.XPATH, using = ".//div[@id='generate_success']")
	private WebElement successMessage;

	@FindBy(how = How.XPATH, using = ".//label[@for='fp_user_name']")
	private WebElement uNameLabel;

	@FindBy(how = How.XPATH, using = ".//label[@for='fp_user_mail']")
	private WebElement emailLabel;

	@FindBy(how = How.XPATH, using = ".//input[@id='generate_pwd_button']")
	private WebElement button;

	public ForgetPassword(WebDriver driver)
	{
		globalObj = new Global();
		this.driver = driver;
		PageFactory.initElements(driver, this);
		prop = globalObj.readProperties();
	}


	public void getURL()
	{
		list = driver.findElements(By.tagName("a"));
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println("URL => " + list.get(i).getText());
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

		// url = new URL("https://yahoo.com");

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


	public void clickForgotPassword()
	{
		forgotPassword.click();
	}


	public void forgotPassword()
	{
		forgotPasswordUsername.sendKeys(prop.getProperty("uname"));
		forgotPasswordEmail.sendKeys(prop.getProperty("Email"));
		forgotPasswordSubmit.click();
	}


	public void forgotPasswordInvalid()
	{
		forgotPasswordUsername.clear();
		forgotPasswordEmail.clear();
		forgotPasswordUsername.sendKeys(prop.getProperty("uname"));
		forgotPasswordEmail.sendKeys(prop.getProperty("Email2"));
		forgotPasswordSubmit.click();
	}


	public void forgotPasswordInvalid2()
	{
		forgotPasswordUsername.clear();
		forgotPasswordEmail.clear();
		forgotPasswordUsername.sendKeys("");
		forgotPasswordEmail.sendKeys("");
		forgotPasswordSubmit.click();
	}


	public void forgotPasswordInvalid3()
	{
		forgotPasswordUsername.clear();
		forgotPasswordEmail.clear();
		forgotPasswordUsername.sendKeys("");
		forgotPasswordEmail.sendKeys(prop.getProperty("Email"));
		forgotPasswordSubmit.click();

	}


	public void forgotPasswordInvalid4()
	{
		forgotPasswordUsername.clear();
		forgotPasswordEmail.clear();
		forgotPasswordUsername.sendKeys(prop.getProperty("uname"));
		forgotPasswordEmail.sendKeys("");
		forgotPasswordSubmit.click();
	}


	public void assertCheckValid()
	{
		globalObj.wait(driver).until(ExpectedConditions.textToBePresentInElementLocated(
				By.xpath(".//div[@id='generate_success']"), "Your request has been submitted."));
		Assert.assertEquals(successMessage.getText(), prop.getProperty("Message"));
	}


	public void assertCheckValid2()
	{
		globalObj.wait(driver)
				.until(ExpectedConditions.textToBePresentInElementLocated(
						By.xpath(".//div[@id='generate_success']"),
						"Provide both a User Name and an Email Address."));
		Assert.assertEquals(successMessage.getText(), prop.getProperty("Message2"));
	}


	public void assertCheckValid3()
	{
		globalObj.wait(driver).until(ExpectedConditions.textToBePresentInElementLocated(
				By.xpath(".//div[@id='generate_success']"), "Provide both a User Name and an Email Address."));
		Assert.assertEquals(successMessage.getText(), prop.getProperty("Message3"));
	}


	public void assertCheckValid4()
	{
		globalObj.wait(driver).until(ExpectedConditions.textToBePresentInElementLocated(
				By.xpath(".//div[@id='generate_success']"), "Provide both a User Name and an Email Address."));
		Assert.assertEquals(successMessage.getText(), prop.getProperty("Message3"));
	}


	public void assertCheckValid5()
	{
		globalObj.wait(driver).until(ExpectedConditions.textToBePresentInElementLocated(
				By.xpath(".//div[@id='generate_success']"), "Provide both a User Name and an Email Address."));
		Assert.assertEquals(successMessage.getText(), prop.getProperty("Message3"));
	}


	public void assertCheckboxCheck()
	{
		uName = forgotPasswordUsername.isEnabled();
		email = forgotPasswordEmail.isEnabled();
		Assert.assertEquals(uName, true);
		Assert.assertEquals(email, true);

	}


	public void labelCheck()
	{
		globalObj.wait(driver).until(ExpectedConditions.textToBePresentInElementValue(button, "Submit"));
		Assert.assertEquals(button.getAttribute("value"), prop.getProperty("Label4"));
		forgotPasswordSubmit.click();
		Assert.assertEquals(button.getAttribute("value"), prop.getProperty("Label3"));
	}


	public void closeBrowser()
	{
		driver.close();
	}
}
