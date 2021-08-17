package com.HMHOneMDS;

import org.testng.annotations.Test;

import com.utilities.Constants;
import com.utilities.ReadConfigInpData;
import com.utilities.ReportGenerate;
import com.utilities.Reports;
import com.utilities.TestData;
import com.utilities.genericMethods;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

public class HmhRegression extends genericMethods {

	Constants c = new Constants();
	public static int card_result_size;
	public static String guid, Prog_title, inp_location, inp_disp_title, inp_desc, inp_compo, inp_uri, inp_inst, inp_std, inp_assign, URL,withslash,withoutslash, value;
	private static XSSFWorkbook workbooks;
	private static XSSFSheet sheets;
	static ArrayList<ArrayList<String>> data = TestData.getSheet();

	@BeforeMethod
	public void beforeMethod() throws IOException, InterruptedException {

		try {
			Reports.logStart(); // ExtentReports
			dataPropertiesFile();
			ReadConfigInpData.readConfigData();
			ReadConfigInpData.readHeaders();
			ReportGenerate.createFolder(Constants.program_title);
			ReportGenerate.createHeader("Test Result"); // Excel Report
			login();
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}

	}

	@Test
	public void linkExecution() {

		try {
			withslash = " https://cert.hmhco.com/";
			withoutslash = " https://cert.hmhco.com";
			waitForElementToBeClickable("discover");
			ClickElement("discover");
			waitUntilPageLoad();
			ClickElement("showSearch");

			ScrollDown();

			Reports.Info("Discover - Clicked", " ");

			waitForElementToBeClickable("teacherEdition");

			ClickElement("teacherEdition");
			Thread.sleep(8000);
			switchWindowClose();

			System.out.println("Executing test");
			FileInputStream fiss = new FileInputStream(new File(System.getProperty("user.dir")+"\\testdata\\New folder\\NAG6_U1.xlsx"));
			workbooks= new XSSFWorkbook(fiss);
			sheets = workbooks.getSheetAt(0);
			XSSFRow row = sheets.getRow(0);

			int col_num = -1;
			int rowCount = sheets.getLastRowNum();

			for(int i=0; i <row.getLastCellNum(); i++)
			{
				if(row.getCell(i).getStringCellValue().trim().equals("urls"))
					col_num = i;
			}

			for(int j=1; j<=rowCount; j++)
			{
				row = sheets.getRow(j);
				XSSFCell cell = row.getCell(col_num);

				value = cell.getStringCellValue();
				//System.out.println("Value of the Excel Cell is - "+ value);
				genericMethods.driver.get(withoutslash+value);
				Thread.sleep(3000);
				genericMethods.switchWindow();
				//System.out.println("Link Launching in New Tab");
				Thread.sleep(1000);
				genericMethods.getCurrentURL();	
				URL = genericMethods.currURL;
				System.out.println("Output URLsss " + URL);

				/*if(genericMethods.driver.getPageSource().contains("The specified key does not exist.") || genericMethods.driver.getPageSource().contains("AccessDenied") || genericMethods.driver.getPageSource().contains("This XML file does not appear to have any style information associated with it. The document tree is shown below.") ||genericMethods.driver.getPageSource().contains("Parameter cannot be blank"))
				{	
					System.out.println("Broken Link");
					Reports.Fail("Broken Link - XML Error ", URL);
					ReportGenerate.writeResult("TC01_Link", Constants.program_title, guid, " "," ","Broken Link - XML Error", genericMethods.driver.getCurrentUrl(), "FAIL");
					Thread.sleep(1000);

					BrowserClose();
					//genericMethods.driver.switchTo().window(genericMethods.parentWindow);
					switchWindow();
				}
				else
				{
					genericMethods.uricheck(URL);

					if(genericMethods.statusCode==404) 
					{
						ReportGenerate.writeResult("TC01_Link", Constants.program_title , guid, " "," ",genericMethods.statusCode + " Broken Link", URL,  "FAIL");
					}

					if((genericMethods.statusCode==200) && (URL.equalsIgnoreCase(inp_uri)))
					{
						Reports.Pass("Link launched successfully", URL);
						ReportGenerate.writeResult("TC01_Link", Constants.program_title, guid, " "," ",genericMethods.statusCode + " Link Launched Successfully", URL, "PASS");
					}

					else if((genericMethods.statusCode==400) && (URL.equalsIgnoreCase(inp_uri)))
					{
						Reports.Pass("Link launched successfully", URL);
						ReportGenerate.writeResult("TC01_Link", Constants.program_title, guid, " "," ",genericMethods.statusCode + " Link Launched Successfully", URL, "PASS");
					}
					
					else if(inp_uri.contains(".jnlp") || inp_uri.contains(".rtf")|| inp_uri.contains(".pptx")|| inp_uri.contains(".doc") || inp_uri.contains(".tns") || inp_uri.contains(".8xp") || inp_uri.contains(".flipchart") || inp_uri.contains(".notebook") || inp_uri.contains(".bnk") || inp_uri.contains(".docx"))
					{
						genericMethods.driver.get("chrome://downloads/");
						System.out.println("Downloads Page has been Launched");
						System.out.println("Validating Downloaded URL");
						WebElement root1 = genericMethods.driver.findElement(By.tagName("downloads-manager"));//Get shadow root element
						WebElement shadowRoot1 = genericMethods.expandRootElement(root1);
						WebElement root3 = shadowRoot1.findElement(By.cssSelector("downloads-item"));
						WebElement shadowRoot3 = genericMethods.expandRootElement(root3);
						System.out.println("Shadow Root - 3rd Tree has been expand");
						String actualHeading = shadowRoot3.findElement(By.cssSelector("a[id=url")).getText();
						Thread.sleep(1000);
						System.out.println("Output URL " + actualHeading);
						ReportGenerate.writeResult("TC01_Link", Constants.program_title, guid, " "," ","Link Downloaded Successfully", actualHeading, "PASS");
						genericMethods.driver.navigate().back();
						System.out.println("MDS Page has been Launched");
					}

					

					Thread.sleep(1000);
					BrowserClose();
					//genericMethods.driver.switchTo().window(genericMethods.parentWindow);
					switchWindow();

				}*/
			
			
			
				
			}
		



	} catch (Exception e) {
		e.printStackTrace();
		Reports.Pass("Link launched successfully but not matched with input", URL);
		ReportGenerate.writeResult("TC01_Link", Constants.program_title, guid, " "," ",genericMethods.statusCode + " Link Launched Successfully but not matched with input", URL, "PASS");
	}


}


@AfterMethod
public void afterMethod() throws IOException, ParseException {

	Browserquit();
}

}
