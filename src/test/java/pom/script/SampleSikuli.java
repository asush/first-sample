package pom.script;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class SampleSikuli {
	public WebDriver driver;
	String csvInputPath = "E:\\SPO2013_Inputs.csv";
	String csvOutputPath1 = "E:\\SPO_SiteFeaturesResults.csv";
	String csvOutputPath2 = "E:\\SPO_SiteFeaturesTargetInitial.csv";
	

	String[] newline = { "\n" };
	String[] header = "Location~URL~Site Features~Status~Comments".split("~");

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
				// DesiredCapabilities capabilities =
				// DesiredCapabilities.internetExplorer();
				// capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
				// true);
				System.setProperty("webdriver.ie.driver", "C:\\Users\\sushmitad\\Downloads\\IEDriverServer.exe");
				// driver = new InternetExplorerDriver(capabilities);
				driver = new InternetExplorerDriver();
			}
			driver.manage().window().maximize();
		}
	}

	@Test
	public void siteOpen() throws Exception {


//***************************TESTED GMAIL LOGIN CODE WITH SIKULI***********************************
//		driver.get("http://www.google.com");
//		Thread.sleep(3000);

//		Screen screen = new Screen();
//		Pattern image1 = new Pattern("C:\\Users\\sushmitad\\Desktop\\24-11-2017\\gmail_logo.PNG");
//		Pattern image2 = new Pattern("C:\\Users\\sushmitad\\Desktop\\24-11-2017\\signin.PNG");
//		Pattern image3 = new Pattern("C:\\Users\\sushmitad\\Desktop\\24-11-2017\\uname.PNG");
//		Pattern image4 = new Pattern("C:\\Users\\sushmitad\\Desktop\\24-11-2017\\next.PNG");
//		Pattern image5 = new Pattern("C:\\Users\\sushmitad\\Desktop\\24-11-2017\\pwd.PNG");
//		
//		screen.click(image1);
//		screen.wait(image2,3);
//		screen.click(image2);
//		screen.type(image3,"sush.das0609@gmail.com");
//		screen.click(image4);
//		screen.type(image5,"");
//		screen.click(image4);
//************************************************************************************************
		
		driver.get("http://devportal2013.netwovenindia.com/sites/SeleniumTest");
//		Thread.sleep(5000);
		
		Screen screen = new Screen();
		Pattern image1 = new Pattern("C:\\Users\\sushmitad\\Desktop\\autoit_replace\\window1.PNG");
		Pattern image2 = new Pattern("C:\\Users\\sushmitad\\Desktop\\autoit_replace\\window2.PNG");
		Pattern image3 = new Pattern("C:\\Users\\sushmitad\\Desktop\\autoit_replace\\window3.PNG");
		
		screen.type(image1,"tu001");
		screen.wait(image2,3);
		screen.type(image2,"Passw0rd");
		screen.wait(image3,3);
		screen.click(image3);
		
		
	}

	

}
