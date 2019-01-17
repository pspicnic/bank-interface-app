/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import za.co.pps.bank.mutual.data.DataServiceLayer;
import za.co.pps.bank.mutual.logic.CreateNABTransactionLayer;
import za.co.pps.bank.mutual.logic.NABResponseHandlerService;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 19 Oct 2017
 * @version 1.0
 */
@Transactional(propagation=Propagation.MANDATORY)
@Component
public class TimerServiceHelper {
	

	@Autowired
	private CSVReader csvReader;
	@Autowired 
	private CSVWriter csvWriter;
	
	@Autowired 
	private DataServiceLayer dataLayer;
	
	@Autowired
	private BankInterfaceValidator validator;
	
	@Autowired
	private CreateNABTransactionLayer messageCreater;
	
	@Autowired
	private NABResponseHandlerService nabResponseService;
	
	@Autowired
	private PropertiesUtil propUtil;
	
	@Value("${spring.profiles.active}")
	private String profileActive; 
	  
	
	void processInputFile(BankInterfaceContext context,File file) throws FileNotFoundException, Exception {
		
		File exceptionFile = new File(propUtil.getExecptionLocation()+"Exception.csv");
		if(exceptionFile.exists()){
			exceptionFile.delete();
		}
		
		List<List<String>> readCSVFile = csvReader.readCSVFile(file);
		for (List<String> entry : readCSVFile) {
			if(!validator.validateBankDetails(entry)){
				//csvWriter.writeLine(w, values); // Write to exception location 
			}
			dataLayer.processDataEntryies(entry);
		}
		
		dataLayer.collateRelatedData(); //Collate the data and insert to database
		
		for(BankAccountTypesEnum type : BankAccountTypesEnum.values()){
			if("Debit Order".equalsIgnoreCase(type.getAccountType())){
			    messageCreater.createNABMessageByTypeOfAccount(context, propUtil.getNabMessageDDOutput(), type.getAccountType());
			}
			if("Credit Card".equalsIgnoreCase(type.getAccountType())){
			    messageCreater.createNABMessageByTypeOfAccount(context,propUtil.getNabMessageCCOutput(), type.getAccountType());
			}
		}
		
		if(moveFileToProcessedFolder(file)){
			//do something in here if the file is not renamed and moved
		}
	}
	
	public void processAcknowledgements()throws Exception{
		nabResponseService.processAcknowledgements();
	}	

	public  void processExceptionFolder(){
		throw new UnsupportedOperationException();
	}
	
	public void executeCMDLines(){
		throw new UnsupportedOperationException();
	}
	
	private boolean  moveFileToProcessedFolder(File file) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		try {
		
			String nabRequestFiles = "nabRequestFiles/";
			String strNewFile =  propUtil.getProcessedFileOutput()+nabRequestFiles+"Processed"+"_"+format.format(new java.util.Date())+"_"+file.getName();

			Path moveFrom  = FileSystems.getDefault().getPath(file.getAbsolutePath());
			Path processedDir = FileSystems.getDefault().getPath(strNewFile);
		
			Files.move(moveFrom, processedDir);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}	

}