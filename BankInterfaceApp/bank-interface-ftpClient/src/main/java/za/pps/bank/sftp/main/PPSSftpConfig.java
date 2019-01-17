/**
 * 
 */
package za.pps.bank.sftp.main;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import za.pps.bank.common.EncryptionDataBean;
import za.pps.bank.common.UploadFileToSFTPServerDataBean;
import za.pps.bank.pgp.encryption.FileEncryptionService;
import za.pps.bank.sftp.upload.SfptUploadFile;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 10 Oct 2017
 * @version 1.0
 */
@Component
public class PPSSftpConfig {
	
	@Autowired
	private FileEncryptionService fileEncryption;
	
	@Autowired
	private SfptUploadFile fileUpload; 
	
	public Map<String,String> encryptFiles(EncryptionDataBean bean) {
		
		Map<String,String> results = new HashMap<String, String>();
		
		String ddFile = bean.getDDFullFilePath();
		String ccFile = bean.getCCFullFilePath();
		
		System.out.println("============= Going to encrypt the following files =================");
		System.out.println(ddFile);
		System.out.println(ccFile);
		
		if(ddFile != null){
			System.out.println( "  " );
			if(!StringUtils.isEmpty(bean.getDdFileName())){
				System.out.println( "File Name: " + bean.getDdFileName() );
				results.put("DD", fileEncryption.encryptFile(bean.getDdFileName(), bean.getDdFilePath()));
			}
		}
		if(ccFile != null){
			if(!StringUtils.isEmpty(bean.getCcFileName())){
				System.out.println( "File Name: " + bean.getCcFileName() );
				results.put("CC",fileEncryption.encryptFile(bean.getCcFileName(),bean.getCcFilePath()));
			}
		}
		
		return results;
	}
	
	public void decryptFiles(){
		throw new UnsupportedOperationException();
	}

	
	public boolean uploadToSFTPServer( UploadFileToSFTPServerDataBean bean){
		
		boolean isFileUploadSuccessful = false;
		String ddFile = bean.getDdFilePath();
		String ccFile = bean.getCcFilePath();
		
		if(ddFile != null){
			isFileUploadSuccessful = fileUpload.uploadFileToServer(bean.getDdFileName(), bean.getDdFilePath());
		}
		if(ccFile != null){
			isFileUploadSuccessful = fileUpload.uploadFileToServer(bean.getCcFileName(),bean.getCcFilePath());
		}
		
		return isFileUploadSuccessful;
	}
	
	
	
	
	
	
}
