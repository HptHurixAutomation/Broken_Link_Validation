package com.utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadConfigInpData extends Constants{
	
	public static String propFileName;
	public static InputStream fileInput;
	static String linkCount;
	public static String startVal;
	public static String endVal;
	public static int statusCode, start, end;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	ArrayList<ArrayList<String>> data = TestData.getSheet();
	Constants c = new Constants();
	
	
  public static void readConfigData() throws IOException {
	try {
			Properties properties = new Properties();
			propFileName = System.getProperty("user.dir")+"\\src\\test\\resources\\config\\data.properties";
	
			InputStream in = new FileInputStream(propFileName);
			properties.load(in);
			startVal = properties.getProperty("StartVal");
			endVal = properties.getProperty("EndVal");
			
		
	} catch (Exception e) {
		System.out.println("Exception: " + e);
	} 
  }
  
  
  public static void readHeaders() throws InterruptedException, IOException {
		try {
			
			start = Integer.parseInt(ReadConfigInpData.startVal);
			end = Integer.parseInt(ReadConfigInpData.endVal);
			FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "\\testdata\\New folder\\" + Constants.mds_filename));
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(Constants.sheetname);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
