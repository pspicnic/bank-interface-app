/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 24 Feb 2018
 * @version 1.0
 */
@Component
public class FileReaderUtil {

	public List<String> readContentFromFile(File file) throws Exception {
		List<String> fileContent = new ArrayList<>();
		String line = "";
			try(BufferedReader br = new BufferedReader(new FileReader(file.getPath()))){
				while ((line = br.readLine()) != null) {
					fileContent.add(line);
				}
			}
		return fileContent;
	}

	 public List<String> readContentFromLatestFile(String ddOutputFolder) throws Exception {
	        List<String> fileContent = new ArrayList<>();
	        if(ddOutputFolder != null) {
	            File fileToDisplay = getLatestFile(ddOutputFolder);
	            String line = "";
	            try(BufferedReader br = new BufferedReader(new FileReader(fileToDisplay.getPath()))){
	            	while ((line = br.readLine()) != null) {
		               fileContent.add(line);
		            }
	            }
	        }     
	        return fileContent;
	    }


	 public File getLatestFile(String folderPath) {
	        File latestFile = null;
		 File folderFile = new File(folderPath);
		 File[] files = folderFile.listFiles(new FileFilter() {
	            public boolean accept(File file) {
	                return file.isFile();
	            }
	        });
	        long lastMod = Long.MIN_VALUE;
	        for (File file : files) {
	        	//This code needs to be refactored, this filter is just a temporary fix
	        	if(file.getName().contains("decrypted") 
	            || file.getName().contains("encrypted")
	            || file.getName().contains("encrypt")
	            || file.getName().contains("public_key")
	            || file.getName().contains("private_key")
	        	|| file.getName().contains("asc")
	        	|| file.getName().contains(".exp")
	        	|| file.getName().contains(".sh"))
	        		continue;
	        	if( file.getName().contains("DirectEntry")
	        		|| file.getName().contains("CreditCard")
	        			){
	        		if (file.lastModified() > lastMod) {
	        			latestFile = file;
	        			lastMod = file.lastModified();
	        		}
	        	}
	        }
	        return latestFile;
	    }
	
}
