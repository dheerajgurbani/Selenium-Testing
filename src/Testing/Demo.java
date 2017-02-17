package Testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Demo {
	
	
	private final static String FILE_PATH = "F:\\Testing\\selenium-java-2.53.0\\workspaceForTesting\\TestingDemo\\src\\Testing\\phonenumbers.txt"; //giving file path
	private final static String websiteurl = "https://www.movietickets.com/myaccount.asp?";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize(); // window getting maximized
		File file = new File(FILE_PATH);// file reading operations
 		FileReader fileReader = new FileReader(file);
 		BufferedReader bufferedReader = new BufferedReader(fileReader);
 		String line = null; //initializing testcase to be passed, then if condition fails we will return as fail
 		driver.get(websiteurl); // giving url to be tested
 		boolean testcasepass = true;
 		String pwd = "Dheeraj@1234";
 		String repwd = "Dheeraj@1234";
 		//  checking if conditions if url entered matched or not
 		if(driver.getCurrentUrl().equals(websiteurl)) {
 			System.out.println("Link matched");
 		} else {
 			System.out.println("Link not matched");
 			testcasepass = false;
 		}
 		
 		String sitetitle = driver.getTitle();
        System.out.println(sitetitle);
        //Data Drivern
       	WebElement email=driver.findElement(By.id("email"));
		 WebElement first=driver.findElement(By.id("first_name"));
		 WebElement last=driver.findElement(By.id("last_name"));
		 WebElement paswd =driver.findElement(By.id("password"));
		 WebElement repaswd =driver.findElement(By.id("password_verify"));
		 //Object oriented: dropdown exists.
		 Select dropdown = new Select(driver.findElement(By.id("sex")));
		 email.sendKeys("dheerajdummy@gmail.com"); //putting emaildid
	     first.sendKeys("Dheeraj");
	     last.sendKeys("Gurbani");
	     dropdown.selectByVisibleText("Male"); //putting values in dropdown
	     paswd.sendKeys(pwd);
		 repaswd.sendKeys(repwd);
		 //Data Driven for password matching using java functions for string comparision
		 if(!pwd.equals(repwd)){
			 testcasepass = false;
	       	 System.out.println("password not matching");
		 }
		 //Reading Line by line value in the text file for looping values in zip code Looping Statment
 		while ((line = bufferedReader.readLine()) != null) {
 			String currPhone = line;
			WebElement name = driver.findElement(By.id("lblZip"));
			name.sendKeys(currPhone);
		}
		//wait for page to load all values and then go to different links of web page
 		Thread.sleep(5000);
 		try{
 		driver.findElement(By.linkText("Movies")).click(); //Title of the page checking
 		driver.findElement(By.linkText("Trailers")).click();
 		driver.findElement(By.linkText("Contact Us")).click();
 		driver.findElement(By.linkText("3D")).click();
		}
	
		catch(Exception ex){
			System.out.print(ex);
		}finally {
			driver.close();
		}


	}

}

