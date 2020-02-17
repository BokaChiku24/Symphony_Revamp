package com.filta.qa.login_test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.filta.qa.login_page.LoginPage;
import com.filta.qa.util.Global;

public class LogInPage_TestCase
{
	private WebDriver driver;
	private LoginPage loginPageObj;
	private Global globalObj;
	private ExtentReports extent;
	private ExtentHtmlReporter htmlReporter;
	private ExtentTest extentLogger;
	private String screenshotPath;

	public static Logger log = Logger.getLogger("LogInPage Test Case");
	static
	{
		PropertyConfigurator.configure(".//Log4j.properties");
	}

	@BeforeClass
	public void property()
	{
		globalObj = new Global();
		driver = globalObj.driver();
		loginPageObj = new LoginPage(driver);
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/Symphony_Reports/HomePage/LogInPage_TestCase.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows 7 64 Bit");
		extent.setSystemInfo("Browser", "Google Chrome");
		extent.setSystemInfo("Author:", "Kunal Chavan");
		extent.setSystemInfo("Testing:", "Functional Testing");
		htmlReporter.config().setReportName("Login Page Functionality Test Case");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a'('zzz')'");
		htmlReporter.loadXMLConfig("./extent-config.xml");
	}


	@Test
	public void checktext()
	{
		log.info("Test Case 1: Text Check On Login Page");
		extentLogger = extent.createTest("Test Case 1: Text Check On Login Page");
		loginPageObj.checkText2();
	}


	@Test(priority = 1)
	public void checkDropDown()
	{
		log.info("Test Case 2: Drop Down Check On Login Page");
		extentLogger = extent.createTest("Test Case 2: Drop Down Check On Login Page");
		loginPageObj.dropDown();
	}


	@Test(priority = 2)
	public void footerCheck()
	{
		log.info("Test Case 3: Footer Check On Login Page");
		extentLogger = extent.createTest("Test Case 3: Footer Check On Login Page");
		loginPageObj.checkText();
	}


	@Test(priority = 3)
	public void checkURL()
	{
		log.info("Test Case 4: URL Check On Login Page");
		extentLogger = extent.createTest("Test Case 4: URL Check On Login Page");
		loginPageObj.checkURL();
	}


	@Test(priority = 4)
	public void checkTextBox()
	{
		log.info("Test Case 5: Check Login Page TextBox Enabled Or Not");
		extentLogger = extent.createTest("Test Case 5: Check Login Page TextBox Enabled Or Not");
		loginPageObj.checkTextBoxAssert();
	}


	@Test(priority = 5)
	public void checkLabel()
	{
		log.info("Test Case 6: Check Login Page TextBox Label");
		extentLogger = extent.createTest("Test Case 6: Check Login Page TextBox Label");
		loginPageObj.checkTextBoxLabel();
	}


	@Test(priority = 6)
	public void availableLinks()
	{
		log.info("Test Case 7: Get All Login URL");
		extentLogger = extent.createTest("Test Case 7: Get All Login URL");
		loginPageObj.availableLinks();
	}


	@Test(priority = 7)
	public void getBrokenLink()
	{
		log.info("Test Case 8: Get All Broken URL");
		extentLogger = extent.createTest("Test Case 8: Get All Broken URL");
		loginPageObj.brokenLink();
	}


	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException
	{
		if (testResult.getStatus() == ITestResult.FAILURE)
		{
			extentLogger.log(Status.FAIL,
					MarkupHelper.createLabel(testResult.getName() + " - Test Case Failed", ExtentColor.RED));
			extentLogger.log(Status.FAIL,
					MarkupHelper.createLabel(testResult.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			String dateName = new SimpleDateFormat("dd MMMM yyyy zzzz").format(new Date());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			screenshotPath = System.getProperty("user.dir") + "/LoginPage_errorScreenshots/" + testResult.getName()
					+ dateName + "_" + Arrays.toString(testResult.getParameters()) + ".png";
			FileUtils.copyFile(scrFile, new File(screenshotPath));
			extentLogger.fail(
					"Test Case Failed Snapshot Is Below " + extentLogger.addScreenCaptureFromPath(screenshotPath));
		}
		else if (testResult.getStatus() == ITestResult.SKIP)
		{
			extentLogger.log(Status.SKIP,
					MarkupHelper.createLabel(testResult.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		}
		else if (testResult.getStatus() == ITestResult.SUCCESS)
		{
			extentLogger.log(Status.PASS,
					MarkupHelper.createLabel(testResult.getName() + " Test Case Passed", ExtentColor.GREEN));
		}

	}


	@AfterClass
	public void closeBrowser()
	{
		log.info("Login Page Test Case Ends Here");
		extent.flush();
		loginPageObj.closeBrowser();
	}
}
