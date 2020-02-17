package com.filta.qa.login_page;

import org.testng.Assert;

import com.filta.qa.util.Global;

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

public class LoginPage
{
	private WebDriver driver;
	private Properties prop;
	private Global globalObj;
	private List<WebElement> list;
	private ArrayList<String> handles;
	private ArrayList<String> handles2;
	private List<WebElement> elementList;
	private List<WebElement> finalList;
	private List<WebElement> allLinks;

	@FindBy(how = How.XPATH, using = ".//select[@name='login_language']")
	private WebElement language;

	@FindBy(how = How.XPATH, using = ".//div[@id='footer']")
	private WebElement footer;

	@FindBy(how = How.XPATH, using = ".//div[@class='login']//tbody//tr[3]//td")
	private WebElement textMessage;

	@FindBy(how = How.XPATH, using = ".//a[@href='LICENSE.txt' and text()='AGPLv3']")
	private WebElement link1;

	@FindBy(how = How.XPATH, using = ".//a[@href='LICENSE.txt' and text()=' GNU Affero General Public License version 3']")
	private WebElement link2;

	@FindBy(how = How.XPATH, using = ".//input[@id='user_name']")
	private WebElement uName;

	@FindBy(how = How.XPATH, using = ".//input[@id='user_password']")
	private WebElement pWord;

	@FindBy(how = How.XPATH, using = ".//label[@for='user_name']")
	private WebElement uNameLabel;

	@FindBy(how = How.XPATH, using = ".//label[@for='user_password']")
	private WebElement pWordLabel;

//	@FindBy(how = How.XPATH, using = ".//tbody//tr[7]//td[@scope='row']")
//	private WebElement languageLabel;

	@FindBy(how = How.XPATH, using = ".//input[@title='Log In']")
	private WebElement loginLabel;

	public LoginPage(WebDriver driver)
	{
		globalObj = new Global();
		this.driver = driver;
		prop = globalObj.readProperties();
		PageFactory.initElements(driver, this);
	}


	public void dropDown()
	{

	}
//	public void dropDown()
//	{
//		list = driver.findElements(By.xpath(".//select[@name='login_language']//option"));
//		for (int i = 0; i < list.size(); i++)
//		{
//			System.out.println(list.get(i).getText());
//		}
//		Assert.assertEquals(list.get(0).getText(), prop.getProperty("english"));
//		Assert.assertEquals(list.get(1).getText(), prop.getProperty("german"));
//		Assert.assertEquals(list.size(), Integer.parseInt(prop.getProperty("LanguageSize")));
//		Assert.assertEquals(globalObj.select(language).getFirstSelectedOption().getText(),
//				prop.getProperty("english"));
//	}


	public void checkText()
	{
		if (footer.getText().startsWith("Server response time") && footer.getText().endsWith(
				"All other company and product names may be trademarks of the respective companies with which they are associated."))
		{
			System.out.println("Footer Test Pass");
		}
	}


	public void checkText2()
	{

		Assert.assertEquals(textMessage.getText(), prop.getProperty("TextLoginPage"));
	}


	public void checkURL()
	{
		link1.click();
		handles = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(handles.get(1));
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("CheckURL1"));
		closeBrowser();
		driver.switchTo().window(handles.get(0));
		link2.click();
		handles2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(handles2.get(1));
		Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("CheckURL2"));
		closeBrowser();
		driver.switchTo().window(handles2.get(0));

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


	public void checkTextBoxAssert()
	{
		Assert.assertEquals(uName.isEnabled(), true);
		Assert.assertEquals(pWord.isEnabled(), true);
	}


	public void checkTextBoxLabel()
	{
		Assert.assertEquals(uNameLabel.getText(), prop.getProperty("LoginUnameLabel"));
		Assert.assertEquals(pWordLabel.getText(), prop.getProperty("LoginPwordLabel"));
	//	Assert.assertEquals(languageLabel.getText(), prop.getProperty("LoginLanguageLabel"));
		Assert.assertEquals(loginLabel.getAttribute("value"), prop.getProperty("LoginLabel"));
	}


	public void closeBrowser()
	{
		driver.close();
	}

}
