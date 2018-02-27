package edu.utsa.tl13;
import java.io.*;


public class ReadInputFile {
	
	public static String[] readInputFile(String FileName){
		
		String filecontent=null;
		try{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName));
		 
		  StringBuffer stringBuffer = new StringBuffer();
		  String line = null;
		 
		 
		  
			while((line =bufferedReader.readLine())!=null){
			   if(-1 != line.indexOf("%")){
			    	
			    	line =line.substring(0, line.indexOf("%"));
			    }
				     
			    	stringBuffer.append(line).append(" ");
			    
			  }
			
			filecontent = stringBuffer.toString();
			filecontent.trim();
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		  //System.out.println(filecontent);
		  
		  String symbol[] = filecontent.split("\\s+");
		  //RemoveComments.removeComments(symbol);
		  return symbol;
		
	}

}
