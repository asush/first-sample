package pom.objects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;

public class BrowserInvoke {
	
	public WebDriver driver;
	Properties prop = new Properties();
	
	public BrowserInvoke() {}
	
	public BrowserInvoke(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	
	public WebDriver invokeBrowser() throws IOException {
	
			FileInputStream fis = new FileInputStream(
				"E:\\Workspace_Sushmita\\com.datamigration.jivespo\\src\\test\\java\\config\\loginCredentials.properties");
			prop.load(fis);

			if (prop.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
			"C://Users/sushmitad/Desktop/Office/Gecko/geckodriver.exe");
			driver = new FirefoxDriver();
			} else if (prop.getProperty("browser").contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\sushmitad\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			} else {
			System.setProperty("webdriver.ie.driver", "C:\\Users\\sushmitad\\Downloads\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			}
		driver.manage().window().maximize();
		return driver;
	}
	
		

}
