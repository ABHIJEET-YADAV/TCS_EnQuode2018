package library;

import java.sql.SQLException;

import org.automationtesting.excelreport.Xl;

public class GenerateReport {
	//Generate final excel report 
	public GenerateReport() throws Exception{		
		Xl.generateReport("F:\\Automation\\enquode2018\\TestReport", "testReport.xlsx");
		Thread.sleep(5000);
	}
	
	
	//Create and Insert final report data in  Access database 
	public void readExcelData() throws SQLException {
		ExcelDataConfig dataObject = new ExcelDataConfig();
		int noOfRows= dataObject.getRowCount(1);
		Object[][] data = dataObject.getData(1);
		MsAccessDataConfig config = new MsAccessDataConfig("jdbc:ucanaccess://F:\\Automation\\enquode2018\\TestReport\\outputReport.accdb");	
		config.createOutputReport(data, noOfRows);
	}

}