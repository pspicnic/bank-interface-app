/**
 * 
 */
package za.co.pps.bank.mutual.utils;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 15 Sep 2017
 * @version 1.0
 */
public enum CollationCreationStatusEnum {
	
	SYSTEM_CREATED("Created By System"),MANUALLY_CREATED("Created By User");
	
	CollationCreationStatusEnum(String value){
		this.value = value;
	}
	private String value;

	public String getValue(){
		return this.value;
	}
		
	
	

}
