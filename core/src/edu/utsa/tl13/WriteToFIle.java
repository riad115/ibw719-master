package edu.utsa.tl13;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFIle {
	
	public static void writeToFile(String pFilename, StringBuffer pData) throws IOException { 
		
		try {
			 
		      File file = new File(pFilename);
	 
		      if (file.createNewFile()){
		        //System.out.println("File is created!");
		      }else{
		    	  file.delete();
		       // System.out.println("File already exists.");
		      }
	 
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
        BufferedWriter out = new BufferedWriter(new FileWriter(pFilename,false));  
        out.write(pData.toString());
        //out.newLine();
        out.write("}");
        out.flush();  
        out.close();  
    }  

}
