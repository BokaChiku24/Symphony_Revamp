package com.filta.qa.login_test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.filta.qa.login_page.Login;
import com.filta.qa.util.Global;
import com.filta.qa.util.ReadExcelData;

public class LogIn_TestCase
{

	private WebDriver driver;
	private Login login;
	private Global globalObj;
	private Properties prop;
	private ExtentReports extent;
	private ExtentHtmlReporter htmlReporter;
	private ExtentTest extentLogger;
	private ReadExcelData data;
	private String[][] array;
	private String screenshotPath;
	private String dateName;
	private File scrFile;
	private int totalRows;
	private int totalColumn;

	public static Logger log = Logger.getLogger("Sign In Test Case");
	static
	{
		PropertyConfigurator.configure(".//Log4j.properties");
	}

	@BeforeClass
	public void beforeClass()
	{
		globalObj = new Global();
		driver = globalObj.driver();
		prop = globalObj.readProperties();
		login = new Login(driver);
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/Symphony_Reports/HomePage/Login_TestCase.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows 10 64 Bit");
		extent.setSystemInfo("Browser", "Google Chrome");
		extent.setSystemInfo("Author:", "Kunal Chavan");
		extent.setSystemInfo("Testing:", "Functional Testing");
		htmlReporter.config().setReportName("Login Functionality Test Case");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a'('zzz')'");
		htmlReporter.loadXMLConfig("./extent-config.xml");

	}


	@DataProvider(name = "getData")
	public String[][] getDataFromExcel()
	{
		data = new ReadExcelData(prop.getProperty("Path"), "Valid");
		totalRows = data.getTotalRows();
		totalColumn = data.getTotalColumns();
		array = new String[totalRows][totalColumn];
		array[0][0] = data.getCellData(1, 0);
		array[0][1] = data.getCellData(1, 1);
		return array;
	}


	@Test(dataProvider = "getData")
	public void signInTesting(String uName, String passWord)
	{
		log.info("Test Case 1: Check Login Functionality With Valid Credentials And Avaliable Links Name");
		extentLogger = extent.createTest(
				"Test Case 1: Check Login Functionality With Valid Credentials And Avaliable Links Name");
		login.availableLinks();
		login.brokenLink();
		login.enterUserName(uName);
		login.enterPassword(passWord);
		login.clickSubmit();
		login.assertCheck();
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
			dateName = new SimpleDateFormat("dd MMMM yyyy zzzz").format(new Date());
			scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			screenshotPath = System.getProperty("user.dir") + "/Login_errorScreenshots/" + testResult.getName()
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
	public void afterClass()
	{
		log.info("Sign In Test Case Ends Here");
		extent.flush();
		login.driverClose();
	}
}
