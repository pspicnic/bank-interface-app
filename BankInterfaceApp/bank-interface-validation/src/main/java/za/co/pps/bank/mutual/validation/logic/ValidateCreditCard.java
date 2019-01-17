package za.co.pps.bank.mutual.validation.logic;

import org.springframework.stereotype.Component;

import static za.co.pps.bank.mutual.validation.client.CreditCardTypes.*;
import za.co.pps.bank.mutual.validation.client.CreditCardValidation;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Apr 2018
 * @version 1.0
 */

@Component
public class ValidateCreditCard implements CreditCardValidation{ 
    
	private static final String  ERROR_MSG =  "Your credit card number or type is incorrect, please re-enter";
	private static final String SUCCESS_MSG = "valid";
	
	@Override
    public String validateCreditCardNumber(String accountType, String ccNumber) {

        String creditCardNumber = ccNumber.replace("-", "");
        Integer[] cardDigitsArray = new Integer[creditCardNumber.length()];
        for(int i = 0; i < creditCardNumber.length(); i++) {
            cardDigitsArray[i] = Character.getNumericValue(creditCardNumber.charAt(i));
        }

        
        int numberOfDigits = cardDigitsArray.length;

        String firstDigitsStr = cardDigitsArray[0] + "" + cardDigitsArray[1];

        if (accountType == AMERICAN_EXPRESS.getType()|| accountType == AMEX.getType()) {

            if (numberOfDigits != 15) {
                return ERROR_MSG;
            }

            if ((firstDigitsStr.equals("34")) && (firstDigitsStr.equals("37"))) {
                return ERROR_MSG;
            }

        } else if (accountType == MASTER_CARD.getType()) {

            if (numberOfDigits != 16) {
                return ERROR_MSG;
            }

            int firstDigits = Integer.valueOf(firstDigitsStr);
            if ((firstDigits < 51) || (firstDigits > 55)) {
                return ERROR_MSG;
            }

        } else if (accountType == VISA.getType()) {

            if ((numberOfDigits != 13) && (numberOfDigits != 16)) {
                return ERROR_MSG;
            }

            int firstDigits = cardDigitsArray[0];
            if (firstDigits != 4) {
                return ERROR_MSG;
            }
        }

        boolean validCardNumber = validateCreditCardCheckSum(cardDigitsArray);
        if (!validCardNumber) {
            return ERROR_MSG;
        } else {
            return SUCCESS_MSG;
        }
    }

	@Override
    public boolean validateCreditCardCheckSum (Integer[] cardDigitsArray) {

        int digit;
        int total = 0;
        for (int i = 0; i < cardDigitsArray.length; i++) {
            // get digits in reverse order
            digit = cardDigitsArray[cardDigitsArray.length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            total += digit > 9 ? digit - 9 : digit;
        }
        return (total%10==0);
    }

	

}
