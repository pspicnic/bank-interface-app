package za.co.pps.bank.mutual.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import za.co.pps.bank.mutual.utils.FileReaderUtil;
import za.co.pps.bank.mutual.utils.PropertiesUtil;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;
import za.pps.bank.common.EncryptionDataBean;
import za.pps.bank.common.UploadFileToSFTPServerDataBean;
import za.pps.bank.sftp.main.PPSSftpConfig;

@Controller
public class FileDisplayController {

    @Autowired
    private PropertiesUtil propertiesUtil;
    
    @Autowired
    private FileReaderUtil fileUtil;

    @Autowired
    private PPSSftpConfig ppsConfig;
    
    
    @Autowired
    private  BankInterfaceValidator validator; 
    
    private final static String PROCESSED_FILES_FOLDER = "old-files/";
    private final static String UNPROCESS_FILES_FOLDER = "failed-files/";
    private final static String LANDING_VIEW = "/fileDisplay", UPLOAD_VIEW="redirect:/uploadStatus";
    
    /**
     * 
     * @param model
     * @return
     */
    @GetMapping("/viewFile")
    public String readAndDisplayLatestFiles(Model model) {
        try {
        	model.addAttribute("fileStatus","Uploaded Files View");
        	loadView(model);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return LANDING_VIEW;
    }
    
	/**
	 * @param model
	 * @throws Exception
	 */
	private void loadView(Model model) throws Exception {
		String ddOutputFolder = propertiesUtil.getNabMessageDDOutput();
		File ddLatestFile = fileUtil.getLatestFile(ddOutputFolder);
		
		if(ddLatestFile != null){
			List<String> ddFileContent = fileUtil.readContentFromFile(ddLatestFile);
			if(!ddFileContent.isEmpty()){
				model.addAttribute("ddFileContent", ddFileContent);
				model.addAttribute("ddFolderPath", ddOutputFolder);
				model.addAttribute("ddFilename", ddLatestFile.getName());
			}
		}
		
		String ccOutputFolder = propertiesUtil.getNabMessageCCOutput();
		File ccLatestFile = fileUtil.getLatestFile(ccOutputFolder);
		System.out.println( ccLatestFile );
		if(ccLatestFile != null){
			List<String> ccFileContent = fileUtil.readContentFromFile(ccLatestFile);
			if(!ccFileContent.isEmpty()){
				model.addAttribute("ccFileContent", ccFileContent);
				model.addAttribute("ccFolderPath", ccOutputFolder);
				model.addAttribute("ccFilename", ccLatestFile.getName());
			}
		}
	}

    /**
     * 
     * @param ddFilePath
     * @param ddFilename
     * @param ccFilePath
     * @param ccFilename
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/encryptFiles")
    public String encryptFile(@RequestParam("ddPath") String ddFilePath,
                              @RequestParam("ddFilename") String ddFilename,
                              @RequestParam("ccPath") String ccFilePath,
                              @RequestParam("ccFilename") String ccFilename,Model redirectAttributes) {
    	   try {
    		
    		   
    	    if(!isInvalidDetails(ddFilename,ccFilename,redirectAttributes)){
				 loadView(redirectAttributes);
    	    	 return LANDING_VIEW;	
    	    }
    	
    	    Map<String,String> results = ppsConfig.encryptFiles(new EncryptionDataBean()
    	    									  .setDdFileName(ddFilename)
    	    									  .setDdFilePath(ddFilePath)
    	    									  .setCcFileName(ccFilename)
    	    									  .setCcFilePath(ccFilePath));
    	    if(results.get("DD") != null){ 
    	    	redirectAttributes.addAttribute("encryptedDD", results.get("DD"));
    	    }
    	    if(results.get("CC") != null ){
    	    	redirectAttributes.addAttribute("encryptedCC", results.get("CC"));
    	    }
    	    redirectAttributes.addAttribute("fileStatus", "Files Encrypted Successfully");
    	 
				loadView(redirectAttributes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    return LANDING_VIEW;
    }

	/**
	 * @param redirectAttributes
	 * @throws Exception
	 */
	private boolean isInvalidDetails(String ddFilename, String ccFilename,Model redirectAttributes) throws Exception {
		
		/*
		if(StringUtils.isEmpty(ddFilename) && StringUtils.isEmpty(ccFilename)){
			redirectAttributes.addAttribute("fileStatus","Please Upload Files");
		    return false;	
		}
		
		if(validator.getCollectionDateFromHeaderForCreditCard() ){
		 	redirectAttributes.addAttribute("fileStatus","Collection Date is Invalid. Collection Date should equal today's date");
		 	return false;
		}
		
		if(validator.getCollectionDateFromHeaderForDirectDebit()){
		 	redirectAttributes.addAttribute("fileStatus","Collection Date is Invalid. Collection Date should equal today's date");
		 	return false;
		}
		*/
		return true;
		
	}

    /**
     * 
     * @param encryptedDD
     * @param encryptedCC
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/sendFileToServer")
    public String uploadFilesToServer(@RequestParam("encryptedDD") String encryptedDD, @RequestParam("encryptedCC") String encryptedCC,RedirectAttributes redirectAttributes) {
    	redirectAttributes.addFlashAttribute("uploadState", "File Upload Status");
    	
    	if(StringUtils.isEmpty(encryptedDD) && StringUtils.isEmpty(encryptedCC)){
	    	redirectAttributes.addAttribute("fileStatus","Please Encrypt Files First");
	    	try {
				loadView((Model)redirectAttributes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return LANDING_VIEW;
    	}
    	
    	String dd = propertiesUtil.getNabMessageDDOutput().concat(encryptedDD);
    	String cc = propertiesUtil.getNabMessageCCOutput().concat(encryptedCC);
    	
    	boolean isSuccess =  ppsConfig.uploadToSFTPServer(new UploadFileToSFTPServerDataBean()
       	      .setDdFileName(dd )
       	      .setDdFilePath("/DTDD") //Remote folder name for Direct Debit
       	      .setCcFileName( cc )
       	      .setCcFilePath("/DTMOTO")); //Remote folder for Credit Card
    	
        if(isSuccess){
        	redirectAttributes.addFlashAttribute("message", "Files have been sent to the bank Successfully");
        	moveProcessedFiles(encryptedDD, propertiesUtil.getNabMessageDDOutput(),PROCESSED_FILES_FOLDER);
        	moveProcessedFiles(encryptedCC, propertiesUtil.getNabMessageCCOutput(),PROCESSED_FILES_FOLDER);
        }else{
        	redirectAttributes.addFlashAttribute("message", "Please Contact System Support for assistance");
        	redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload files to NAB... ");
        	moveProcessedFiles(encryptedDD, propertiesUtil.getNabMessageDDOutput(),UNPROCESS_FILES_FOLDER);
        	moveProcessedFiles(encryptedCC, propertiesUtil.getNabMessageCCOutput(),UNPROCESS_FILES_FOLDER);
        }
        return UPLOAD_VIEW;
    }
    
    
    private final void moveProcessedFiles(String fileName, String path, String outputFolder){
    	if(StringUtils.isEmpty(fileName) || StringUtils.isEmpty(path) ){
    		return;
    	}
    	try{
			File dir = new File(path);
			String[] _tmpFileName  = fileName.split("_");
			if(dir.isDirectory()){
				File[] files = dir.listFiles();
				
				Stream<File> fileStream = Arrays.stream(files);
				boolean isContainsName = fileStream.anyMatch( value -> value.getName().contains(_tmpFileName[0]));
				
				if(isContainsName){
					
				}
				
				
				/*
				for (File file : files) {
					if(file.getName().contains(_tmpFileName[0])){
						String strNewFile = path.concat(outputFolder).concat(file.getName());
						Path moveFrom  = FileSystems.getDefault().getPath(file.getAbsolutePath());
						Path processedDir = FileSystems.getDefault().getPath(strNewFile);
						Files.move(moveFrom, processedDir);
					}
				}
				*/
			}
			
    	} catch (IOException e) {
    		e.printStackTrace();
		}	
    }
    
}
