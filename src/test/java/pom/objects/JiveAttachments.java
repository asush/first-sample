package pom.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class JiveAttachments {
	
	String jiveURL,attachpresent,jiveAllAttachNameStr,attachmentNo,eachAttachDetails[],res;
	String spoURL, spoPath;
	int attachmentNos;
	List<String> jiveAllAttchName = new ArrayList<String>();
	List<String> allAttachDetails = new ArrayList<String>();
	HashMap<String,Object> hm=new HashMap<String,Object>();  
	
	public HashMap<String, Object> findAttachmentsJive (WebDriver driver,List<String> urls){
		
		for(int fcount=0 ; fcount < urls.size(); fcount++){
			
			String urlVals [] = urls.get(fcount).split(",");
			
			urlVals[0] = urlVals[0].substring(1);
			urlVals[1] = urlVals[1].substring(0, urlVals[1].length() - 1);
			jiveURL = urlVals[0];
			spoURL = urlVals[1];
			
			driver.get(jiveURL);
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				
			}
			
			
			String disTitle = driver.findElement(By.xpath("//div[@class='j-content clearfix']/header/h1")).getText();
			System.out.println(disTitle);
			String groupName = driver.findElement(By.xpath(".//*[@id='js-breadcrumb-intro']/a[2]")).getText();
			
			int attachsec = driver.findElements(By.xpath("//div[@class='jive-attachments']")).size();
			
			if(attachsec > 0){
				
				jiveAllAttchName.clear();
				attachpresent = "Yes";
				attachmentNos = driver.findElements(By.xpath("//li[@class='clearfix']")).size();
				attachmentNo = String.valueOf(attachmentNos);
						
				System.out.println("attachpresent--->>  "+attachpresent);
				System.out.println("attachmentNo--->>  "+attachmentNo);
				
				for(int i = 0 ; i < attachmentNos; i++){
				String attachment = driver.findElements(By.xpath("//li[@class='clearfix']/div/a")).get(i).getText();
				
				if(attachment.contains("\"")){
					attachment=attachment.replace('\"', '_');
				}
				if(attachment.contains("/")){
					attachment=attachment.replace('/', '_');
				}
				if(attachment.contains("*")){
					attachment=attachment.replace('*', '_');
				}
				if(attachment.contains(":")){
					attachment=attachment.replace(':', '_');
				}
				if(attachment.contains(">")){
					attachment=attachment.replace('>', '_');
				}
				if(attachment.contains("<")){
					attachment=attachment.replace('<', '_');
				}
				if(attachment.contains("?")){
					attachment=attachment.replace('?', '_');
				}
				if(attachment.contains("|")){
					attachment=attachment.replace('|', '_');
				}
				if(attachment.contains(",")){
					attachment=attachment.replace(',', '_');
				}
				if(attachment.contains("`")){
					attachment=attachment.replace('`', '_');
				}

				System.out.println(attachment);
				
				jiveAllAttchName.add(attachment);
				}
				
					System.out.println("jiveAllAttchName----->>"+jiveAllAttchName);	
//					Collections.sort(jiveAllAttchName);
//					System.out.println("jiveAllAttchName SORTED----->>"+jiveAllAttchName);	
				
//					StringBuilder jiveAllAttachNametostr = new StringBuilder();
//					for (String sb : jiveAllAttchName)
//					{
////						if(sb.contains(",")){
////							sb=sb.replace(',', ' ');	
////						}
//						jiveAllAttachNametostr.append(sb);
////						jiveAllAttachNametostr.append(";");
//					}

//					jiveAllAttachNameStr = jiveAllAttachNametostr.toString();
//					System.out.println("jiveAllAttachNameStr----------->>>>"+jiveAllAttachNameStr);
					
					res = String.join(";", jiveAllAttchName);
			        System.out.println("RESSS-->"+res);
					
					
			}else{
				attachpresent = "No";
				attachmentNos = 0;
				attachmentNo = String.valueOf(attachmentNos);
				res = "No Attachment";
				System.out.println("attachpresent--->>  "+attachpresent);
				System.out.println("attachmentNo--->>  "+attachmentNo);
			}
			
			spoPath = spoURL+"/Shared Documents/Attachments";
			eachAttachDetails	= new String[]{"Jive",attachpresent,jiveURL,groupName,disTitle,attachmentNo,res,spoPath};
			String eachAttachDetailsStr = Arrays.toString(eachAttachDetails);
			allAttachDetails.add(eachAttachDetailsStr);
			
		}
		
		hm.put("webdriver",driver);
		hm.put("attachdetails",allAttachDetails);  
	
		return hm;
	
		
	}

	

}
