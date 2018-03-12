package samplejen;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Jenkins_Test {
	public WebDriver driver;
	
	
	Properties prop = new Properties();

	@Test
	public class Test1 {
		public void invokeBrowser() throws IOException {
			// Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(
					"E:\\Workspace_Sushmita\\com.datamigration.spo365\\src\\test\\java\\config\\loginCredentials.properties");
			prop.load(fis);

			if (prop.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						"C://Users/sushmitad/Desktop/Office/Gecko/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (prop.getProperty("browser").contains("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\Users\\sushmitad\\Downloads\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
			} else {
				System.setProperty("webdriver.ie.driver", "C:\\Users\\sushmitad\\Downloads\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
//			driver.manage().window().maximize();
		}
	}
	
	@Test
	public void jenkinsSample() throws InterruptedException
	{
		driver.get("http://www.learn-automation.com");
		Thread.sleep(3000);
		Assert.assertTrue(driver.getTitle().contains("Selenium"));
		
	}
	

}
