/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import static za.co.pps.bank.mutual.utils.StringUtils.padLeft;
import static za.co.pps.bank.mutual.utils.StringUtils.padRight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import za.co.pps.bank.mutual.common.beans.NABMessageCCHeaderDTO;
import za.co.pps.bank.mutual.common.beans.NABMessageDDHeaderDTO;
/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 28 Aug 2017
 * @version 1.0
 */
@Component
public class NABMessageCreater {
	
	public static NABTransactionTotalsDD nabTransactionTotals;

	public NABMessageCreater(){}
	
	private final static class NABMessageTemplate{//Please make sure that the length of the strings are both 60 long.
		private final static String template1="                                                            ";
		public final static String template = "                                                            ".concat(template1);//The spacing should be 120;
		public final static String test = "                                                                                                                        ";
	}
	
	 public final  class NABHeaderMessageDD{
		
		private  String recordType;
		private  String reelSequenceNumber;
		private  String financialInstitution;
		private  String nameOfUserSupplyingFile;
		private  String numberOfUserSupplyingFile;
		private  String descriptionOfentriesOnFile;
		private  String dateOfProcessing;
		private  String[] values;
		
		
		public  void setValues(String...values ){
			 this.values = values;
			 recordType = values[0];
			 reelSequenceNumber = values[1];
			 financialInstitution = values[2];
			 nameOfUserSupplyingFile = values[3];
			 numberOfUserSupplyingFile=values[4];
			 descriptionOfentriesOnFile=values[5];
			 dateOfProcessing=values[6];
		}
		
		public String[] getValues(){
			return this.values;
		}
		
		public  String  createNABHeaderMessage(){
			
			StringBuilder builder = new StringBuilder(NABMessageTemplate.template);
			
			builder.replace(0, 0, recordType);
			builder.replace(18, 19, padLeft(reelSequenceNumber,2).replace(" ", "0"));
			builder.replace(20, 22, financialInstitution);
			builder.replace(30, 55, nameOfUserSupplyingFile);
			builder.replace(56, 61, numberOfUserSupplyingFile);
			builder.replace(62, 73, descriptionOfentriesOnFile);
			builder.replace(74, 79, dateOfProcessing);
		
			builder.setLength(NABMessageTemplate.template.length());

			return builder.toString();
		}	
		
		
		public NABMessageDDHeaderDTO createNABHeaderMessageObjectBean(String headerDetails){
			return new NABMessageDDHeaderDTO()
					.setRecordType(headerDetails.substring(0, 1).trim())
					.setReelSequenceNumber(headerDetails.substring(18, 20).trim())
					.setFinancialInstitution(headerDetails.substring(20, 23).trim())
					.setNameOfUserSupplyingFile(headerDetails.substring(30, 56).trim())
					.setNumberOfUserSupplyingFile(headerDetails.substring(56, 62).trim())
					.setDescriptionOfentriesOn(headerDetails.substring(62, 74).trim())
					.setDateOfProcessing(headerDetails.substring(74, 80).trim());
		}
		
	}//End of class 
	
	
	public final class NABTransactionTotalsDD{
		
		private String recordType;
		private String bsbNumber;
		private String netTotalAmount;
		private String creditTotalAmount;
		private String debitTotalAmount;
		private String fileCountOfRecordType;
		private String[] values; 
		
		public void setValues(String...values){
			this.values = values;
			recordType = values[0];
			bsbNumber = values[1];
			netTotalAmount = getAmountInCents(values[2]);
			creditTotalAmount = getAmountInCents( values[3]);
			debitTotalAmount = getAmountInCents(values[4]);
			fileCountOfRecordType = values[5];
		}
		
		public String[] getValues(){
			return this.values;
		}
		
		public String createNABTotalsMessage(){
			
			StringBuilder builder = new StringBuilder(NABMessageTemplate.template);
			
			builder.replace(0,0, recordType);
			builder.replace(1, 7, bsbNumber);
			builder.replace(20, 29, padLeft(netTotalAmount, 10).replace(" ","0"));//formatStr(10,padLeft(netTotalAmount,10),"0"));
			builder.replace(30, 39, padLeft(creditTotalAmount, 10).replace(" ","0"));//formatStr(10,padLeft(creditTotalAmount,10),"0"));
			builder.replace(40, 49, padLeft(debitTotalAmount, 10).replace(" ","0"));//formatStr(10,padLeft(debitTotalAmount,10),"0"));
			builder.replace(74, 79, padLeft(fileCountOfRecordType, 6).replace(" ","0"));//formatStr(6,padLeft(fileCountOfRecordType,10),"0"));
			
			builder.setLength(NABMessageTemplate.template.length());
			return builder.toString();
		}
		
	}//End of class
	
	
	public final class NABDirectEntryMessageDD{
		
		private  String recordType;
		private  String bsbNumber;
		private  String accountNumber;
		private  String indicator;
		private  String transactionCode;
		private  String amount;
		private  String titleOfAccount;
		private  String lodgementReference;
		private  String bsbNumberInFormat;
		private  String accountNumberTrace;
		private  String nameOfRemitter;
		private  String amountOfWithholdingTax;
		
		private String[] values; 

		public  void setValues(String...values ){
			 this.values = values;
			 recordType = values[0];
			 bsbNumber = values[1];
			 accountNumber = values[2];
			 indicator = values[3];
			 transactionCode=values[4];
			 amount=getAmountInCents(values[5]);
			 titleOfAccount=values[6];
			 lodgementReference=values[7];
			 bsbNumberInFormat=values[8];
		     accountNumberTrace=values[9];
			 nameOfRemitter=values[10];
			 amountOfWithholdingTax=values[11];
		}
		
		public String[] getValues(){
			return this.values;
		}
		
		public String createDiretEntryMessage(){
			StringBuilder builder = new StringBuilder(NABMessageTemplate.template);
			
			builder.replace(0, 0,   recordType);
			builder.replace(1, 7,   bsbNumber);
			builder.replace(8, 16,  padLeft(accountNumber, 9));
			builder.replace(17,17,  padLeft(indicator,1));
			builder.replace(18,19,  padLeft(transactionCode,2));
			builder.replace(20,29,  padLeft(amount,10).replace(" ", "0"));
			builder.replace(30,61,  padRight(titleOfAccount,32));
			builder.replace(62,79,  padRight(lodgementReference,18));
			builder.replace(80,86,  padLeft(bsbNumberInFormat,7));
			builder.replace(87,95,  padLeft(accountNumberTrace,9));
			builder.replace(96,111, padRight(nameOfRemitter,16));
			
			StringBuilder str = new StringBuilder(padLeft(amountOfWithholdingTax,8));
			str.setLength(8);
			builder.replace(112,120,str.toString().replaceAll(" ", "0"));
			builder.setLength(NABMessageTemplate.template.length());

			return builder.toString();
		}
		
	}
	
	//////////// End of Direct Debit 
	public final class NABHeaderMessageCC{
		
		private String recordType; 
		private String batchFileVersion;
		private String clientID;
		private String fileCreationDate;
		private String recordCount;
		
		public void setValues(String...values){
			this.recordType = values[0];
			this.batchFileVersion = values[1];
			this.clientID = values[2];
			this.fileCreationDate = values[3];
			this.recordCount = values[4];
		}
		
		public List<String> createEntries(){
			List<String> entries = new ArrayList<String>();
			
			entries.add(0,this.recordType);
			entries.add(1,this.batchFileVersion);
			entries.add(2,this.clientID);
			entries.add(3,this.fileCreationDate);
			entries.add(4,this.recordCount);

			return entries;
		}

		public NABMessageCCHeaderDTO createNABHeaderMessageObjectBean(String headerDetails){
			
			String[] header = headerDetails.replace("[", "").replace("]", "").trim().split(","); 
			
			return new NABMessageCCHeaderDTO()
					.setRecordType(header[0].trim())
					.setBatchFileVersion(header[1].trim())
					.setClientID(header[2].trim())
					.setFileCreationDate(header[3].trim())
					.setRecordCount(header[4].trim());
					
		}
		
	}
	 
	public final class NABDirectEntryMessageCC{
		
		private String recordType; 
		private String subAccount; 
		private String electronicBankingNumber; 
		private String transactionType; 
		private String transactionSource; 
		private String customerTransactionReference; 
		private String cardNumber; 
		private String expiryDate; 
		private String currency; 
		private String transactionAmount; 
		private String originalBankTransactionID; 
		private String cardholderName; 
		
		public void setValues(String...values){
			this.recordType = values[0];
			
			//This data u  populate one of the two, either the  
			this.subAccount = values[1];
			this.electronicBankingNumber= values[1]; 
			
			this.transactionType= values[2]; 
			this.transactionSource= values[3]; 
			this.customerTransactionReference= values[4];
			this.cardNumber= values[5]; 
			this.expiryDate= values[6]; 
			this.currency= values[7]; 
			this.transactionAmount= getAmountInCents(values[8]);
			this.originalBankTransactionID= values[9]; 
			this.cardholderName = values[10].substring(0, Math.min(values[10].length(), 25));
		}
		
		public List<String> createEntries(){
			List<String> entries = new ArrayList<String>();
			entries.add(0,this.recordType);
			
			//You populate either one of the two fields
			//entries.add(1,this.subAccount);
			entries.add(1,this.electronicBankingNumber);
			
			entries.add(2,this.transactionType);
			entries.add(3,this.transactionSource);
			entries.add(4,this.customerTransactionReference);
			entries.add(5,this.cardNumber);
			entries.add(6,this.expiryDate);
			entries.add(7,this.currency);
			entries.add(8,this.transactionAmount);
			entries.add(9,this.originalBankTransactionID);
			entries.add(10,this.cardholderName);
			
			return entries;
		}
	}
	
	public final class NABTransactionTotalsCC{
		private String recordType; 
		private String currency; 
		private String detailRecordCount; 
		private String totalCreditAmount; 
		private String totalDebitAmount; 
		private String totalPreAuthorisationAmount; 
		
		private String value1;
		private String value2;
		private String value3;
		
		public void setValues(String...values){
			this.recordType = values[0];
			this.currency=values[1]; 
			this.detailRecordCount=values[2];
			this.totalCreditAmount=getAmountInCents(values[3]); 
			this.totalDebitAmount=getAmountInCents(values[4]);
			this.totalPreAuthorisationAmount=getAmountInCents(values[5]); 
			
			//this.value1 = getAmountInCents(values[6]);
			//this.value2 = getAmountInCents(values[7]);
			//this.value3 = getAmountInCents(values[8]);
			
		}
		
		public List<String> createEntries(){
			List<String> entries = new ArrayList<String>();
			entries.add(0,this.recordType);
			entries.add(1,this.currency);
			entries.add(2,this.detailRecordCount);
			entries.add(3,this.totalCreditAmount);
			entries.add(4,this.totalDebitAmount);
			entries.add(5,this.totalPreAuthorisationAmount);
		//	entries.add(6,this.value1);
		//	entries.add(7,this.value2);
		//	entries.add(8,this.value3);
			return entries;
		}
		
		public void createDataFromDetails(List<List<String>> data){
			
		}
	}
  
	 /////////////////// End of Credit Card
	
	public enum RecordType{
		
		HEADER("0"),DETAILS("1"),DD_TOTALS("7"),CC_TOTALS("9");
		
		RecordType(String args){
			this.type = args;
		}
		private String type;
		
		public String getType(){
			return this.type;
		}
	}
	
	public enum TransactionType{
		
		PAY("Payment"),
		REF("Refund"),
		COM("Completion"),
		PRE("Preauthorisation"),
		REC("Recurring"),
		UNR("Unmatched Refund"),
		UNC("Unmatched Completion");
		
		TransactionType(String args){
			this.type = args;
		}
		private String type;
		
		public String getType(){
			return this.type;
		}
	}
	public enum CurrencyEnum{
		
		AUD("AUD");
		
		CurrencyEnum(String args){
			this.curr = args;
		}
		private String curr;
		
		public String getCurrency(){
			return this.curr;
		}
		
	}
	
	
	
	public String getAmountInCents(String amount){
		 return new BigDecimal(amount).multiply(new BigDecimal(100)).intValue()+"";
	}
	
	public static String formatStr(int length, String value, String filler){
		if(length == value.length()){
			return value;
		}
		
		StringBuilder str = new StringBuilder(value);
		str.setLength(length);
		String _str =  str.toString();
		_str = _str.replace(Character.MIN_VALUE, '@');
		return _str.replaceAll("@", filler);
	}
		
}