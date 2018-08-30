package testClass;

import org.testng.annotations.Test;
import library.MsAccessDataConfig;
import pageObjects.RegisterAndLogin;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;


public class TestCases {
	
  WebDriver driver;
	
  
  //Runs before Every @Test
  @BeforeMethod
  public void beforeMethod() {
	  try {
		System.setProperty("webdriver.edge.driver", "F:\\Library\\MicrosoftWebDriver.exe");
		  driver = new EdgeDriver();
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  driver.manage().window().maximize();
		  driver.get("http://www.way2automation.com/demo.html");
	} catch (Exception e) {
		assertTrue(false, "Unable to start driver");
	}
  }
  
  
  //TestCase 1
  @Test(priority=1, dataProvider="registrationData")
  public void TestCaseRegistration(String name, String phone, String email, String country, String city, String username, String passward) throws Throwable {
	  RegisterAndLogin.newRegistration(driver, name, phone, email, country, city, username, passward);
	  }

  
  //TestCase 2
  @Test(priority=2, dataProvider="loginData")
  public void TestcaseLogin(String username , String passward) throws Throwable {

	  RegisterAndLogin.userLogin(driver, username , passward);
  }
  
  
  //TestCase 3
  @Test(priority=3, dataProvider="alertBoxData")
  public void TestcaseCheckAlertOption(String username , String passward, String inputAlertData ) throws Throwable {
	  RegisterAndLogin.checkAlert(driver, username, passward, inputAlertData);
  }


  //Runs after Every @Test
  @AfterMethod
  public void afterMethod(ITestResult result) {
	  
		// Here will compare if test is failing then only it will enter into if condition
		if(ITestResult.FAILURE==result.getStatus())
		{
		try 
		{
		// Create reference of TakesScreenshot
		TakesScreenshot ts=(TakesScreenshot)driver;
		 
		// Call method to capture screenshot
		File source=ts.getScreenshotAs(OutputType.FILE);
		 
		//In order to give unique name to every screenshot current date and time is used
		DateFormat dateFormat = new SimpleDateFormat("MM_DD_YYYY_HH_mm_ss");
	    Date date = new Date();
	    String date1= dateFormat.format(date);
	    // result.getName() will return name of test case so that screenshot name will be same
	    String screenshotPath = "./Screenshots/"+result.getName()+date1+".png";
		FileUtils.copyFile(source, new File(screenshotPath));
		 
		System.out.println("Screenshot taken");
		} 
		catch (Exception e)
		{
		System.out.println("Exception while taking screenshot "+e.getMessage());
		} 
		}
		  System.out.println("Quiting");
		  driver.quit();
	  }

  
  
  //Data Provider for TestCaseRegistration
  @DataProvider(name="loginData")
  public Object[][] passLoginData() throws SQLException{
	  MsAccessDataConfig config = new MsAccessDataConfig("jdbc:ucanaccess://F:/Automation/enquode2018/InputData/Enquode.accdb");
	  int rows = config.getRowCount("loginTable");
	  Object[][] data = new Object[rows][2];
	  for(int i=0;i<rows;i++) {
		  Object[][] temp = new Object[1][2];
		  temp=config.getData(2);
		  data[i][0]=temp[0][0];
		  data[i][1]=temp[0][1];
	  }
	  return data;
  }
  
  
  //Data Provider for TestcaseLogin
  @DataProvider(name="registrationData")
  public Object[][] passRegistrationData() throws SQLException{
	  MsAccessDataConfig config = new MsAccessDataConfig("jdbc:ucanaccess://F:/Automation/enquode2018/InputData/Enquode.accdb");
	  int rows = config.getRowCount("registerTable");
	  Object[][] data = new Object[rows][7];
	  for(int i=0;i<rows;i++) {
		  Object[][] temp = new Object[1][2];
		  temp=config.getData(7);
		  data[i][0]=temp[0][0];
		  data[i][1]=temp[0][1];
		  data[i][2]=temp[0][2];
		  data[i][3]=temp[0][3];
		  data[i][4]=temp[0][4];
		  data[i][5]=temp[0][5];
		  data[i][6]=temp[0][6];
	  }
	  return data;
  }
  
  
  //Data Provider for TestcaseCheckAlertOption 
  @DataProvider(name="alertBoxData")
  public Object[][] passAlertBoxData() throws SQLException{
	  MsAccessDataConfig config = new MsAccessDataConfig("jdbc:ucanaccess://F:/Automation/enquode2018/InputData/Enquode.accdb");
	  int rows = config.getRowCount("alertInputTable");
	  Object[][] data = new Object[rows][3];
	  for(int i=0;i<rows;i++) {
		  Object[][] temp = new Object[1][2];
		  temp=config.getData(3);
		  data[i][0]=temp[0][0];
		  data[i][1]=temp[0][1];
		  data[i][2]=temp[0][2];
	  }
	  return data;
  }

}


