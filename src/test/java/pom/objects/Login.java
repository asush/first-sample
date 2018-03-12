package pom.objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Login {
	
	Properties prop = new Properties();
	public Login() {}
	
	//Initialize objects in the page
			public Login(WebDriver driver){
				PageFactory.initElements(driver, this);
			}

	public WebDriver loginJive(WebDriver driver) throws IOException{
		FileInputStream fis = new FileInputStream("E:\\Workspace_Sushmita\\com.datamigration.jivespo\\src\\test\\java\\config\\loginCredentials.properties");
			prop.load(fis);
		driver.get("https://keysight-int.jiveon.com");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
		}
		
		driver.findElement(By.xpath("//*[@id='sso-login-submit']/strong")).click();
		driver.findElement(By.xpath("//*[@id='okta-signin-username']")).sendKeys(prop.getProperty("jiveusername"));
		driver.findElement(By.xpath("//*[@id='okta-signin-password']")).sendKeys(prop.getProperty("jivepassword"));
		driver.findElement(By.xpath("//*[@id='okta-signin-submit']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
		driver.findElement(By.xpath("//*[@id='input39']")).sendKeys(prop.getProperty("answer"));
		driver.findElement(By.xpath("//input[@value='Verify']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}
		
		// LOGIN CLOSE---------------------------------
		
		return driver;
	}
	
	public WebDriver loginSPO(WebDriver driver) throws IOException{
		FileInputStream fis = new FileInputStream("E:\\Workspace_Sushmita\\com.datamigration.jivespo\\src\\test\\java\\config\\loginCredentials.properties");
		prop.load(fis);
	driver.get("https://keysighttech.sharepoint.com");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		
	}
	// LOGIN SATRT-------------------------------
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.findElement(By.xpath(".//*[@id='cred_userid_inputtext']")).sendKeys(prop.getProperty("spousername"));
			driver.findElement(By.xpath(".//*[@id='cred_userid_inputtext']")).sendKeys(Keys.TAB);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.xpath(".//*[@id='passwordInput']")).sendKeys(prop.getProperty("spopwd"));
			driver.findElement(By.xpath(".//*[@id='passwordInput']")).sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			// LOGIN CLOSE---------------------------------
	
		
		return driver;
	}
	

}
