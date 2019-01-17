/**
 * 
 */
package za.co.pps.bank.mutual.common.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 03 Apr 2018
 * @version 1.0
 */
public class NABMessageCCHeaderDTO {

	private String recordType; 
	private String batchFileVersion;
	private String clientID;
	private String fileCreationDate;
	private String recordCount;
	private  Date collectioDate;
	
	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}
	/**
	 * @param recordType the recordType to set
	 */
	public NABMessageCCHeaderDTO setRecordType(String recordType) {
		this.recordType = recordType;
		return this;
	}
	/**
	 * @return the batchFileVersion
	 */
	public String getBatchFileVersion() {
		return batchFileVersion;
	}
	/**
	 * @param batchFileVersion the batchFileVersion to set
	 */
	public NABMessageCCHeaderDTO setBatchFileVersion(String batchFileVersion) {
		this.batchFileVersion = batchFileVersion;
		return this;
	}
	/**
	 * @return the clientID
	 */
	public String getClientID() {
		return clientID;
	}
	/**
	 * @param clientID the clientID to set
	 */
	public NABMessageCCHeaderDTO setClientID(String clientID) {
		this.clientID = clientID;
		return this;
	}
	/**
	 * @return the fileCreationDate
	 */
	public String getFileCreationDate() {
		return fileCreationDate;
	}
	/**
	 * @param fileCreationDate the fileCreationDate to set
	 */
	public NABMessageCCHeaderDTO setFileCreationDate(String fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
		getCollectionDate();
		return this;
	}
	/**
	 * @return the recordCount
	 */
	public String getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount the recordCount to set
	 */
	public NABMessageCCHeaderDTO setRecordCount(String recordCount) {
		this.recordCount = recordCount;
		return this;
	}

	public Date getCollectionDate(){
		
		   try {
			   collectioDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.fileCreationDate);
			   return collectioDate;
		   } catch (ParseException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   return collectioDate;
	
	}
	
	
	
}
