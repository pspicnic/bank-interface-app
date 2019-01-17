package za.co.pps.bank.mutual;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import za.co.pps.bank.mutual.validation.logic.ValidateCreditCard;

import static org.junit.Assert.assertEquals;

public class ValidateCreditCardTest extends AbstractTest{

    @Autowired
    ValidateCreditCard validateCreditCard;

    Integer[] ccArray1 = {3,7,8,2,8,2,2,4,6,3,1,0,0,0,5,1};
    Integer[] ccArray2 = {3,7,8,2,8,2,2,4,6,3,1,0,0,0,5,2};
    String ccNumber1 = "3482-8224-6310-0051";
    String ccNumber2 = "3682-8224-6310-005";
    String ccNumber3 = "3482-8224-6310-005";

    @Test
    public void testValidateCreditCardNumber() {
        assertEquals("Your credit card number or type is incorrect, please re-enter",
                validateCreditCard.validateCreditCardNumber("Amex", ccNumber1));
        assertEquals("Your credit card number or type is incorrect, please re-enter",
                validateCreditCard.validateCreditCardNumber("American Express", ccNumber2));
        assertEquals("Your credit card number or type is incorrect, please re-enter",
                validateCreditCard.validateCreditCardNumber("Amex", ccNumber3));
    }

    @Test
    public void testValidateCreditCardCheckSum() {
        assertEquals(false, validateCreditCard.validateCreditCardCheckSum(ccArray1));
        assertEquals(true, validateCreditCard.validateCreditCardCheckSum(ccArray2));
    }
}

