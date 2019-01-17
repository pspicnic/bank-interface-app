/**
 * 
 */
package za.co.pps.bank.mutual.validation.client;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Apr 2018
 * @version 1.0
 */
public interface CreditCardValidation {
	
	/**
	 *
	 * @param accountType
	 * @param ccNumber
	 * @return
	 */
	abstract String validateCreditCardNumber(String accountType, String ccNumber);
	
	/**
	 * 
	 * @param cardDigitsArray
	 * @return
	 */
	abstract boolean validateCreditCardCheckSum (Integer[] cardDigitsArray);

}