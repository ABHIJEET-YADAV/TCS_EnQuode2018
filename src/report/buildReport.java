package report;

import library.GenerateReport;

public class buildReport {

	//Run this separately in order to create final output report in access database just after executing Testng.xml
	public static void main(String[]args)
	{
		
		try {
		GenerateReport object = new GenerateReport();
		object.readExcelData();
	   } 
		catch (Exception e) {
		e.printStackTrace();
	   }
	}
	
}
