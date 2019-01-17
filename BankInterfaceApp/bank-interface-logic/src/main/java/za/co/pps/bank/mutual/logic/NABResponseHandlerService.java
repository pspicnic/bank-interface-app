/**
 * 
 */
package za.co.pps.bank.mutual.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.pps.bank.mutual.data.DataServiceDAO;
import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;
import za.co.pps.bank.mutual.utils.NABTransactionStatusEnum;
import za.co.pps.bank.mutual.utils.PropertiesUtil;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 14 Sep 2017
 * @version 1.0
 */
@Service 
@SuppressWarnings({ "unused", "unused" })
public class NABResponseHandlerService {
     
	enum STATUS{
		OLD,NEW,UUID;
	}
	
	@Autowired
	private DataServiceDAO dataService;
	@Autowired
	private PropertiesUtil propUtil;
	
	public void processAcknowledgements()throws Exception{
		File acknowledgementFolder;
		String ackNowledgementLocation = "acknowledgements/";

		if(propUtil.getStatus_dd_folder() != null){
			acknowledgementFolder = new File(propUtil.getStatus_dd_folder());//The property will always point to a folder
			if(acknowledgementFolder.isDirectory()){
				File[] acks = acknowledgementFolder.listFiles();
				for (File file : acks) {
					boolean isFileProcessed = false;
					if(file.isDirectory()){
						continue;
					}
					
					List<CollationDTO> collationList = null;
					
					Map<String,String> status = getStatusFromAckFile(file);
					collationList = dataService.getCollationDAO().findCollationsByUUID(status.get(STATUS.UUID.name()));

					for(CollationDTO collation : collationList){
						collation.setTransaction_status(status.get(STATUS.NEW.name()));
					
						List<CollectionsDTO> collectionList = dataService.getCollectionDAO().findCollectionsByCollationId(collation.getId());
						for (CollectionsDTO collectionDTO : collectionList) {
							collectionDTO.setStatus(status.get(STATUS.NEW.name()));
							collectionDTO.setStatus_modified_date(new java.util.Date());
						}
						dataService.getCollectionDAO().save(collectionList);
						isFileProcessed = true;
					}
					
					if(isFileProcessed){
						dataService.getCollationDAO().save(collationList);
						moveFileToProcessedFolder(file, ackNowledgementLocation);		
					}
								
				}
			}
		}
	}
	
	
	private Map<String,String> getStatusFromAckFile(File file) {
		Map<String,String> status = new HashMap<String, String>();
		String[] uuid = file.getName().split("_");    
		
		
		if(file.getName().contains(NABTransactionStatusEnum.ACCEPTED.getValue())){
			status.put(STATUS.NEW.name(), NABTransactionStatusEnum.ACCEPTED.getValue());
			status.put(STATUS.UUID.name(), uuid[0]);
		}
		
		if(file.getName().contains(NABTransactionStatusEnum.PROCESSED.getValue())){
			status.put(STATUS.NEW.name(), NABTransactionStatusEnum.PROCESSED.getValue());
			status.put(STATUS.UUID.name(), uuid[0]);
		}
		
		if(file.getName().contains(NABTransactionStatusEnum.REJECTED.getValue())){
			status.put(STATUS.NEW.name(), NABTransactionStatusEnum.REJECTED.getValue());
			status.put(STATUS.UUID.name(), uuid[0]);
		}
		
		if(file.getName().contains(NABTransactionStatusEnum.PENDING.getValue())){
			status.put(STATUS.NEW.name(), NABTransactionStatusEnum.PENDING.getValue());
			status.put(STATUS.UUID.name(), uuid[0]);
		}
		
		return status;
	}
	
	private void moveFileToProcessedFolder(File file,String ackNowledgementLocation) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String strNewFile =  propUtil.getProcessedFileOutput()+ackNowledgementLocation+"Processed"+"_"+format.format(new java.util.Date())+"_"+file.getName();

	    Path moveFrom  = FileSystems.getDefault().getPath(file.getAbsolutePath());
		Path processedDir = FileSystems.getDefault().getPath(strNewFile);
		Files.move(moveFrom, processedDir);
	}
	
}
