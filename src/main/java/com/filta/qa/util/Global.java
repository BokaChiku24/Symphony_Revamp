package com.filta.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Global
{
	private WebDriver driver;
	private TakesScreenshot screen;
	private WebElement element;
	private JavascriptExecutor jS;
	private Alert alertObj;
	private Select selectObj;
	private Actions action;
	private WebDriverWait wait;
	private File f;
	private File fileObj;
	private File file;
	private ChromeOptions options;
	private Properties prop;
	private FileInputStream input;
	private String text;
	private String message;
	private String url;
	private boolean result;
	private static int count = 1;
	private Map<String, Object> prefs;
	private DesiredCapabilities capablities;
	private String browser = "chrome";

	// Driver Initialization Method !!
	public WebDriver driver()
	{
		prop = readProperties();
		if (browser.equalsIgnoreCase(prop.getProperty("browserChrome")))
		{
			options = new ChromeOptions();
			options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			options.addArguments("--start-maximized");
			options.addArguments("--disable-web-security");
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			options.addArguments("--no-proxy-server");
			options.addArguments("disable-infobars");
			options.addArguments("--log-level=3");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("enable-automation");
			options.addArguments("--disable-extensions");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--aggressive-cache-discard"); 
			options.addArguments("--disable-cache"); 
			options.addArguments("--disable-application-cache"); 
			options.addArguments("--disable-offline-load-stale-cache"); 
			options.addArguments("--disk-cache-size=0");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--incognito");
			options.addArguments("--disable-features=VizDisplayCompositor");
			options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			options.addArguments("enable-features=NetworkServiceInProcess");
			options.addArguments("disable-features=NetworkService");
			options.addArguments("--force-device-scale-factor=1");
			prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			capablities = DesiredCapabilities.chrome();
			capablities.setCapability(ChromeOptions.CAPABILITY, options);
			capablities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ChromeDriverService service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(prop.getProperty("driverChrome"))).usingAnyFreePort().build();
			options.merge(capablities);
			System.setProperty("webdriver.chrome.driver", prop.getProperty("driverChrome"));
			driver = new ChromeDriver(service, options);
			driver.get(prop.getProperty("URL2"));
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
		else
		{
			FirefoxProfile profile = new FirefoxProfile();
			DesiredCapabilities dc = DesiredCapabilities.firefox();
			profile.setAcceptUntrustedCertificates(false);
			profile.setAssumeUntrustedCertificateIssuer(false);
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", "C:\\Downloads");
			profile.setPreference("browser.download.downloadDir", "C:\\Downloads");
			profile.setPreference("browser.download.defaultFolder", "C:\\Downloads");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/anytext ,text/plain,text/html,application/plain");
			dc = DesiredCapabilities.firefox();
			dc.setCapability(FirefoxDriver.PROFILE, profile);
			System.setProperty("webdriver.gecko.driver", prop.getProperty("driverFirefox"));
			driver = new FirefoxDriver();
			driver.get(prop.getProperty("URL2"));
			driver.manage().window().maximize();
		}
		return driver;
	}


	public Properties readProperties()
	{
		f = new File("E:\\Work\\Symphony_Revamp\\configs\\Configuration.properties");
		fileObj = new File("C:\\Users\\kunal\\git\\Symphony\\FiltaSymphony\\configs\\OfficeConfiguration.properties");
		file = new File(
				"C:\\Users\\Boka_Chiku\\git\\Symphony\\FiltaSymphony\\configs\\HomePC_Configuration.properties");
		if (f.exists() == true)
		{
			try
			{
				input = new FileInputStream(f);

			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prop = new Properties();
			try
			{
				prop.load(input);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return prop;
		}
		else if (fileObj.exists() == true)
		{

			try
			{
				input = new FileInputStream(fileObj);

			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prop = new Properties();
			try
			{
				prop.load(input);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return prop;
		}
		else if (file.exists() == true)
		{
			try
			{
				input = new FileInputStream(file);

			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prop = new Properties();
			try
			{
				prop.load(input);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return prop;

	}


	public void screenShot(WebDriver driver)
	{
		screen = (TakesScreenshot) driver;
		f = screen.getScreenshotAs(OutputType.FILE);
		try
		{
			FileUtils.copyFile(f, new File("E:\\Msite\\MobileApp\\ScreenShots\\Screen" + count + ".png"));
			count++;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public WebElement driverWait(WebDriver driver, String xPath)
	{
		wait = new WebDriverWait(driver, 30);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		return element;
	}


	public Select select(WebElement element)
	{
		selectObj = new Select(element);
		return selectObj;
	}


	public WebDriverWait wait(WebDriver driver)
	{
		wait = new WebDriverWait(driver, 2500);
		return wait;
	}


	public String getString(WebDriver driver, String xPath)
	{
		text = driver.findElement(By.xpath(xPath)).getText();
		return text;
	}


	public String getStringElement(WebElement element)
	{
		text = element.getText();
		return text;
	}


	public void click(WebDriver driver, String xPath)
	{
		driver.findElement(By.xpath(xPath)).click();
	}


	public void send(WebDriver driver, String xPath, String value)
	{
		driver.findElement(By.xpath(xPath)).sendKeys(value);
	}


	public void clickElement(WebElement element)
	{
		element.click();
	}


	public void sendElementKey(WebElement element, String text)
	{
		element.sendKeys(text);
	}


	public void sendElement(WebDriver driver, String xPath, String text)
	{
		driver.findElement(By.xpath(xPath)).sendKeys(text);
	}


	public void sleepMethod()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			System.out.println("We are in Catch Block !!");
			e.printStackTrace();
		}
	}


	public void implicityWait(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
	}


	public String url(WebDriver driver)
	{
		url = driver.getCurrentUrl();
		return url;
	}


	public boolean enabled(WebElement element)
	{
		result = element.isEnabled();
		return result;
	}


	public void linkText(WebDriver driver, String text)
	{
		driver.findElement(By.linkText(text)).click();
	}


	public WebElement webElementReturn(WebDriver driver, String xPath)
	{
		element = driver.findElement(By.xpath(xPath));
		return element;
	}


	public String getAttributes(WebElement element, String value)
	{
		text = element.getAttribute(value);
		return text;
	}


	public void clearText(WebDriver driver, String xPath)
	{
		driver.findElement(By.xpath(xPath)).clear();
	}


	public String alert(WebDriver driver)
	{
		alertObj = driver.switchTo().alert();
		message = alertObj.getText();
		alertObj.accept();
		return message;
	}


	public void alertAccept(WebDriver driver)
	{
		alertObj = driver.switchTo().alert();
		alertObj.accept();

	}


	public Actions action(WebDriver driver)
	{
		action = new Actions(driver);
		return action;
	}


	public Alert alertFunction(WebDriver driver)
	{
		alertObj = driver.switchTo().alert();
		return alertObj;
	}


	public String alert(String input, WebDriver driver)
	{
		alertObj = driver.switchTo().alert();
		message = alertObj.getText();
		if (input.equalsIgnoreCase("accept"))
			alertObj.accept();
		else if (input.equalsIgnoreCase("dismiss"))
			alertObj.dismiss();
		return message;
	}


	public JavascriptExecutor jsReturn(WebDriver driver)
	{
		jS = (JavascriptExecutor) driver;
		return jS;
	}

}
