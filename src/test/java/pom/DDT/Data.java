package pom.DDT;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class Data {
	
	String[] AttachmentHeader = "Location~Attachment Present?~Page URL~Group Name~Page Title~Total Attachment Nos~Attachments~Status~Comments".split("~");
	String[] ImageHeader = "Location~Item Type~Item URL~Folder Name~Total Item Nos~Status~Comments".split("~");
	CSVReader reader = null;
	String jiveurl,spourl,urlDetails[],urlsStr;
	List<String> urls = new ArrayList<String>();
	String[] newline = {"\n"};
	
	public void attachmentCSVOPIntialize (String csvAttOutputPath){
	
	try {
		CSVWriter writer = new CSVWriter(new FileWriter(csvAttOutputPath, true),'~');
		writer.writeNext(AttachmentHeader);
		writer.flush();
		writer.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
	public void imageCSVOPIntialize (String csvAttOutputPath){
	
	try {
		CSVWriter writer = new CSVWriter(new FileWriter(csvAttOutputPath, true),'~');
		writer.writeNext(ImageHeader);
		writer.flush();
		writer.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	public List<String> dataRead(String csvInputPath){
		
		try {	
		reader = new CSVReader(new java.io.FileReader(csvInputPath));
		String[] celldata = reader.readNext();
		
		while ((celldata = reader.readNext()) != null) {
			
			String url = celldata[0];
			String[] urlDetails = url.split(",");
			urlsStr = Arrays.toString(urlDetails);
			urls.add(urlsStr);
		}		
		
	}catch (IOException e) {
		//e.printStackTrace();
		System.out.println("Exception Error in eRoom");
	}
		return urls;
		}
	
	
	
	public void opDataWrite(List<String> jWriteDetails, List<String> spoWriteDetails, String csvAttOutputPath) throws IOException{
		
		String[] jFinalArray = new String [10];
		String[] spoFinalArray = new String [10];
		
		jFinalArray  = jWriteDetails.toArray(jFinalArray);
		spoFinalArray  = spoWriteDetails.toArray(spoFinalArray);
		
		CSVWriter writer = new CSVWriter(new FileWriter(csvAttOutputPath, true),'~');
		writer.writeNext(jFinalArray);
		writer.writeNext(spoFinalArray);
		writer.writeNext(newline);
		writer.flush();
		writer.close();
		
	}
	
}
