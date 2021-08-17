package com.utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestData {
	
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;

	public static String propFileName, linkCount, startVal, endVal;
	InputStream inputStream;
	
		
 public static ArrayList<ArrayList<String>> getSheet() {
			
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			
		try {
				FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+"\\testdata\\New folder\\Test_Data.xlsx"));
				workbook = new XSSFWorkbook(fis);
				 
				sheet = workbook.getSheetAt(0);	
				
				// get the number of rows
				int rowCount = sheet.getLastRowNum();
			
				// get the number of columns
				int columnCount = sheet.getRow(0).getLastCellNum();
				
				// loop through the rows
				for(int i=0; i <rowCount+1; i++){
				try {
						XSSFRow row = sheet.getRow(i);
						ArrayList<String> record = new ArrayList<String>();

						for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
								record.add(row.getCell(j).getStringCellValue()); // add to the record
								
							} catch (Exception e)  {
								
							
								e.printStackTrace();
							}				
					}
					data.add(record);
				
					} 
				catch (Exception e) {
						
						e.printStackTrace();
					}
				
				}
				
			} catch (IOException e) {
				
				 String details=e.getMessage();
				 Reports.Warning(details, " ");
			}
		
	
			return data;
		}	

  
}
