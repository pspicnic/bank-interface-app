/**
 * 
 */
package za.co.pps.bank.mutual.utils;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 11 Sep 2017
 * @version 1.0
 */
public enum BankAccountTypesEnum {
	
	CREDIT_CARD("Credit Card"),
	DEBIT("Debit Order"),
	BPAY("BPay"),
	VISA("Visa"),
	CHEQUE("Cheque"),
	INVALID_PAYMENT_METHOD("Invalid Payment Method");
	
	BankAccountTypesEnum(String args){
		this.accountType = args;
	}
	
	private String accountType;
	
	public String getAccountType(){
		return this.accountType;
	}
	

}
