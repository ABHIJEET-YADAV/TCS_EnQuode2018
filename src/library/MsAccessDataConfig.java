package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//MS Access database handling
public class MsAccessDataConfig {
	Statement s;
	ResultSet rs;
	Connection conn;
	int count= 0 ;
	//Constructor for creating connection
	public MsAccessDataConfig(String dbPath) {
		try {  
			 conn=DriverManager.getConnection(dbPath);
				s = conn.createStatement();
			}
		catch(Exception e){
				System.out.println(e);
				}  
	}
	
	
	//count no. of data set in the access database in order to read it
	public int getRowCount(String tableName) throws SQLException {		
		rs = s.executeQuery("SELECT * from "+tableName);
		ResultSet rs1 = s.executeQuery("SELECT * from "+tableName);
		while(rs1.next())
		   {  
			   count++;  
		   }  
		return count;
	}
	
	
	//Return a 2d array object which contain input data to the data provider
	public Object[][] getData(int noOfColumn) throws SQLException {
	
		Object[][] data = new Object[1][noOfColumn];
		rs.next();
		int startIndex=2;
		for(int i=0;i<noOfColumn;i++) {
			data[0][i]=rs.getString(startIndex);
			startIndex++;
		}
		startIndex=2;
		return data;
	}

	
	//Create and insert data into the finalReport table in Acces Database
	public void createOutputReport(Object[][] data, int noOfRows) throws SQLException {
		try {
			 s.execute("DROP TABLE finalReport");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		s.execute("create table finalReport ( Testcase_Name varchar(100), Status varchar(100), Exception varchar(250), Start_Time varchar(50), End_Time varchar(45), Duration varchar(50))");
		for(int i=1 ; i<=noOfRows ; i++) {
		    if(data[i][2]=="") {
		    	data[i][2]="N.A";
		    }
			s.execute("insert into finalReport values('"+data[i][0]+"','"+data[i][1]+"','"+data[i][2]+"','"+data[i][3]+"','"+data[i][4]+"','"+data[i][5]+"')");
		}
		System.out.println("Database Created");
        s.close();
        conn.close();
	}
	
}
