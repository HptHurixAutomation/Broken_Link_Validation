package com.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class ReportGenerate {

	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet;

	private static ExtentReports extent;
	private static ExtentTest Parent;
	public static String screenshot;
	public static String screenText = "Above screenshot shows status as: ";
	private static XSSFRow row = null;
	private static XSSFCell cell = null;
	private static XSSFFont font = null;

	@BeforeTest

	public static void createFolder(String progTitle) throws InterruptedException, IOException {
		try {
			File file = new File(System.getProperty("user.dir") + "\\Reports\\" + Constants.sheetname);
			if (!file.exists())

			{
				file.mkdir();
				System.out.println("Folder Created");

			} else {
				System.out.println("Folder Exists");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test

	public static void createHeader(String sheetName) throws InterruptedException, IOException {
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
			row = sheet.createRow(0);

			row.createCell(0).setCellValue("Test Case ID");
			row.createCell(1).setCellValue("Title");
			row.createCell(2).setCellValue("GUID");
			row.createCell(3).setCellValue("Component Name");
			row.createCell(4).setCellValue("Display Title");
			row.createCell(5).setCellValue("Description");
			row.createCell(6).setCellValue("Actual Value");
			row.createCell(7).setCellValue("Result");

			flushWorkbook();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void writeResult(String id, String progtitle, String guid, String componentname, String displaytitle, String desc, String actual, String result) {

		try {

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			//	style.setFillPattern(CellStyle.SOLID_FOREGROUND);


			font = workbook.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);

			CellStyle style1 = workbook.createCellStyle();
			style1.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			//	style1.setFillPattern(CellStyle.SOLID_FOREGROUND);

			row = sheet.createRow(sheet.getLastRowNum() + 1);

			row.createCell(0).setCellValue(id);
			row.createCell(1).setCellValue(progtitle);
			row.createCell(2).setCellValue(guid);
			row.createCell(3).setCellValue(componentname);
			row.createCell(4).setCellValue(displaytitle);
			row.createCell(5).setCellValue(desc);
			row.createCell(6).setCellValue(actual);

			cell = row.createCell(7);

			if (result.equalsIgnoreCase("Fail")) {
				cell.setCellValue(result);
				style.setFont(font);
				cell.setCellStyle(style);
			} else if (result.equalsIgnoreCase("Warning")) {
				cell.setCellValue(result);
				style1.setFont(font);
				cell.setCellStyle(style1);
			}
			cell.setCellValue(result);

			flushWorkbook();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	static void flushWorkbook() throws IOException, ParseException {

		FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Reports\\" + Constants.sheetname + "\\" + Constants.fos + ".xlsx"));
		workbook.write(fos);
		fos.flush();
	}

	/*public static void logStart()
	{
		extent = new ExtentReports(".\\Reports\\HtmlReport.html", false);
		Parent = extent.startTest("Validate broken Links");
		extent.flush();
	}*/
	
	public static void logStart()
	{
		extent = new ExtentReports(".\\Reports\\HtmlReport.html", false);

		Parent = extent.startTest("Broken links Validation");
		extent.flush();
	}


	public static void Pass(String details, String actualResult) throws IOException, InterruptedException
	{
		Reports.details=details;
		Parent.log(LogStatus.PASS, details, actualResult);

		Parent.log(LogStatus.PASS,Parent.addScreenCapture(genericMethods.addScreenCapture()) + screenText + " Test Pass");

	}

	public static void Fail(String details, String actualResult) throws IOException, InterruptedException
	{
		Reports.details=details;
		Parent.log(LogStatus.FAIL, details, actualResult);
		Parent.log(LogStatus.FAIL,Parent.addScreenCapture(genericMethods.addScreenCapture()) + screenText + " Test Fail");
	}

	public static void Info(String details, String actualResult) throws IOException, InterruptedException
	{
		Reports.details=details;
		Parent.log(LogStatus.INFO, details, actualResult);
	}

	public static void Skip(String details, String actualResult) throws IOException, InterruptedException
	{
		Reports.details=details;
		Parent.log(LogStatus.SKIP, details, actualResult);
		Parent.log(LogStatus.SKIP,Parent.addScreenCapture(genericMethods.addScreenCapture()) + screenText +" Test skipped");
	}

	public static void Fatal(String details, String actualResult, String screenshot)
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

	public static void closeExtentReport(){

		extent.endTest(Parent);
		extent.flush();
	}

}
