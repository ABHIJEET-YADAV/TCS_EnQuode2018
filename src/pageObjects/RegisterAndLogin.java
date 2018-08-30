package pageObjects;

import static org.testng.Assert.assertTrue;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;


public class RegisterAndLogin {
	
	public static WebElement element = null;
	public static String parent;
	public static String childWindow;
	
	
	//Perform First TestCase
	public static void newRegistration(WebDriver driver, String name, String phone, String email, String country, String city, String username, String passward) throws Throwable {
		registerButton(driver);
		driver = handleNewWindow(driver);
		fillRegistrationForm(driver, name, phone, email, country, city, username, passward);
	}
	
	//Perform Second TestCase
	public static void userLogin(WebDriver driver, String username , String passward) throws Throwable {
		registerButton(driver);
		driver = handleNewWindow(driver);
		fillLoginForm(driver, username , passward);
		checkLoginSuccess(driver);
	}
	
	//Perform Third TestCase
	public static void checkAlert(WebDriver driver, String username , String passward, String inputAlertData) throws Throwable {
		registerButton(driver);
		driver = handleNewWindow(driver);
		fillLoginForm(driver, username, passward);
		verifyAlertOptions(driver);
		verifyBothAlerts(driver, inputAlertData);		
	}
	
	
	//Click on registration  hyperlink
	public static void registerButton(WebDriver driver) {
		try {
			parent=driver.getWindowHandle();
			element = driver.findElement(By.xpath("//*[@id=\"toggleNav\"]/li[6]/a"));
			element.click();
		} 
		catch (Exception e) {
			assertTrue(false, "Unable To Click On Registration Link");
		}
	}
	
	
	//Switch to the new tab
	public static WebDriver handleNewWindow(WebDriver driver) throws Exception{
		try {
			Set<String>s1=driver.getWindowHandles();
			Iterator<String> I1= s1.iterator();
			while(I1.hasNext()) {
				childWindow=I1.next();
				if(!parent.equals(childWindow)) {
				    driver.switchTo().window(childWindow);
					parent=driver.getWindowHandle();
				}
			}
		} 
		catch (Exception e) {
			assertTrue(false, "Unable to Switch Driver");
		}
		return driver;
	}
		
	
	//Fill the registration form
	public static void fillRegistrationForm(WebDriver driver, String name, String phone, String email, String country, String city, String username, String passward) throws Exception {
		SoftAssert assertion= new SoftAssert();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(4000);
		
		try {
			driver.findElement(By.xpath("//input[@name=\"name\"]")).sendKeys(name);
			driver.findElement(By.xpath("//input[@name=\"phone\"]")).sendKeys(phone);
			driver.findElement(By.xpath("//input[@name=\"email\"]")).sendKeys(email);
			Select dropdownCountry = new Select(driver.findElement(By.name("country")));
			dropdownCountry.selectByVisibleText(country);
			driver.findElement(By.xpath("//input[@name=\"city\"]")).sendKeys(city);
			driver.findElement(By.xpath("//*[@id=\"load_form\"]/fieldset[6]/input")).sendKeys(username);
			driver.findElement(By.xpath("//*[@id=\"load_form\"]/fieldset[7]/input")).sendKeys(passward);
			driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div/div/form/div/div/input")).click();
			Thread.sleep(4000);
		} catch (Exception e1) {
			assertTrue(false,"Unable to locate element");
		}
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try {
			assertion.assertFalse(driver.findElement(By.xpath("//*[@id=\"alert\"]")).isDisplayed(), "Register Failed Because User Already Have An Account");
		}
		catch(Exception e) {
			System.out.println("Registration Successfull");
		}
		
//		Thread.sleep(2000);
		assertion.assertAll();
		driver.close();
	}
	
	
	//Fill the login form
	public static void fillLoginForm(WebDriver driver, String username , String passward) throws Exception {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(7000);
		try {
			driver.findElement(By.xpath("//*[@id=\"load_box\"]/form/div/div/p/a")).click();
			driver.findElement(By.xpath("//*[@id=\"login\"]/form/fieldset[1]/input")).sendKeys(username);
			driver.findElement(By.xpath("//*[@id=\"login\"]/form/fieldset[2]/input")).sendKeys(passward);
			driver.findElement(By.xpath("//*[@id=\"login\"]/form/div/div[2]/input")).click();
		} catch (Exception e) {
			assertTrue(false,"Unable to locate element");
		}
		Thread.sleep(4000);
	}
	
	
	//Check whether the login is successful or not
	public static void checkLoginSuccess(WebDriver driver) throws Exception {
		SoftAssert assertion= new SoftAssert();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try {
			assertion.assertFalse(driver.findElement(By.xpath("//*[@id=\"alert1\"]")).isDisplayed(), "Login Failed Because Invalid Username Or Passward");
		}
		catch(Exception e) {
			System.out.println("Login Successfull");
		}
		
		Thread.sleep(2000);
		assertion.assertAll();
		driver.close();
	}
	
	
	//Check both alert option available on this page
	public static void verifyAlertOptions(WebDriver driver) throws Exception {
		SoftAssert assertion= new SoftAssert();
		driver.findElement(By.xpath("//*[@id=\"toggleNav\"]/li[7]/a")).click();
		Thread.sleep(2000);
		assertion.assertTrue(driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/div[1]/div[1]/ul/li[1]/a")).isDisplayed(),"Simple alert option not present");
		assertion.assertTrue(driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/div[1]/div[1]/ul/li[2]/a")).isDisplayed(),"Input alert option not present");
		assertion.assertAll();
//		Thread.sleep(2000);
	}
	
	
	//Check whether both alert working properly or not
	public static void verifyBothAlerts(WebDriver driver, String inputAlertData) throws Exception {
			SoftAssert assertion= new SoftAssert();
			try {
				Thread.sleep(2000);
				String id=driver.getWindowHandle();
		        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/div[1]/div[1]/ul/li[2]/a")).click();
				Thread.sleep(2000);
				driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"example-1-tab-2\"]/div/iframe")));
				driver.findElement(By.xpath("//*[contains(text(),'Click the button to demonstrate the Input box.')]")).click();
				Thread.sleep(1000);
				Alert inputAlert = driver.switchTo().alert();
				assertion.assertTrue(inputAlert.getText().contains("Please enter your name"), "Input Alert Text Is Incorrect");
				inputAlert.sendKeys(inputAlertData);
				inputAlert.accept();
				Thread.sleep(1000);
				assertion.assertTrue(driver.findElement(By.xpath("//*[@id=\"demo\"]")).getText().contentEquals("Hello "+inputAlertData+"! How are you today?"), "Input Alert Not working properly");	
				driver.switchTo().window(id);
     		}
			catch(Exception e) {
				assertion.assertTrue(false, "Simple Alert In Not Opening");
			}
			
			assertion.assertAll();
			Thread.sleep(2000);
			
			try {
		        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/div[1]/div[1]/ul/li[1]/a")).click();
				Thread.sleep(2000);
				driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"example-1-tab-1\"]/div/iframe")));
				driver.findElement(By.xpath("//*[contains(text(),'Click the button to display an alert box:')]")).click();
				Thread.sleep(1000);
				Alert simpleAlert = driver.switchTo().alert();
				assertion.assertTrue(simpleAlert.getText().contentEquals("I am an alert box!"), "Simple Alert Text Is Incorrect");
				simpleAlert.accept();
				Thread.sleep(1000);
	  		}
			catch(Exception e) {
				assertion.assertTrue(false, "Simple Alert In Not Opening");
			}
			assertion.assertAll();
			Thread.sleep(1000);
			System.out.println("Both Alert Boxes Working Perfectly");
		
		}

}
