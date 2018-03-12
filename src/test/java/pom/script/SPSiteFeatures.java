package pom.script;

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
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;



public class SPSiteFeatures {
	public WebDriver driver;
	String csvInputPath = "E:\\SPO_Inputs.csv";
	String csvOutputPath1 = "E:\\SPO_SiteFeaturesResults.csv";
	String csvOutputPath2 = "E:\\SPO_SiteFeaturesTargetInitial.csv";
	
	String sourceurl,targeturl,sourceFeatureString,eachFeatureDetails[],targetFeatureString,sFeatureNameInTArray[];
	String sourceurlFinal,sourceURLinFunc,targetURLinFunc,sourceFeatureinFunc,targeturlFinal;
	List<String> allFeatureDetails = new ArrayList<String>();
	List<String> sourceFeatureArrayList = new ArrayList<String>();
	List<String> targetFeatureArrayListInitial = new ArrayList<String>();
	
	String[] newline = {"\n"};
	String[] header = "Location~URL~Site Features~Status~Comments".split("~");
	
	String spoitemtype,spoFilesEntriesNo,sizeStatus,sizeComments,entriesStatus,entriesComments,nextTotalno;
	String eroomLocation,eroomitemType,eroomFileFodlerURL,eroomFileFolderName,eroomFileEntriesNo;
	WebElement spoDBparent;
	String status,comments;
	
	
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
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\sushmitad\\Downloads\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				} else {
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				System.setProperty("webdriver.ie.driver", "C:\\Users\\sushmitad\\Downloads\\IEDriverServer.exe");
				driver = new InternetExplorerDriver(capabilities);
				}
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
	}
	
	
	@Test
	public void siteOpen() throws Exception {
		
		CSVReader reader = null;
		CSVWriter writer1 = new CSVWriter(new FileWriter(csvOutputPath1, true),'~');
		writer1.writeNext(header);
		writer1.flush();
		writer1.close();
		
		CSVWriter writer2 = new CSVWriter(new FileWriter(csvOutputPath2, true),'~');
		writer2.writeNext(header);
		writer2.flush();
		writer2.close();
		
		Thread.sleep(3000);
		driver.get("http://info.collaboration.is.keysight.com");
		Thread.sleep(3000);
		
		//Login handling with Auto IT
		Runtime.getRuntime().exec("C:\\Users\\sushmitad\\Desktop\\Office\\SPO migration\\credentialsIE.exe");
		Thread.sleep(3000);
		//Login part skipped 

		
		try {		
			reader = new CSVReader(new java.io.FileReader(csvInputPath));
			String[] celldata = reader.readNext();
			
			
			while ((celldata = reader.readNext()) != null) {
				
				String urls = celldata[0];
				String[] urlVals = urls.split(",");
				sourceurl = urlVals[0];
				targeturl = urlVals[1];
				
//				System.out.println(sourceurl);
//				System.out.println(targeturl);
				
				sourceurlFinal = sourceurl+"/_layouts/15/ManageFeatures.aspx";
				
		driver.get(sourceurlFinal);
		
		if (sourceurl != null && !sourceurl.contains("javascript")) {

			try {
				URL eroomsite = new URL(sourceurl);
				HttpURLConnection eroomconn = (HttpURLConnection) eroomsite.openConnection();
				eroomconn.setRequestMethod("GET");
//				System.out.println(
//						eroomconn.getResponseCode() + "   :   " + eroomconn.getResponseMessage() + "---" + eroomurl + "\n");
				
				if (eroomconn.getResponseCode() != 404) {

					String sourcepageTitle = driver.getTitle();
					
						if (!sourcepageTitle.equals("Error")) {
							
							int sactiveNo = driver.findElements(By.xpath(".//input[@value='Deactivate']")).size();
							System.out.println(driver.getTitle()+"---"+sactiveNo);
							
							//Screenshot part
//							File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//							// Now you can do whatever you need to do with it, for example copy somewhere
//							FileUtils.copyFile(scrFile, new File("E:\\Screenshits\\"+System.currentTimeMillis()+".png"));
							
							for (int i = 0; i < sactiveNo; i ++)
							{
								String sfeatureName = driver.findElements(By.xpath("//input[@value='Deactivate']//parent::div//parent::td/parent::tr/td[2]/table/tbody/tr[1]/td/h3")).get(i).getText();
								System.out.println("featureNames---->>"+sfeatureName);
								
								sourceFeatureArrayList.add(sfeatureName);
							}
							
							sourceFeatureString = String.join(";", sourceFeatureArrayList);
					        System.out.println("sourceFeatureString-->"+sourceFeatureString);

						
						} else {							
							System.out.println("Sorry, something went wrong -->>  " + sourceurl + "\n\n");
							driver.close();
							Test1 class1 = new Test1();
							class1.invokeBrowser();
						}
				
					
				} else {
					System.out.println("Invalid URL:  " + sourceurl + "\n");
					driver.close();
					Test1 class1 = new Test1();
					class1.invokeBrowser();
					}

			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("GOOOODDDD");
			}
		}
		eachFeatureDetails	= new String[]{sourceurlFinal,sourceFeatureString,targeturl};
		String eachFeatureDetailsStr = Arrays.toString(eachFeatureDetails);
		allFeatureDetails.add(eachFeatureDetailsStr);
	}	
			targetFeatureDetails(allFeatureDetails);
		//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx	
		}catch (IOException e) {
			System.out.println("Exception Error in Source");
		}
	}
	
	
	public void targetFeatureDetails(List<String> allFeatureDetails) throws IOException{
		
		//String initialURL = "https://keysighttech.sharepoint.com";


		for(int fcount=0 ; fcount < allFeatureDetails.size(); fcount++){
			
			String spoFeatureDtlVals [] = allFeatureDetails.get(fcount).split(",");
			
			System.out.println("from Array0--> "+ spoFeatureDtlVals[0]);
			System.out.println("from Array1--> "+ spoFeatureDtlVals[1]);
			System.out.println("from Array2--> "+ spoFeatureDtlVals[2]);
			
			sourceURLinFunc = spoFeatureDtlVals[0].trim();
			sourceFeatureinFunc = spoFeatureDtlVals[1].trim();
			targetURLinFunc = spoFeatureDtlVals[2].trim();
			
			targetURLinFunc = targetURLinFunc.substring(0, targetURLinFunc.length() - 1).trim();
			
//			Test1 class1 = new Test1();
//			class1.invokeBrowser();
			
			driver.get("https://netwoveninc.sharepoint.com");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {}
			
			// LOGIN SATRT-------------------------------
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//				driver.findElement(By.xpath(".//*[@id='cred_userid_inputtext']")).sendKeys("sushmitad@netwoven.com");
			driver.findElement(By.xpath(".//*[@id='cred_userid_inputtext']")).sendKeys(prop.getProperty("targetusername"));
			driver.findElement(By.xpath(".//*[@id='cred_userid_inputtext']")).sendKeys(Keys.TAB);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
			driver.findElement(By.xpath(".//*[@id='cred_password_inputtext']")).sendKeys(prop.getProperty("targetpwd"));
			driver.findElement(By.xpath(".//*[@id='cred_password_inputtext']")).sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			// LOGIN CLOSE---------------------------------
			
			targeturlFinal = targetURLinFunc+"/_layouts/15/ManageFeatures.aspx";
			driver.get(targeturlFinal);
			
			if (targeturlFinal != null && !targeturlFinal.contains("javascript")) {
				
				try {
					URL spofoldersite = new URL(targetURLinFunc);
					HttpURLConnection spofolderconn = (HttpURLConnection) spofoldersite.openConnection();
					spofolderconn.setRequestMethod("GET");
//					System.out.println(
//							spofolderconn.getResponseCode() + "   :   " + spofolderconn.getResponseMessage() + "---" + targetURLinFunc + "\n");
					
					if (spofolderconn.getResponseCode() != 404) {

						String targetpageTitle = driver.getTitle();
						
							if (!targetpageTitle.equals("Error")) {
								
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e1) {}
								
								driver.findElement(By.xpath(".//*[@id='ctl00_PlaceHolderMain_featact_rptrFeatureList_ctl09_ctl00_btnActivate']")).click();
								
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e1) {}
//								
//								int tactiveNoInitial = driver.findElements(By.xpath(".//input[@value='Deactivate']")).size();
//								System.out.println(driver.getTitle()+"---"+tactiveNoInitial);
//								
//								
//								for (int i = 0; i < tactiveNoInitial; i ++)
//								{
//									String tfeatureNameInitial = driver.findElements(By.xpath("//input[@value='Deactivate']//parent::div//parent::td/parent::tr/td[2]/table/tbody/tr[1]/td/h3")).get(i).getText();
//									System.out.println("target featureNames---->>"+tfeatureNameInitial);
//									
//									targetFeatureArrayListInitial.add(tfeatureNameInitial);
//								}
//								
//								targetFeatureString = String.join(";", targetFeatureArrayListInitial);
//						        System.out.println("targetFeatureString-->"+targetFeatureString);
//						        
//						        CSVWriter writer = new CSVWriter(new FileWriter(csvOutputPath2, true),'~');
//								writer.writeNext(new String[]{"Target",targetURLinFunc,targetFeatureString,"Active","Initial State"});
//								writer.writeNext(newline);
//								writer.flush();
//								writer.close();
//								
//								
//								sFeatureNameInTArray = sourceFeatureinFunc.split(";");
//								
//								List<String> sFeatureList = Arrays.asList(sFeatureNameInTArray);
//								
//								for (String e : sFeatureList)  
//							      {  
//									
//								int featureFound = driver.findElements(By.xpath("//*[contains(text(), '"+e+"')]")).size();
////								int featureFound = driver.findElements(By.xpath("//*[./text(), '"+e+"']")).size();
//								System.out.println(featureFound);
//								
//								if(featureFound > 0){
//									
//									String name = driver.findElements(By.xpath("//*[contains(text(), '"+e+"')]//parent::td//parent::tr//parent::tbody//parent::table//parent::td//parent::tr/td[3]/div/input")).get(0).getAttribute("value");
////									System.out.println(name);
//									
//									if(name.equals("Activate")){
//										
//										try {
//											Thread.sleep(3000);
//										} catch (InterruptedException e1) {}
//										
////										driver.findElement(By.xpath("//*[contains(text(), '"+e+"')]//parent::td//parent::tr//parent::tbody//parent::table//parent::td//parent::tr/td[3]/div/input")).click();
//										driver.findElement(By.xpath("//*[contains(text(), 'Announcement Tiles')]//parent::td//parent::tr//parent::tbody//parent::table//parent::td//parent::tr/td[3]/div/input[contains(@id,'PlaceHolderMain_featact_rptrFeatureList')]")).click();
//										try {
//											Thread.sleep(3000);
//										} catch (InterruptedException e1) {}
//										
//										System.out.println(e+"-->Clicked");
//									}else{
//										System.out.println(e+"--->"+name);  
//									}
//								}
////							         System.out.println("CHECKEDDDDDDDDDDDDD"+e);  
//							      } 

							
							} else {							
								System.out.println("Sorry, something went wrong -->>  " + targetURLinFunc + "\n\n");
								driver.close();
								Test1 class1 = new Test1();
								class1.invokeBrowser();
							}
					
						
					}else {
//								CSVWriter writer = new CSVWriter(new FileWriter(csvOutputPath, true),'~');
//								writer.writeNext(new String[]{"SPO","Sorry, something went wrong",currentspoFolderURL," ", " "," ", " ", " ", " ","FAIL"});
//								writer.writeNext(newline);
//								writer.flush();
//								writer.close();
//						
//								System.out.println("Sorry, something went wrong -->>  " + currentspoFolderURL + "\n\n");
//								driver.close();
								//Test1 class2 = new Test1();
//								class1.invokeBrowser();
							}
					
						}catch (Exception e) {
							
//							CSVWriter writer = new CSVWriter(new FileWriter(csvOutputPath, true),'~');
//							writer.writeNext(new String[]{"eRoom","Items",spoFileDtlVals[2],spoFileDtlVals[3],spoFileDtlVals[4]});
//							writer.writeNext(new String[]{"SPO","ISSUE","FAIL"});
//							writer.writeNext(newline);
//							writer.flush();
//							writer.close();
							
	
						}
						
			//}		
			} else {
						
//						CSVWriter writer = new CSVWriter(new FileWriter(csvOutputPath, true),'~');
//						writer.writeNext(new String[]{"SPO", "Invalid URL", currentspoFolderURL," ", " "," ", " ", " ", " ","FAIL"});
//						writer.writeNext(newline);
//						writer.flush();
//						writer.close();
//						
//						System.out.println("Invalid URL:  " + currentspoFolderURL + "\n\n");
//						driver.close();
//						//Test1 class2 = new Test1();
//						class1.invokeBrowser();
						}
			
//			driver.close();
		//}
	}
				
	}
	
	
}
