/**
 * 
 */
package za.co.pps.bank.mutual.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import za.co.pps.bank.mutual.utils.PropertiesUtil;


/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 23 Oct 2017 
 * @version 1.0
 */
@Controller
public class UploadController {

	private final static String INPUT_FILE_NAME = "Book3.csv";
	private final static String LINUX = "/var/pps-mutual-bank-interface/input/";
	private final static String WINDOWS = "C:/var/bank-interface/";
	
	@Autowired
	private PropertiesUtil prop;
	
	@GetMapping("/login")
    public String login() {
        return "/login";
    }
	
	@GetMapping("/")
	public String upload(){
		return "/login";
	}
	
	@GetMapping("/upload")
	public String upload1(){
		return "/upload";
	}
	
	@GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "/uploadStatus";
    }
	
	@GetMapping("/403")
	public String error403() {
	   return "/error/403";
	}
	
	
	
	@PostMapping("/uploadFile") 
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
		doCleanUp();
		
		//This method is up for discussion
		/*if(checkIfFilesNeedToBeSubmitted()){
			redirectAttributes.addFlashAttribute("message", "Please submit previously added files first to continue");
			return "redirect:uploadStatus";
		}
		*/
		
		if(file.isEmpty()){
			redirectAttributes.addFlashAttribute("message", "Please select a file to Upload");
			return "redirect:uploadStatus";
		}
		
		try {
		
			byte[] fileBytes = file.getBytes();
			
			//String filePath = (SystemUtils.IS_OS_WINDOWS) ? WINDOWS : LINUX;
			String filePath = prop.getTbInputLocation();
			Path path = Paths.get(filePath+INPUT_FILE_NAME);
			
			Files.write(path, fileBytes);

			redirectAttributes.addFlashAttribute("message","File has been successfully uploaded to Server");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message","Error while encrypting: "+e.getMessage());

		}

		return "redirect:/uploadStatus";
	}
	
	
	/**
	 * This methos is used for cleaning-up resorces before processing
	 */
	private void doCleanUp(){
		refreshFiles(prop.getExecptionLocation().concat("Exception.csv"), prop.getInputFileName().concat("Book3.csv"));
		moveProcessedFiles(prop.getNabMessageDDOutput());
		moveProcessedFiles(prop.getNabMessageCCOutput());
	}
	
	private void refreshFiles(String...path){
		for(String filePath : path){
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}		
		}  
		  
	}
	
	
	private boolean checkIfFilesNeedToBeSubmitted(){
		return checkDirForFiles(prop.getNabMessageDDOutput(), prop.getNabMessageCCOutput());
	}
	
	private boolean checkDirForFiles(String...path){
		for (String stringPath : path) {
			File dir = new File(stringPath);
			if(dir.isDirectory()){
				File[] files = dir.listFiles();
			    for (File file : files) {
			    	if(file.getName().contains(".asc") || file.getName().contains("csv") ){
			    		return true;
			    	}
			    }
			}
		}
		return false;
	}
	
    private final static String PROCESSED_FOLDER = "old-files/";

	
	private final void moveProcessedFiles(String path){
	    	try{
				File dir = new File(path);
				if(dir.isDirectory()){
					File[] files = dir.listFiles();
					for (File file : files) {
						if(file.getName().contains(".csv") || file.getName().contains(".asc")){
							String strNewFile = path.concat(PROCESSED_FOLDER).concat(file.getName());
							Path moveFrom  = FileSystems.getDefault().getPath(file.getAbsolutePath());
							Path processedDir = FileSystems.getDefault().getPath(strNewFile);
							Files.move(moveFrom, processedDir);
						}
					}
				}
				
	    	} catch (IOException e) {
	    		e.printStackTrace();
			}	
	}
	

}
