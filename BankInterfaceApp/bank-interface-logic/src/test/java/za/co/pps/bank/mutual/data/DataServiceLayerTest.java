/**
 * 
 */
package za.co.pps.bank.mutual.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import za.co.pps.AbstractTest;
import za.co.pps.bank.mutual.communication.EmailSenderImpl;
import za.co.pps.bank.mutual.data.model.CollationDAO;
import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollationHeaderDAO;
import za.co.pps.bank.mutual.data.model.CollationHeaderDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDAO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;
import za.co.pps.bank.mutual.logic.CreateNABTransactionLayer;
import za.co.pps.bank.mutual.utils.BankAccountTypesEnum;
import za.co.pps.bank.mutual.utils.BankInterfaceContext;
import za.co.pps.bank.mutual.utils.CSVReader;
import za.co.pps.bank.mutual.utils.CollationWrapper;
import za.co.pps.bank.mutual.utils.PropertiesUtil;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;
import za.co.pps.bank.mutual.validation.client.CreditCardTypes;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 21 Aug 2017
 * @version 1.0
 */
//@DataJpaTest
@Transactional//(propagation=Propagation.NOT_SUPPORTED)
@Profile(value = "local" )
@ActiveProfiles("local")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataServiceLayerTest extends AbstractTest{
	
	
	@Autowired
	CSVReader csvReader;
	
	@Autowired 
	private DataServiceLayer dataLayer;
	
	@Autowired
	BankInterfaceValidator validator;
	
	@Autowired
	private CreateNABTransactionLayer messageCreater;
	
	@Autowired
	private PropertiesUtil propUtil;
	
	@Autowired
	CollectionsDAO collectionDAO;
	
	@Autowired
	CollationDAO collationDAO;
	
	@Autowired
	CollationHeaderDAO headerDAO;
	
	@Autowired
	CollationWrapper collationWrapper;
	
	@Value("${file.input.location}")
	private String fileLocation;
	
	@Value("${file.name}")
	private String fileName;
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	EmailSenderImpl emailSender;
	

	
	private BankInterfaceContext context = new BankInterfaceContext();
	
	
	//@Before
	@Test
	@Ignore
	@Rollback(true)
	public void setUp() throws Exception{
	
	
		File file = new File(fileLocation.concat(fileName));
		
		List<List<String>> readCSVFile = csvReader.readCSVFile(file);
		for (List<String> entry : readCSVFile) {
			if(!validator.validateIsFirstRowInFile(entry)){
				//if(validator.validateEntryForNullsOrEmpty(entry)){
	 				dataLayer.processDataEntryies(entry);
	 			//}
			}
		}
		
		//emailSender.sendSimpleEmail("lsingatha@pps.co.za", "Testing", "Im just testing this shit buddy");
		//System.out.println( Arrays.asList(appContext.getBeanDefinitionNames()));
		
	}
	
	@Test
	@Rollback(value=true)///-------------------
	@Ignore
	public void test_find_related_collections_and_collate_the_data(){
		
		List<Long> list = collectionDAO.getDistinctMemberNumbers();
		//List<CollectionsDTO> dtos = new ArrayList<CollectionsDTO>();
		assertThat(list).isNotEmpty();
		for(Long memberNum : list){
			List<CollectionsDTO> collections = collectionDAO.findCollectionByMemberNumber(memberNum);
			assertThat(collections).isNotEmpty();
			//dtos.addAll(collections);
			for(CollectionsDTO collection : collections){
				assertThat(collection.getCollation_id()).isNull();
			}
			collationWrapper.getWrapper().put(memberNum,collections );
	    }
		//assertThat( dtos.size()).isEqualTo(25);
		assertThat(collationWrapper.getWrapper()).isNotEmpty();
		collationWrapper.collateObjects();
		collationWrapper.getWrapper().clear();
		
		try {
			test_create_nab_files();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	@Rollback(value=false)
	@Ignore
	public void test_validate_data(){
		assertThat( collectionDAO.findAll()).isNotEmpty();
	}

   
	@Test
	@Rollback(value=false)
	@Ignore
	public void test_custome_query_find_collections_by_account_number(){
		
		assertThat( collectionDAO.findAll()).isNotEmpty();
	
		String account_number = "5163610047354624";//Integer.parseInt("5163104000992414");//Long.parseLong("4601841500316825");
		List<CollectionsDTO> list = collectionDAO.findCollectionByAccountNumber( account_number );
        assertThat(list).isNotEmpty();
        for(CollectionsDTO collection : list){
        	assertThat(collection.getAccount_number()).isEqualTo(account_number);
        }
        
	}
	
	@Test 
	@Rollback(value=false)
	@Ignore
	public void  test_get_distinct_member_numbers(){
		
		List<Long> list = collectionDAO.getDistinctMemberNumbers();
		for(Long entry : list){
	       	System.out.println( entry.toString() );
	    }
		assertThat(list).isNotEmpty();
		
	}
	
	@Test
	@Rollback(value=false)
	@Ignore
	public void test_find_collections_by_member_number(){
		
		List<Long> list = collectionDAO.getDistinctMemberNumbers();
		assertThat(list).isNotEmpty();
		
		for(Long memberNum : list){
			Long _tmpMemberNumer = memberNum;
			List<CollectionsDTO> collections = collectionDAO.findCollectionByMemberNumber(memberNum);
			assertThat(collections).isNotEmpty();
			System.out.println("======================== : " + memberNum);
			for (CollectionsDTO collection : collections) {
		       	assertThat(collection.getMember_number()).isEqualTo(_tmpMemberNumer);
				System.out.println( collection.toString());				
			}
	    }
	}
	
	
	
	@Test
	@Rollback(value=false)///----------------------------------
	@Ignore
	//@Repeat(5)
	public void test_create_nab_files() throws Exception{
		
		 for(BankAccountTypesEnum type : BankAccountTypesEnum.values()){
		    	if("Debit Order".equalsIgnoreCase(type.getAccountType())){
				    messageCreater.createNABMessageByTypeOfAccount(context,propUtil.getNabMessageDDOutput(), type.getAccountType());
		    	}
		    	if("Credit Card".equalsIgnoreCase(type.getAccountType())){
				    messageCreater.createNABMessageByTypeOfAccount(context,propUtil.getNabMessageCCOutput(), type.getAccountType());
		    	}
		    }
	}
	
	
	@Test
	@Rollback(value=false)
	@Ignore
	public void test_find_all_collations_by_transaction_type(){
		List<CollationDTO> list = collationDAO.findCollationByAccountType(BankAccountTypesEnum.CREDIT_CARD.getAccountType().trim());
		assertThat(list).isNotEmpty();
		for(CollationDTO collation : list){
			assertThat(collation.getPayment_method()).isEqualToIgnoringCase(BankAccountTypesEnum.CREDIT_CARD.getAccountType());
		}
	}
	
	@Test
	@Ignore
	public void test_find_all_unprocessed_collations(){
		
	    List<CollationDTO> collationList = collationDAO.findUnProcessedCollations();
		assertThat(collationList).isNotEmpty();
		for(CollationDTO collation : collationList){
			assertThat(collation.getTransaction_code()).isNullOrEmpty();
			assertThat(collation.getProcessed_date()).isNull();
		}
	} 
	
	@Test
	@Ignore
	public void test_header_collation_details(){
		CollationHeaderDTO headerDTO = headerDAO.findAll().iterator().next();
		assertThat(headerDTO).isNotNull();
		System.err.println( headerDTO.toString());
	}
	
	/*
	 *  String ccNumber1 = "3482-8224-6310-0051";
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
	 * 
	 */
	
	
	
	public static void main(String...args){  ///////////////////////// Testing 
		
	}
	

	
	
}