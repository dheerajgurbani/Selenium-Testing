package Emirates;

/* Title: Automating the Emirates Sign up page to join Skywards
 * Test Objective: 
 * 	- Check that the sign up fails if the fields are incomplete or incorrect.
 * 	- Check that the links on the page opens to the correct web pages.
 * 	 - 4 attempts to sign up have been provided and data is read dynamically from input files/lists/arrays in each attempt.
 * Version: 1.0
 * Test script path: c:\\sompath\emirates_signup.java
 * Input files Path: E:\\EclipseWorkspace\\cmpe287\\src\\cmpe287\\phonenumbers.txt to store the contact numbers.
 * Dependencies: Firefox Web browser
 * Test elements:
 *	- Check if the link opens to the correct page by checking the page title
 *	- Sign up by filling in the form fields (Enter incorrect data or miss some fields to fail the sign up) 
 *  	- Click on the several links presents on the web page and see if they open the correct page.
 * Data Source: 
 * 	- Data has been read from the text file and also from different data structures (map, array, lists)
 * 	- Static data has been used as well.
 * Validation:
 * 	- Check the presence of the error dialog to ensure that the sign up failed.
 * 	- Check the page title to ensure that the page is correct
 * 	- Check the page URL to ensure that links land up on the correct page.
 * */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class test {
	
	//Making all the URLs as constant
	private final static String FILE_PATH = "E:\\EclipseWorkspace\\cmpe287\\src\\cmpe287\\phonenumbers.txt";
	private final static String PAGE_URL = "https://www.emirates.com/account/english/light-registration/";
	private final static String LINK1_URL = "http://www.emirates.com/english/skywards/about/skywards.aspx";
	private final static String LINK2_URL = "https://www.emirates.com/account/english/miles-calculator/miles-calculator.aspx";
	
	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriver wd = new FirefoxDriver();
		
		//file here stores the contact numbers. The contact numbers will be read from the file one by one.
		File file = new File(FILE_PATH);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		//reading the title from the array of Strings
		String[] title = {"Mr","Ms","Mrs","Miss"};
		int index = 0;
		
		//variable to determine if the tc passed or failed.
		boolean fail = false;

		try {
			String line = null;
			
			//4 attempts have been provided to sign up and data is read from the file.
			while ((line = bufferedReader.readLine()) != null) {
				String currPhone = line;

				// open the emirates registration page
				wd.get(PAGE_URL);
				Thread.sleep(5000);
				
				// Check the title of the page
				String expectedTitle = "Join Emirates Skywards";
				String currTitle = wd.getTitle();
				Thread.sleep(2000);
				
				//validate the url and the title
				if (!expectedTitle.equals(currTitle)) {
					System.out.println("TEST FAIL: PAGE TITLE DIDN'T MATCH\n");
					fail = true;
				}

				// maximize the window
				wd.manage().window().maximize();
				Thread.sleep(3000);
				
				//select the title field
				Select dropdown = new Select(wd.findElement(By.id("ddlTitle")));
				dropdown.selectByVisibleText(title[index]);
				Thread.sleep(2000);

				// enter the first name
				WebElement name = wd.findElement(By.name("txtFirstName"));
				name.sendKeys("Dheeraj");
				Thread.sleep(2000);

				// enter the last name
				name = wd.findElement(By.name("txtFamilyName"));
				name.sendKeys("Gurbani");
				Thread.sleep(2000);

				// enter the dob fields
				dropdown = new Select(wd.findElement(By.id("ddlDates")));
				dropdown.selectByVisibleText("01");
				Thread.sleep(2000);

				dropdown = new Select(wd.findElement(By.id("ddlMonth")));
				dropdown.selectByVisibleText("January");
				Thread.sleep(2000);

				dropdown = new Select(wd.findElement(By.id("ddlYear")));
				dropdown.selectByVisibleText("1989");
				Thread.sleep(2000);

				// select the email address
				name = wd.findElement(By.name("txtEmailAddress"));
				name.sendKeys("dummy@gmail.com");
				Thread.sleep(2000);

				// select the country of residence
				name = wd.findElement(By.name("ddlResidence-suggest"));
				name.clear();
				name.sendKeys("United States");
				Thread.sleep(5000);

				// select the language from the dropdown
				//missing this required field to fail the sign up process.
				
				/*dropdown = new Select(wd.findElement(By.id("ddlPreferredLanguage")));
				dropdown.selectByVisibleText("English");
				Thread.sleep(10000);*/

				// mobile number
				dropdown = new Select(wd.findElement(By.id("mobileTelephoneNumber_ddlCountryCode")));
				dropdown.selectByVisibleText("United States (+1)");
				Thread.sleep(2000);

				name = wd.findElement(By.id("mobileTelephoneNumber_txtTelephone"));
				name.sendKeys(currPhone);
				Thread.sleep(2000);

				// enter the password in the field
				name = wd.findElement(By.id("txtSetNewPassword"));
				name.sendKeys("tempPassword");
				Thread.sleep(2000);

				// enter the password again - confirm field
				name = wd.findElement(By.id("txtConfirmNewPwd"));
				name.sendKeys("tempPassword");
				Thread.sleep(2000);

				// click the sign up and agreement check boxes
				wd.findElement(By.id("chksignUpNL")).click();
				Thread.sleep(1000);

				wd.findElement(By.id("chkAgreemant")).click();
				Thread.sleep(1000);

				// click the join button now
				wd.findElement(By.id("MainContent_ctl00_btnjoin")).click();

				//check the presence of the error panel 
				if((wd.findElement(By.className("errorPanel")).isDisplayed()) == true)
					System.out.println("TEST CASE PASS - CANNOT SIGN UP WITH INVALID/MISSING FIELDS");
				else
					fail = true;
				index++;
			}
			
			//click on the links on the page and validate the url of the destination page
			wd.findElement(By.linkText("Emirates Skywards")).click();
			Thread.sleep(3000);
			if(wd.getCurrentUrl().equals(LINK1_URL)) {
				System.out.println("Link matched");
			} else {
				System.out.println("Link - Emirates Skywards did not match");
				fail = true;
			}
			
			//going back to check more urls.
			wd.navigate().back();
			
			wd.findElement(By.linkText("Miles Calculator")).click();
			Thread.sleep(3000);
			if(wd.getCurrentUrl().equals(LINK2_URL)) {
				System.out.println("Link matched");
			} else {
				System.out.println("Link = Miles Calculator did not match");
				fail = true;
			}
			
			//check if the test case is finally passed or failed.
			if(fail)
				System.out.println("TEST CASE FAILED. See LOGS for DETAILS.");
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			
			//closing all the streams.
			wd.close();
			fileReader.close();
			bufferedReader.close();

		}
	}

}

/*NOTE: Emirates Sign up page gives an exception that could not be resolved on using the XPath.
Hence elements are looked up by the name and Id. It is ensured that these names/ids are not duplicated on the page.

Contents of phonenumbers.txt
1234567890
9999999999
2342341234
9999888822

It can be enhanced to add more data.
*/
