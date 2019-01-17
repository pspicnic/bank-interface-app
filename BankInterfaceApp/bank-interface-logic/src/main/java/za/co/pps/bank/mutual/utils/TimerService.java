package za.co.pps.bank.mutual.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import za.co.pps.bank.mutual.data.DataServiceLayerImpl;
import za.co.pps.bank.mutual.logic.CreateNABTransactionLayer;
import za.co.pps.bank.mutual.logic.NABResponseHandlerService;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;
import za.pps.bank.sftp.main.PPSSftpConfig;
import za.pps.bank.sftp.main.SftpConfig;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service
public class TimerService {

	private static final Logger LOG = LoggerFactory.getLogger(TimerService.class);
	
	@Autowired
	private PropertiesUtil propUtil;
	
	@Autowired
	private SftpConfig config;
	
	@Autowired
	private PPSSftpConfig ppsConfig;
	
	@Autowired
	private TimerServiceHelper helper;
	
	private BankInterfaceContext context = new BankInterfaceContext();

	
	@Scheduled(fixedRateString="${spring.boot.schedule.rate}")
	public void procesInputFile()throws Exception{	
		System.out.println("Running =============== new");
		
		File file = new File(propUtil.getTbInputLocation());
		File[] tmpFiles = file.listFiles();

		
		if(tmpFiles == null || tmpFiles.length == 0){
			return;
		}
		
		for (File f : tmpFiles) {
			System.out.println( f.getName());
			if(f.isFile() && f.getName().equalsIgnoreCase( propUtil.getInputFileName().trim() )){
				try {
					helper.processInputFile(context,f);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
					throw new Exception("Cannot read file supplied",ex);
				}
			}
		}
	}

	//@Scheduled(fixedRateString="${spring.boot.schedule.test.rate}")
	public void processAcknowledgements()throws Exception{
		helper.processAcknowledgements();
	}	

	//@Scheduled(fixedRateString="1000")//"${spring.boot.schedule.test.rate}")
	public  void processExceptionFolder(){
		//process and send email with the attachment
	}
	
	//@Scheduled(fixedRateString="1000")//"${spring.boot.schedule.test.rate}")
	public void executeCMDLines()throws Exception{
		
	}
	
}
