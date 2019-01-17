/**
 * 
 */
package za.co.pps.bank.mutual.validation;

import javax.validation.Validation;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.Validate;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 10 Sep 2017
 * @version 1.0
 */
@Component
public class NABMessageValidator {

	
	public void validateMessage(String transactionType,String message){
		
		if("0".equalsIgnoreCase(transactionType)){
			validateHeaderMessae(transactionType, message);
		}else if("1".equalsIgnoreCase(transactionType)){
			validateDetailsMessage(transactionType, message);
		}else if("7".equalsIgnoreCase(transactionType)){
			validateTotalsMessage(transactionType, message);
		}
		
	} 
	
	private void validateHeaderMessae(String transactionType,String message){
				
	}
	
	private void validateDetailsMessage(String transactionType,String message){
		if("01".equalsIgnoreCase(transactionType)){
			
		}
	} 
	
	private void validateTotalsMessage(String transactionType,String message){

		/*
		String recordType = message.substring(0,1);
		Validate
		 
		String bsbNumber;
		String netTotalAmount;
		String creditTotalAmount;
		String debitTotalAmount;
		String fileCountOfRecordType;
		*/
		
	}
	
	public static enum ValidationFieldsEnum{
		COLLECTION_DATE,BSB_NUMBER;
	}
	
	
	
}
