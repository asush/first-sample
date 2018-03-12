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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Simpson {
	public WebDriver driver;
	String csvInputPath = "E:\\SET-XP Rebar-ipop.csv";
//	String csvInputPath = "E:\\SET-XP Threaded Rod-ipop.csv";
//	String csvOutputPath = "E:\\SET-XP Threaded Rod-Output.csv";
	String csvComparePath = "E:\\SET-XP Threaded Rod-Output-Compare.csv";

	int appCount,appitemCount = 0;
	String sourceurl, targeturl, appName, appLink, appItem;
	String[] AdhesiveName;
	String sourceurlFinal, targeturlFinal;
	List<String> appNameList = new ArrayList<String>();
	List<String> appItemList = new ArrayList<String>();
	List<String> appDetailList = new ArrayList<String>();
	String[] header = "SET-XP10~SET-XP22~SET-XP56~ ~SET-XP10 OP~SET-XP22 OP~SET-XP56 OP~Status~Comments".split("~");

	String[] newline = { "\n" };
	String resinStatus, twentyTwoStatus, fiftySixStatus, resinFail, twentyTwoFail, fiftySixFail, finalStatus, Comments = " ";
	Properties prop = new Properties();

	@Test
	public class Test1 {
		public void invokeBrowser() throws IOException {
			// Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(
					"E:\\Workspace_Sushmita\\com.datamigration.spo2013\\src\\test\\java\\config\\loginCredentials.properties");
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

		CSVReader reader = null;
		
		CSVWriter writer1 = new CSVWriter(new FileWriter(csvComparePath, true),'~');
		writer1.writeNext(header);
		writer1.flush();
		writer1.close();
		
		
		AdhesiveName = csvInputPath.split(" ");
		String checkAdhesiveName = AdhesiveName[0];
		checkAdhesiveName = checkAdhesiveName.substring(3);
		System.out.println("checkAdhesiveName-->"+checkAdhesiveName);
		
		if(checkAdhesiveName.equals("SET-3G")){
			driver.get("http://qa.strongtie.com/products/anchorsystems/software/adhesive_est_calc_set3g.asp");
			Thread.sleep(3000);
		}
		if(checkAdhesiveName.equals("SET-XP")){
			driver.get("http://qa.strongtie.com/products/anchorsystems/software/adhesive_est_calc_setxp.asp");
			Thread.sleep(3000);
		}
		if(checkAdhesiveName.equals("AT-XP")){
			driver.get("http://qa.strongtie.com/products/anchorsystems/software/adhesive_est_calc_atxp.asp");
			Thread.sleep(3000);
		}
		if(checkAdhesiveName.equals("ET-HP")){
			driver.get("http://qa.strongtie.com/products/anchorsystems/software/adhesive_est_calc_et.asp");
			Thread.sleep(3000);
		}
		if(checkAdhesiveName.equals("SET")){
			driver.get("http://qa.strongtie.com/products/anchorsystems/software/adhesive_est_calc_set.asp");
			Thread.sleep(3000);
		}
		if(checkAdhesiveName.equals("AT")){
			driver.get("http://qa.strongtie.com/products/anchorsystems/software/adhesive_est_calc_at.asp");
			Thread.sleep(3000);
		}
		
//		int tabsno = driver.findElements(By.xpath("//span[@class='jobtitle']")).size();
//		System.out.println(tabsno);
		
		if(csvInputPath.contains("Threaded Rod")){
			driver.findElement(By.linkText("Threaded Rod")).sendKeys(Keys.ENTER);
			System.out.println("1");
		}
		
		if(csvInputPath.contains("Rebar")){
			driver.findElement(By.xpath(".//*[@id='software']/div[2]/ul/li[2]/a")).sendKeys(Keys.ENTER);
			System.out.println("2");
		}

		if(csvInputPath.contains("Plastic Screen Tube")){
			driver.findElement(By.linkText("Plastic Screen Tube")).sendKeys(Keys.ENTER);
			System.out.println("3");
		}
		
		if(csvInputPath.contains("Steel Screen Tube")){
			driver.findElement(By.linkText("Steel Screen Tube")).sendKeys(Keys.ENTER);
			System.out.println("4");
		}


		try {
			reader = new CSVReader(new java.io.FileReader(csvInputPath));
			String[] celldata = reader.readNext();

			while ((celldata = reader.readNext()) != null) {

				String one = celldata[0].trim();
				String two = celldata[1].trim();
				String three = celldata[2].trim();
				String four = celldata[3].trim();
				String resin = celldata[4].trim();
				String twentyTwo = celldata[5].trim();
				String fiftySix = celldata[6].trim();
				
				
//				System.out.println(one);
//				System.out.println(two);
//				System.out.println(three);
//				System.out.println(four);
//				System.out.println(resin);
//				System.out.println(twentyTwo);
//				System.out.println(fiftySix);
				
				Thread.sleep(3000);
				
				WebElement area = driver.findElement(By.xpath(".//*[@id='threadedrod_intallation_details']"));
				
//				driver.findElement(By.xpath(".//*[@id='threadedrod_form_rod_diameter']")).sendKeys(one);
				area.findElement(By.xpath("//*[contains(@id, 'rod_diameter')]")).sendKeys(one);
				
//				driver.findElement(By.xpath(".//*[@id='threadedrod_form_drill_bit_diameter']")).sendKeys(two);
				area.findElement(By.xpath("//*[contains(@id, 'drill_bit_diameter')]")).sendKeys(two);
				
				driver.findElement(By.xpath(".//*[@id='threadedrod_form_hole_depth']")).clear();
				Thread.sleep(3000);
				driver.findElement(By.xpath(".//*[@id='threadedrod_form_hole_depth']")).sendKeys(three);
				
				driver.findElement(By.xpath(".//*[@id='threadedrod_form_installations']")).clear();
				Thread.sleep(3000);
				driver.findElement(By.xpath(".//*[@id='threadedrod_form_installations']")).sendKeys(four);
				
				driver.findElement(By.xpath(".//*[@id='threadedrod_form']/table/tbody/tr[1]/td[5]/input")).sendKeys(Keys.ENTER);
				
				Thread.sleep(3000);
				
				String resinOP = driver.findElement(By.xpath(".//*[@id='threadedrod_form_8_5oz']")).getText();
				String twentyTwoOP = driver.findElement(By.xpath(".//*[@id='threadedrod_form_22oz']")).getText();
				String fiftySixOP = driver.findElement(By.xpath(".//*[@id='threadedrod_form_56oz']")).getText();
				
				System.out.println(resinOP);
				System.out.println(twentyTwoOP);
				System.out.println(fiftySixOP);
				
				if(resin.equals(resinOP)){
					resinStatus = "PASS";
				}else{
					resinStatus = "FAIL";
					resinFail = "Mismatch in Resin";
				}
				
				if(twentyTwo.equals(twentyTwoOP)){
					twentyTwoStatus = "PASS";
				}else{
					twentyTwoStatus = "FAIL";
					twentyTwoFail = "Mismatch in twentyTwo";
				}
				
				if(fiftySix.equals(fiftySixOP)){
					fiftySixStatus = "PASS";
				}else{
					twentyTwoStatus = "FAIL";
					fiftySixFail = "Mismatch in fiftySix";
				}
				
				if(resinStatus.equals("PASS") && twentyTwoStatus.equals("PASS") && fiftySixStatus.equals("PASS")){
					finalStatus = "PASS";
					
				}else{
					finalStatus = "Fail";
					Comments = resinFail + twentyTwoFail + fiftySixFail;
				}
				
//				CSVWriter writer1 = new CSVWriter(new FileWriter(csvComparePath, true),'~');
//				writer1.writeNext(header);
//				writer1.flush();
//				writer1.close();
				
				System.out.println(resinStatus);
				System.out.println(twentyTwoStatus);
				System.out.println(fiftySixStatus);
				System.out.println(finalStatus);
				System.out.println(Comments);
				
				CSVWriter writer = new CSVWriter(new FileWriter(csvComparePath, true),'~');
//				writer.writeNext(header);
				writer.writeNext(new String[]{resin,twentyTwo,fiftySix," ",resinOP,twentyTwoOP,fiftySixOP,finalStatus,Comments});
				writer.writeNext(newline);
				writer.flush();
				writer.close();	
			
				
				// DROP DOWN SELECTION (DEFAULT SELECT with index 0) ==================
				Select dropdown1= new Select(driver.findElement(By.id("threadedrod_form_rod_diameter")));
				dropdown1.selectByIndex(0);
				
				Select dropdown2= new Select(driver.findElement(By.id("threadedrod_form_drill_bit_diameter")));
				dropdown2.selectByIndex(0);
				
			}
		} catch (IOException e) {
			System.out.println("Exception Error in Source");
		}
}
}
