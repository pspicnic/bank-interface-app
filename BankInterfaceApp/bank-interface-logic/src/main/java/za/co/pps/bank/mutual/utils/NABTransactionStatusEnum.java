/**
 * 
 */
package za.co.pps.bank.mutual.utils;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 13 Sep 2017
 * @version 1.0
 */
public enum NABTransactionStatusEnum {
	
	SENT("SENT"),
	CREATED("CREATED"),
	ACCEPTED("ACCEPTED"),
	PROCESSED("PROCESSED"),
	REJECTED("REJECTED"),
	PENDING("PENDING");
	
	NABTransactionStatusEnum(String value){
		this.value = value;
	}
	private String value;
	
	public String getValue(){
		return this.value;
	}
}
