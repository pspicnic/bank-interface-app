/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;

import za.co.pps.AbstractTest;
import za.co.pps.bank.mutual.common.beans.NABMessageCCHeaderDTO;
import za.co.pps.bank.mutual.common.beans.NABMessageDDHeaderDTO;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;
import za.co.pps.bank.mutual.validation.client.CreditCardTypes;
/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 18 April 201*
 * @version 1.0
 */
@Profile(value = "LOCAL" )
@FixMethodOrder(MethodSorters.DEFAULT)
@ActiveProfiles("LOCAL")
public class CreditCardValidationTest extends AbstractTest {
	

	@Autowired
	PropertiesUtil propertiesUtil;

	@Autowired
	BankInterfaceValidator validator;
	
	@Before
	public void setUp(){}
	

	@Test
	public void positive_test_credit_card_validation(){

		final String SUCCESS_MSG = "valid";
		
		final String amex = "340219172869682";
		final String american_ex = "376522500477152";
		final String visa16 = "4539206301145897";
		final String visa13 = "4024007156017";
		final String visaElectron = "4913720441879377";
		final String masterCard = "5168216521777168";		
		
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Amex", amex)).isEqualToIgnoringCase(SUCCESS_MSG);
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("American Express", american_ex)).isEqualToIgnoringCase(SUCCESS_MSG);

		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Visa", visa16)).isEqualToIgnoringCase(SUCCESS_MSG);
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Visa", visa13)).isEqualToIgnoringCase(SUCCESS_MSG);
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Visa", visaElectron)).isEqualToIgnoringCase(SUCCESS_MSG);
		
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Mastercard", masterCard)).isEqualToIgnoringCase(SUCCESS_MSG);

	}
	
	@Test
	public void negative_test_credit_card_validation(){

	    final String  ERROR_MSG =  "Your credit card number or type is incorrect, please re-enter";

	    final String amex = "kj;lkalja;lkjdlkjalkj";
		final String american_ex = "76522500477152".concat(" ");//Added whitespace

	    final String visa16 = "4564543645641887897";
	    final String visa13 = "33648456415676854";
	    final String visaElectron = "18954656454";
	    
	    final String masterCard = "9652fijhgfdkjhd";		
		
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Amex", amex)).isEqualToIgnoringCase(ERROR_MSG);
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("American Express", american_ex)).isEqualToIgnoringCase(ERROR_MSG);

		
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Visa", visa16)).isEqualToIgnoringCase(ERROR_MSG);
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Visa", visa13)).isEqualToIgnoringCase(ERROR_MSG);
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Visa", visaElectron)).isEqualToIgnoringCase(ERROR_MSG);
		
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber("Mastercard", masterCard)).isEqualToIgnoringCase(ERROR_MSG);

	}
	
	@Test
	public void est_credit_card_validation(){
		
	    //final String card_number = "4564807016182535";//Michael Keller
	    //String card_number = "4564680115198680"; // James Geake
	   // String card_number = "4572332011174627";//Dr Brodie J Garth
	  
	    String card_number = "5163103004462242";//Dr Lin Ye
	   // String card_number = "4572332011174627";//Dr Brodie J Garth
	    
	    //final String card_name = "Visa";
	    final String card_name = "Mastercard";
		
		assertThat(validator.getCreditCardValidation().validateCreditCardNumber(card_name, card_number)).isEqualToIgnoringCase("valid");
		
	}

	
}