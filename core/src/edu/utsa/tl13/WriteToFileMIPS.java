package edu.utsa.tl13;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileMIPS {
	
	public static void writeToFileMIPS(String pFilename, StringBuffer pData) throws IOException { 
		
        int baseNameOffset = pFilename.length() - 5;

        String baseName;
        if (pFilename.substring(baseNameOffset).equals(".tl13"))
            baseName = pFilename.substring(0,baseNameOffset);
        else
            throw new RuntimeException("inputFileName does not end in .tl13");

        String parseOutName = baseName + ".s";
		
		try {
			 
		      File file = new File(parseOutName);
	 
		      if (file.createNewFile()){
		        //System.out.println("File is created!");
		      }else{
		    	  file.delete();
		       // System.out.println("File already exists.");
		      }
	 
	    	} catch (IOException e) {
		      e.printStackTrace();
		}
        BufferedWriter out = new BufferedWriter(new FileWriter(parseOutName,false));  
        out.write(pData.toString());
        //out.newLine();
        //out.write("}");
        out.flush();  
        out.close();
	}
}
