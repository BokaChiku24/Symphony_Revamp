package com.filta.qa.login_test;

/**
 * @author Kunal
 *
 * Forgot Password Test Cases
 */

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
import com.filta.qa.login_page.ForgetPassword;
import com.filta.qa.util.Global;

public class ForgetPassword_TestCase
{
	private WebDriver driver;
	private ForgetPassword forgotPassword;
	private Global globalObj;
	private ExtentReports extent;
	private ExtentHtmlReporter htmlReporter;
	private ExtentTest extentLogger;
	private String screenshotPath;
	private String dateName;
	private File scrFile;

	public static Logger log = Logger.getLogger("Forget Password Test Case");
	static
	{
		PropertyConfigurator.configure(".//Log4j.properties");
	}

	@BeforeClass
	public void beforeClass()
	{
		globalObj = new Global();
		driver = globalObj.driver();
		forgotPassword = new ForgetPassword(driver);
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/Symphony_Revamp_Reports/HomePage/ForgetPassword_TestCase.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows 7 64 Bit");
		extent.setSystemInfo("Browser", "Google Chrome");
		extent.setSystemInfo("Author:", "Kunal Chavan");
		extent.setSystemInfo("Testing:", "Functional Testing");
		htmlReporter.config().setReportName("Forgot assword Functionality Test case");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a'('zzz')'");
		htmlReporter.loadXMLConfig("./extent-config.xml");
	}


	@Test(priority = 0)
	public void checkLink()
	{
		log.info("Test Case 0: Get All URL Names");
		extentLogger = extent.createTest("Test Case 0: Get All URL Names");
		forgotPassword.getURL();
	}


	@Test(priority = 0, dependsOnMethods = "checkLink")
	public void brokenLink()
	{
		log.info("Test Case 0: Get All Broken URL Names");
		extentLogger = extent.createTest("Test Case 0: Get All Broken URL Names");
		forgotPassword.brokenLink();
	}


	@Test(dependsOnMethods = "checkLink")
	public void forgotPassWord()
	{
		log.info("Test Case 1: Forgot Password Check With Valid Username And Email");
		extentLogger = extent.createTest("Test Case 1: Forgot Password Check With Valid Username And Email");
		forgotPassword.clickForgotPassword();
		forgotPassword.forgotPassword();
		forgotPassword.assertCheckValid();
	}


	@Test(priority = 1)
	public void forgotPassWordInvalid()
	{
		log.info("Test Case 2: Forgot Password Check With Invalid Username And Email");
		extentLogger = extent.createTest("Test Case 2: Forgot Password Check With Invalid Username And Email");
		forgotPassword.forgotPasswordInvalid();
		forgotPassword.assertCheckValid2();
	}


	@Test(priority = 2)
	public void forgotPassWordInvalid2()
	{
		log.info("Test Case 3: Forgot Password Check Without Username And Email");
		extentLogger = extent.createTest("Test Case 3: Forgot Password Check Without Username And Email");
		forgotPassword.forgotPasswordInvalid2();
		forgotPassword.assertCheckValid3();
	}


	@Test(priority = 3)
	public void forgotPassWordInvalid3()
	{
		log.info("Test Case 4: Forgot Password Check Without Username And With Email");
		extentLogger = extent.createTest("Test Case 4: Forgot Password Check Without Username And With Email");
		forgotPassword.forgotPasswordInvalid3();
		forgotPassword.assertCheckValid4();
	}


	@Test(priority = 4)
	public void forgotPassWordInvalid4()
	{
		log.info("Test Case 5: Forgot Password Check With Username And Without Email");
		extentLogger = extent.createTest("Test Case 5: Forgot Password Check With Username And Without Email");
		forgotPassword.forgotPasswordInvalid4();
		forgotPassword.assertCheckValid5();
	}


	@Test(priority = 5)
	public void textboxCheck()
	{
		log.info("Test Case 6: Forgot Password Textbox Check");
		extentLogger = extent.createTest("Test Case 6: Forgot Password Textbox Check");
		forgotPassword.assertCheckboxCheck();
	}


	@Test(priority = 6)
	public void labelCheck()
	{
		log.info("Test Case 7: Forgot Password Label Check");
		extentLogger = extent.createTest("Test Case 7: Forgot Password Label Check");
		forgotPassword.labelCheck();
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
			screenshotPath = System.getProperty("user.dir") + "/ForgotPassword_errorScreenshots/"
					+ testResult.getName() + dateName + "_" + Arrays.toString(testResult.getParameters()) + ".png";
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
					MarkupHelper.createLabel(testResult.getName() + " - Test Case Passed", ExtentColor.GREEN));
		}
	}


	@AfterClass
	public void afterClass()
	{
		log.info("Forgot Password Test Case Ends Here");
		extent.flush();
		forgotPassword.closeBrowser();
	}
}
