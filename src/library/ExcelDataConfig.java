package library;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDataConfig {
	
	XSSFWorkbook wb;
	XSSFSheet sheet1;
	
	public ExcelDataConfig() {
		try
		{
			// Specify the path of file
			File src = new File("F:\\Automation\\enquode2018\\TestReport\\testReport.xlsx");
			// load file
			FileInputStream fis = new FileInputStream(src);
			// Load workbook
			wb = new XSSFWorkbook(fis);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	public Object[][] getData(int sheetNumber) {
		// Load sheet
		sheet1 = wb.getSheetAt(sheetNumber);
		int row = wb.getSheetAt(sheetNumber).getLastRowNum();
		int column = 5;
		Object[][] data = new Object[row+1][column+1];
		DataFormatter df = new DataFormatter();
		for(int i=0 ; i<=row ; i++) {
			data[i][0] = df.formatCellValue(sheet1.getRow(i).getCell(0)).toString();
			data[i][1] = df.formatCellValue(sheet1.getRow(i).getCell(1)).toString();
			data[i][2] = df.formatCellValue(sheet1.getRow(i).getCell(2)).toString();
			data[i][3] = df.formatCellValue(sheet1.getRow(i).getCell(3)).toString();
			data[i][4] = df.formatCellValue(sheet1.getRow(i).getCell(4)).toString();
			data[i][5] = df.formatCellValue(sheet1.getRow(i).getCell(5)).toString();
		}
		return data;
	}
	
	
	//Count no. of rows in the excel sheet
	public int getRowCount(int sheetIndex) {
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		return row;
	}
	
}
