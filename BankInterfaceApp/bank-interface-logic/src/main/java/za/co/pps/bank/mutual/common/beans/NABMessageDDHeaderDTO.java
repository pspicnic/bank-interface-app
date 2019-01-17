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
public class NABMessageDDHeaderDTO {
	
	private  String recordType;
	private  String reelSequenceNumber;
	private  String financialInstitution;
	private  String nameOfUserSupplyingFile;
	private  String numberOfUserSupplyingFile;
	private  String descriptionOfentriesOn;
	private  String dateOfProcessing;
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
	public NABMessageDDHeaderDTO setRecordType(String recordType) {
		this.recordType = recordType;
		return this;
	}
	/**
	 * @return the reelSequenceNumber
	 */
	public String getReelSequenceNumber() {
		return reelSequenceNumber;
	}
	/**
	 * @param reelSequenceNumber the reelSequenceNumber to set
	 */
	public NABMessageDDHeaderDTO setReelSequenceNumber(String reelSequenceNumber) {
		this.reelSequenceNumber = reelSequenceNumber;
		return this;
	}
	/**
	 * @return the financialInstitution
	 */
	public String getFinancialInstitution() {
		return financialInstitution;
	}
	/**
	 * @param financialInstitution the financialInstitution to set
	 */
	public NABMessageDDHeaderDTO setFinancialInstitution(String financialInstitution) {
		this.financialInstitution = financialInstitution;
		return this;
	}
	/**
	 * @return the nameOfUserSupplyingFile
	 */
	public String getNameOfUserSupplyingFile() {
		return nameOfUserSupplyingFile;
	}
	/**
	 * @param nameOfUserSupplyingFile the nameOfUserSupplyingFile to set
	 */
	public NABMessageDDHeaderDTO setNameOfUserSupplyingFile(String nameOfUserSupplyingFile) {
		this.nameOfUserSupplyingFile = nameOfUserSupplyingFile;
		return this;
	}
	/**
	 * @return the numberOfUserSupplyingFile
	 */
	public String getNumberOfUserSupplyingFile() {
		return numberOfUserSupplyingFile;
	}
	/**
	 * @param numberOfUserSupplyingFile the numberOfUserSupplyingFile to set
	 */
	public NABMessageDDHeaderDTO setNumberOfUserSupplyingFile(String numberOfUserSupplyingFile) {
		this.numberOfUserSupplyingFile = numberOfUserSupplyingFile;
		return this;
	}
	/**
	 * @return the descriptionOfentriesOn
	 */
	public String getDescriptionOfentriesOn() {
		return descriptionOfentriesOn;
	}
	/**
	 * @param descriptionOfentriesOn the descriptionOfentriesOn to set
	 */
	public NABMessageDDHeaderDTO setDescriptionOfentriesOn(String descriptionOfentriesOn) {
		this.descriptionOfentriesOn = descriptionOfentriesOn;
		return this;
	}
	/**
	 * @return the dateOfProcessing
	 */
	public String getDateOfProcessing() {
		return dateOfProcessing;
	}
	/**
	 * @param dateOfProcessing the dateOfProcessing to set
	 */
	public NABMessageDDHeaderDTO setDateOfProcessing(String dateOfProcessing) {
		this.dateOfProcessing = dateOfProcessing;
		getCollectionDate();
		return this;
	}

	public Date getCollectionDate(){
	
	   try {
		   collectioDate = new SimpleDateFormat("ddMMyy").parse(this.dateOfProcessing);
		   return collectioDate;
	   } catch (ParseException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   return collectioDate;
	}
	
}
