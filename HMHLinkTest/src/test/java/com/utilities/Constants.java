package com.utilities;

import java.text.ParseException;
import java.util.ArrayList;


 public class Constants extends TestData{
	
	public static String URL, State, District, username, mds_version, password, browser, program_title, dropdown, mds_filename, start, end, tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, chromeProfile, sheetname, fos;
	
	ArrayList<ArrayList<String>> data = TestData.getSheet();
	
	public Constants() 
	{

		URL =  data.get(1).get(0);
		
		State = data.get(1).get(1);
		
		District = data.get(1).get(2);
	
		username = data.get(1).get(3);

		password = data.get(1).get(4);
		
		browser = data.get(1).get(5);

		program_title = data.get(1).get(6);
		
		dropdown = data.get(1).get(7);
		
		mds_version = data.get(1).get(8);
		
		mds_filename = data.get(1).get(9);
		
		sheetname = data.get(1).get(10);
		
		tc1 = data.get(1).get(11);
		
		tc2 = data.get(1).get(12);
		
		tc3 = data.get(1).get(13);
		
		tc4 = data.get(1).get(14);
		
		tc5 = data.get(1).get(15);
		
		tc6 = data.get(1).get(16);
		
		tc7 = data.get(1).get(17);
		
		
		
		try {
			fos = "Result_"+sheetname+"_"+genericMethods.simpleDate();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
		//chromeProfile = "C:\\Users\\komalavalli.n\\AppData\\Local\\Google\\Chrome\\User Data\\BackupDefault";
	}

}	

