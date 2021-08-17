package com.utilities;

import java.util.ArrayList;

import org.testng.annotations.AfterTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports {
	static ArrayList<ArrayList<String>> data = TestData.getSheet();
	Constants c = new Constants();
	static ExtentReports extent, extent1;
	static ExtentTest Parent, tc02_compo, Parent1;
	static String details;
	



	public static void logStart()
	{
		extent = new ExtentReports(".\\Reports\\"+Constants.sheetname+"\\"+Constants.fos+".html", false);

		Parent = extent.startTest("URL Validation");
		extent.flush();
	}


	public static void Pass(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.PASS, details, actualResult);
	}

	public static void Fail(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.FAIL, details, actualResult);
	}

	public static void Info(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.INFO, details, actualResult);
	}

	public static void Skip(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.SKIP, details, actualResult);
	}

	public static void Fatal(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.FATAL, details);
	}

	public static void Error(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.ERROR, details, actualResult);
	}

	public static void Warning(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.WARNING, details, actualResult);
	}

	public static void Unknown(String details, String actualResult)
	{
		Reports.details=details;
		Parent.log(LogStatus.UNKNOWN, details, actualResult);
	}

	/* public static void screenshot(String description,String time) 
	  {
		 String image =logger.addScreenCapture("C:\\Users\\venkataesan.t\\Desktop\\testing\\testing\\Screenshot\\bg"+time+".png");
		 logger.log(LogStatus.FAIL, description+image);
	  }*/

	@AfterTest
	public static void after(){

		extent.endTest(Parent);
		extent.flush();
	}

}
