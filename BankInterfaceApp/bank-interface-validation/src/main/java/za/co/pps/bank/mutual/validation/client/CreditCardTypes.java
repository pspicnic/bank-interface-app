/**
 * 
 */
package za.co.pps.bank.mutual.validation.client;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 18 Apr 2018
 * @version 1.0
 */
public enum CreditCardTypes {
	

	VISA("Visa"),
	MASTER_CARD("Mastercard"),
	AMERICAN_EXPRESS("American Express"),
	AMEX("Amex");
	
	
	private CreditCardTypes( String type){
		this.type = type;
	}
	
	private  String type;

	public  String getType(){
		return type.trim();
	}
	
	public static boolean isSelectedTypeCreditCard(String type){
	    
		for (CreditCardTypes value : CreditCardTypes.values()) {
			if(type.equalsIgnoreCase(value.getType())){
				return true;
			}
		}
		return false;
	}
}

