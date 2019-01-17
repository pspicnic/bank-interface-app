package za.co.pps.bank.mutual.validation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import za.co.pps.bank.mutual.data.model.CollectionsDTO;
import za.co.pps.bank.mutual.utils.FileReaderUtil;
import za.co.pps.bank.mutual.utils.PropertiesUtil;
import za.co.pps.bank.mutual.validation.client.CreditCardValidation;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */
@Service
public class BankInterfaceValidator {
	
  @Autowired
  PropertiesUtil propertiesUtil;

  @Autowired
  private FileReaderUtil fileUtil;
  
  @Autowired 
  CreditCardValidation creditCardValidation;
	

  public CreditCardValidation getCreditCardValidation(){
	  return creditCardValidation;
  }
  
	
  public boolean validateBankDetails(List<String> entry){
	  return false;
  }
  
  /**
   * This operatin checks if the entry is not a Header entry
   * @param entry
   * @return Boolean
   */
  public boolean validateIsFirstRowInFile(List<String> entry){
	  
	  /*
		 * [Member_Number, Member_Name, Benefit_Number, Benefit_Kind_Description, Benefit_Status, Billing_Benefit_Status, 
		 *  CurrencyCode, AccountID, AccountName, OpeningBalance, CrMovement, DrMovement, ClosingBalance,
		 *  Anniversary_Date, Monthly_Premium, Billraised, Previous_Monthly_Premium, Aging_Months,
		 *  Frequency, Payment_Method, Collection_Anniversary_Date, Collection_Executed_Date, Collection_date,
		 *  Previous_Month_Collection, Premium_Waiver_Scheduler, Executed_Date, Request_Type, AgeNextBirthday, 
		 *  Policy_Start_Date, Collection_Failure_Reason, Account_Holder_Name, Bank, Account_Number, Payment Frequency, First payment date, 
		 *  Last payment date, NAB Connect ID, Collection amount on NAB,  Yearly amount ,  Difference ]
		 * 
		 */
	  if( entry.get(0).equalsIgnoreCase("Member_Number")
			   || entry.get(1).equalsIgnoreCase("Benefit_number") 
			   || entry.get(2).equalsIgnoreCase("Benefit_Kind_Description") 
			   || entry.get(3).equalsIgnoreCase("Benefit_Status") 
			   || entry.get(4).equalsIgnoreCase("Billing_Benefit_Status") 
			   || entry.get(5).equalsIgnoreCase("CurrencyCode") 
			   || entry.get(19).equalsIgnoreCase("NULL") //If payment mehod is null then ignore the entry 
		){
				return Boolean.TRUE;
		}
	  
	  if( StringUtils.isEmptyOrWhitespace(entry.get(0))
			   || StringUtils.isEmptyOrWhitespace(entry.get(1))  //If payment mehod is null then ignore the entry 
		){
				return Boolean.TRUE;
		}
	  return Boolean.FALSE;
  }
	
   public  List<String> validateCollationData(CollectionsDTO collectionsDTO) {
	   		
	   List<String> results = new  ArrayList<String>();  
       
       if(StringUtils.isEmptyOrWhitespace(collectionsDTO.getBank())){
    	   results.add("Bank Name");
       }
       
       if(StringUtils.isEmptyOrWhitespace(collectionsDTO.getAccount_number())){
    	   results.add("Account Number");
       }
       
       if(StringUtils.isEmptyOrWhitespace(collectionsDTO.getCollection_date())){
    	   results.add("Collection Date");
       }
       
       if(StringUtils.isEmptyOrWhitespace(collectionsDTO.getPayment_method())){
    	   results.add("PaymentMethod");
       }
       
       if(collectionsDTO.getPayment_method().equalsIgnoreCase("Debit Order")){
    	   if(StringUtils.isEmptyOrWhitespace(collectionsDTO.getBsb_number())){
        	   results.add("BSB Number");
           }
       }else{
    	   if(StringUtils.isEmptyOrWhitespace(collectionsDTO.getCredit_card_expiry_date())){
        	   results.add("Credit Card Expiry Date");
           }
       }
       	return results;
   }
   
   
   public boolean getCollectionDateFromHeaderForDirectDebit(){
		
	   try{
			String ddOutputFolder = propertiesUtil.getNabMessageDDOutput();
			File ddLatestFile = fileUtil.getLatestFile(ddOutputFolder);
		 
			List<String> ddFileContent = fileUtil.readContentFromFile(ddLatestFile);
			String[] str = ddFileContent.get(0).split(" ");
		
			SimpleDateFormat format = new SimpleDateFormat("ddMMyy");

			Date _date = format.parse(str[str.length-1]);
	    	Date _systemDate = new Date();
		
			if(_date.getYear() == _systemDate.getYear() && _date.getMonth() == _systemDate.getMonth() && _date.getDate() == _systemDate.getDate()){
				return false;
			}
		
   		} catch (Exception e) {
	   		e.printStackTrace();
		}
		return true;
		
	    
	}

	public boolean getCollectionDateFromHeaderForCreditCard(){
		
		try {
			String _ccOutputFolder = propertiesUtil.getNabMessageCCOutput();
			File _ccLatestFile = fileUtil.getLatestFile(_ccOutputFolder);
			
			if(_ccLatestFile == null){
				return false;
			}
		 
			List<String> _ddFileContent;
		
			_ddFileContent = fileUtil.readContentFromFile(_ccLatestFile);
		
			String[] _str = _ddFileContent.get(0).split(",");
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date _date = format.parse(_str[3]);
			Date _systemDate = new Date();
		
			if(_date.getYear() == _systemDate.getYear() && _date.getMonth() == _systemDate.getMonth() && _date.getDate() == _systemDate.getDate()){
				return false;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
   
}
