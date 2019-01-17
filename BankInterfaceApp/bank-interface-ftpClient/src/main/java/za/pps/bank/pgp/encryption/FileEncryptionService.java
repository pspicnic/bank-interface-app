/**
 * 
 */
package za.pps.bank.pgp.encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 12 Mar 2018
 * @version 1.0
 */
@Service
public class FileEncryptionService {
	
	public String encryptFile(String fileName, String filePath) {
		StringBuffer output = new StringBuffer();
		String encryptedFileName = null;
		
		try {
			
			//This code here, executeS a shell script on the server that I created on each specific path for encrypting
			encryptedFileName = getEncryptedFileName(fileName);
			
			//This code checks the parent dir and checks is the file already exists
			File dir = new File(filePath);
			if(dir.isDirectory()){
				 if(isFileAlreadyEncrypted(encryptedFileName, dir)){
					 return encryptedFileName;
				 }
			}
			
			Process process = Runtime.getRuntime()  
				   .exec(new String[]{"/bin/sh", "-c", String.format("%sencryptFile.sh %s %s %s", 
							filePath,
							filePath.concat("passphrase"),
							filePath.concat(encryptedFileName),
							filePath.concat(fileName))});
			
			process.waitFor();
			InputStream stream = (process.getErrorStream() != null) ? process.getErrorStream() : process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine())!= null) {
            	output.append(line + "\n");
			}
            
            System.out.println("=============Done Encrypting=================");
           
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return encryptedFileName;
		
	}

	/**
	 * @param encryptedFileName
	 * @param dir
	 */
	private boolean isFileAlreadyEncrypted(String encryptedFileName, File dir) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if(file.getName().equalsIgnoreCase(encryptedFileName) ){
				 System.out.println(" ===== File : "+ encryptedFileName + " : ===  is already encrypted");
				 return true;
			}
		}
		return false;
	}
	
	public String getEncryptedFileName(String fileName){
		String[] _tmpFile = fileName.split("\\.");
		return _tmpFile[0].concat("_encrypted.asc");
	}

}
