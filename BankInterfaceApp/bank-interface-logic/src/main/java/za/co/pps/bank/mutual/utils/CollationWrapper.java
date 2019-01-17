/**
 * 
 */
package za.co.pps.bank.mutual.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import za.co.pps.bank.mutual.data.DataServiceDAO;
import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollationHeaderDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;
import za.co.pps.bank.mutual.utils.NABMessageCreater.CurrencyEnum;
import za.co.pps.bank.mutual.utils.NABMessageCreater.NABDirectEntryMessageCC;
import za.co.pps.bank.mutual.utils.NABMessageCreater.NABHeaderMessageCC;
import za.co.pps.bank.mutual.utils.NABMessageCreater.NABTransactionTotalsCC;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;
import za.co.pps.bank.mutual.validation.client.CreditCardTypes;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 24 Aug 2017
 * @version 1.0
 */
@Transactional(propagation=Propagation.MANDATORY)
@Component
public class CollationWrapper {
	
	@Autowired
	private DataServiceDAO dataService;
	
	@Autowired
	private BankInterfaceValidator validator;
	
	@Autowired
	private PropertiesUtil propUtil;
	
	@Autowired 
	private CSVWriter csvWriter;
	
	private CollationHeaderDTO headerDTO; 

	
	private Map<Long,List<CollectionsDTO>> collationWrapper; 

	public Map<Long,List<CollectionsDTO>> getWrapper(){
		if(this.collationWrapper == null){
			this.collationWrapper = new HashMap<Long,List<CollectionsDTO>>();
		}
		return this.collationWrapper;
	}
	
	
	@PostConstruct
	private void init(){
		 headerDTO = dataService.getCollationHeaderDAO().findAll().iterator().next();//This will always return one record
	}
	
	
	
	public void collateObjects(){
		try {
			FileWriter exceptionFileWriter = null;
			File exceptionFile = new File(propUtil.getExecptionLocation().concat("Exception.csv"));
			if(exceptionFile.exists()){
				exceptionFile.createNewFile();
			}
			exceptionFileWriter = new FileWriter(exceptionFile,true);
		
			for(Long key : this.collationWrapper.keySet()){
				
				List<CollectionsDTO> collections = null;
	        	Set<CollectionsDTO> tmpCollections = null;
	        	List<CollectionsDTO> unprocessed = null;

	        	Map<String,List<CollectionsDTO>> groupByAccountNumberMap = groupCollectionByAccountNumber((List<CollectionsDTO>)this.collationWrapper.get(key));
	        	
	        	for(Map.Entry<String, List<CollectionsDTO>> entry : groupByAccountNumberMap.entrySet()){//This code assumes that the member number,bank,Payment Method, are the same 
	        		collections = new ArrayList<CollectionsDTO>();
		        	tmpCollections = new HashSet<CollectionsDTO>();
		        	unprocessed = new ArrayList<CollectionsDTO>();
	        		for(CollectionsDTO col : entry.getValue()){
	        			collections = entry.getValue();
	        			List<String> validateCollationData = validator.validateCollationData(col);
	        			
	        			if(validateCollationData.isEmpty()){
	        				 contains_(col, collections, tmpCollections);
	        			}else{
	        				List<List<String>> parentList = new ArrayList<List<String>>();
						  	parentList.add(col.toStringList());
						    exceptionFileWriter = (FileWriter)csvWriter.writeToExceptionLog(exceptionFileWriter,col.toStringList(),validateCollationData);
						  	dataService.getCollectionDAO().save(col.setIs_collated(Boolean.FALSE.toString().toUpperCase()));
						  	unprocessed.add(col);
	        			}
	        			
	        		}
	        		
	        		for (CollectionsDTO tmpCol : unprocessed) {
						collections.remove(tmpCol);
					}
					for (CollectionsDTO tmpCol : tmpCollections) {
						collections.remove(tmpCol);
					}
					createCollationsAndUpdateCollections(key, collections, tmpCollections,exceptionFileWriter);
	        		
	        	}

	        	/*
				for (CollectionsDTO collectionsDTO : collections) {
					List<String> validateCollationData = validator.validateCollationData(collectionsDTO);
					if(validateCollationData.isEmpty()){
					  contains_(collectionsDTO, collections, tmpCollections);
				  	}else{
					  	List<List<String>> parentList = new ArrayList<List<String>>();
					  	parentList.add(collectionsDTO.toStringList());
					    exceptionFileWriter = (FileWriter)csvWriter.writeToExceptionLog(exceptionFileWriter,collectionsDTO.toStringList(),validateCollationData);
					  	dataService.getCollectionDAO().save(collectionsDTO.setIs_collated(Boolean.FALSE.toString().toUpperCase()));
					  	unprocessed.add(collectionsDTO);
				  	}
				}
			
				for (CollectionsDTO tmpCol : unprocessed) {
					collections.remove(tmpCol);
				}
				for (CollectionsDTO tmpCol : tmpCollections) {
					collections.remove(tmpCol);
				}
				createCollationsAndUpdateCollections(key, collections, tmpCollections);
				*/
			}
		
			exceptionFileWriter.flush();
			exceptionFileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void contains_(CollectionsDTO obj,List<CollectionsDTO> self, Set<CollectionsDTO> tmpCollections ) {
		for (CollectionsDTO entry : self) {
			 if(entry.getMember_number().longValue() == obj.getMember_number().longValue()
				&& entry.getBank().equalsIgnoreCase(obj.getBank())
				&& entry.getAccount_number().equalsIgnoreCase(obj.getAccount_number())
				&& entry.getCollection_date().equalsIgnoreCase(obj.getCollection_date())
				&& entry.getPayment_method().equalsIgnoreCase(obj.getPayment_method())
				){ 
				 tmpCollections.add(obj);
			 }
        }
    }
	
	
	public Map<String,List<CollectionsDTO>> groupCollectionByAccountNumber(List<CollectionsDTO> collections){
		return collections.stream().collect(Collectors.groupingBy(CollectionsDTO::getAccount_number));
	}
	
	public CollationDTO createCollationData(Long key, List<CollectionsDTO> tmpCollections, FileWriter exceptionFileWriter ) {
	            
		 CollationDTO collationData = new CollationDTO();
		 BigDecimal totalMonthlyPremium = BigDecimal.ZERO;
		 BigDecimal totalBillRaised = BigDecimal.ZERO;
		 BigDecimal closingBalance = BigDecimal.ZERO;
		 
		 Long member_number=0L;
		
		 Date anniversary_date=null;
		 String frequency="";
		 String paymentMethod="";
		 String account_holder_name="";
		 String bank="";
		 String account_number="";
		 String collection_date="";
		 String credit_card_expiry_date="";
		
		 
		 String record_type="";
		 String bsb_number="";
		 String indicator=" ";
		 String transaction_code="";
		 String title_of_account="";
		 String lodgement_reference="";
		 String bsb_number_in_format="";
		 String account_number_trace="";
		 String name_of_remitter="";
		 String amount_of_withholding_tax="";
			
		
		for (CollectionsDTO tmpCol : tmpCollections) {
			 totalBillRaised = totalBillRaised.add(tmpCol.getBill_raised());
			 totalMonthlyPremium = totalMonthlyPremium.add(tmpCol.getMonthly_premium());
			 closingBalance = closingBalance.add(tmpCol.getClosing_balace());
			 
			 if(tmpCol.getAnniversary_date() != null){
				 anniversary_date = tmpCol.getAnniversary_date();
			 }
			 if(tmpCol.getFrequency() != null){
				 frequency = tmpCol.getFrequency();
			 }
			 if(tmpCol.getPayment_method() != null){
				 paymentMethod = tmpCol.getPayment_method();
			 }
			 if(tmpCol.getAccount_holder_name() != null){
				 account_holder_name = tmpCol.getAccount_holder_name();
			 }
			 if(tmpCol.getBank() != null){
				 bank = tmpCol.getBank();
			 }
			 if(tmpCol.getAccount_number() != null){
				 account_number = tmpCol.getAccount_number(); 
			 }
			 if(tmpCol.getMember_number() != null){
				 member_number = tmpCol.getMember_number(); 
			 }
			 
			 if(tmpCol.getCollection_date() != null){
				 collection_date = tmpCol.getCollection_date(); 
			 }
			 if(tmpCol.getCredit_card_expiry_date() != null){
				 credit_card_expiry_date = tmpCol.getCredit_card_expiry_date();
			 }
			 ///////////////////// New entries for the file
			 if(tmpCol.getRecord_type() != null){
				 record_type = tmpCol.getRecord_type();
			 }
			 
			 if(tmpCol.getBsb_number() != null){
				 bsb_number = tmpCol.getBsb_number(); 
			 }
			 
			 if(tmpCol.getBsb_number_in_format() != null){
				 bsb_number_in_format = tmpCol.getBsb_number_in_format(); 
			 }
			 
			 if(tmpCol.getIndicator() != null){
				 indicator = tmpCol.getIndicator(); 
			 }
			 
			 if(tmpCol.getTransaction_code() != null){
				 transaction_code = tmpCol.getTransaction_code(); 
			 }
			 
			 if(tmpCol.getTitle_of_account() != null){
				 title_of_account = tmpCol.getTitle_of_account(); 
			 }
			 
			 if(tmpCol.getLodgement_reference() != null){
				 lodgement_reference = tmpCol.getLodgement_reference(); 
			 }
			 
			 if(tmpCol.getAccount_number_trace() != null){
				 account_number_trace  = tmpCol.getAccount_number_trace(); 
			 }
			 
			 if(tmpCol.getName_of_remitter() != null){
				 name_of_remitter = tmpCol.getName_of_remitter(); 
			 }
			 
			 if(tmpCol.getAmount_of_withholding_tax() != null){
				 amount_of_withholding_tax = tmpCol.getAmount_of_withholding_tax(); 
			 }
		}
		
		collationData.setAccount_holder_name(account_holder_name);
		collationData.setAccount_number(account_number);
		collationData.setClosing_balance(closingBalance);
		collationData.setTotal_bill_raised(totalBillRaised);
		collationData.setTotal_monthly_premium(closingBalance);//This should get the value from the closing balance and not total monthly
		collationData.setFrequency(frequency);
		collationData.setPayment_method(paymentMethod);
		collationData.setAnniversary_date(anniversary_date);
		collationData.setBank(bank);
		collationData.setMember_number(member_number);
		collationData.setCreation_date( new Date());
		collationData.setCollection_date(collection_date);
		collationData.setCredit_card_expiry_date(credit_card_expiry_date);
		
		/////////////// New fields
		collationData.setRecord_type(record_type);
		collationData.setBsb_number(bsb_number);
		collationData.setBsb_number_in_format(bsb_number_in_format);
		collationData.setIndicator(indicator);
		collationData.setTransaction_code(transaction_code);
		collationData.setTitle_of_account(title_of_account);
		collationData.setLodgement_reference(lodgement_reference);
		collationData.setAccount_number_trace(account_number_trace);
		collationData.setName_of_remitter(name_of_remitter);
		collationData.setAmount_of_withholding_tax(amount_of_withholding_tax);
		
		collationData.setCreation_status(CollationCreationStatusEnum.SYSTEM_CREATED.getValue());
		
		if(CreditCardTypes.isSelectedTypeCreditCard(bank)){
			if(!"valid".equalsIgnoreCase(validator.getCreditCardValidation().validateCreditCardNumber(bank, account_number))){
			    csvWriter.writeToValidationExceptionLog(exceptionFileWriter, bank,account_number); 
			    return null;
			}
		}
		return  dataService.getCollationDAO().save(collationData);
		
	}

	public void updateCollections( CollationDTO collationData, List<CollectionsDTO> tmpCollections ){
	
		if(collationData == null){
			return;
		}
		
		for (CollectionsDTO collectionsDTO : tmpCollections) {
			collectionsDTO.setCollation_id(collationData.getId());
			collectionsDTO.setIs_collated(Boolean.TRUE.toString().toUpperCase());
		}
		dataService.getCollectionDAO().save(tmpCollections);
	}
	
	private void createCollationsAndUpdateCollections(Long key, List<CollectionsDTO> collections,
			Set<CollectionsDTO> tmpCollections, FileWriter exceptionFileWriter) {
		if(!collections.isEmpty()){
			updateCollections(createCollationData(key, collections,exceptionFileWriter), collections);  
		}
		if(!tmpCollections.isEmpty()){
			List<CollectionsDTO> collectionList = new ArrayList<CollectionsDTO>(tmpCollections);
			updateCollections(createCollationData(key,collectionList,exceptionFileWriter ),collectionList);
		}
	}
	
	
	public String createHeaderMessage(Date collectionDate,String...args){


		NABMessageCreater.NABHeaderMessageDD header = new NABMessageCreater().new NABHeaderMessageDD();
		header.setValues( 
				NABMessageCreater.RecordType.HEADER.getType() ,
				headerDTO.getReel_sequence_number().toString(),
				headerDTO.getFinancial_institution(),
				headerDTO.getName_of_user_supplying_file(),
				"488320", //Need to put the value in the database 
				"COLLECTIONS",
				getProcessedDate(collectionDate));
	    String headerString = header.createNABHeaderMessage();
	    String tmpHeaderStr = headerString.replaceAll("@", " ");
	    
		return tmpHeaderStr;
	} 
	
	
	private String getProcessedDate(Date date ){
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//cal.add(Calendar.DATE, 1);// add one day to the current date 
		return format.format(cal.getTime());
	}
	
	public String createDetailsMessage(String...args){
		
		NABMessageCreater.NABDirectEntryMessageDD entry = new NABMessageCreater().new NABDirectEntryMessageDD();
		entry.setValues(NABMessageCreater.RecordType.DETAILS.getType(),
				args[0],
				args[1],
				args[2], 
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8],
				args[9],
				args[10]);
		String entryStr = entry.createDiretEntryMessage();
		
		return entryStr;
	}
	
	
	public String createTotalsMessage(String...args){
		
		NABMessageCreater.NABTransactionTotalsDD totals = new NABMessageCreater().new NABTransactionTotalsDD();
		totals.setValues(NABMessageCreater.RecordType.DD_TOTALS.getType(),
				args[0],
				args[1],
				args[2],
				args[3],
				args[4]);
		String totalsStr = totals.createNABTotalsMessage();

		return totalsStr;
	}
	
	
	
	public List<String> createCreditCardHeader(String recordCount, Date processingDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tmpFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		
		NABMessageCreater.NABHeaderMessageCC header = new NABMessageCreater().new NABHeaderMessageCC();
	   /*
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		//cal.add(Calendar.HOUR_OF_DAY,9);
		
		String tmpDate = tmpFormat.format(cal.getTime());
		String strAUSTime = tmpDate.substring(11, tmpDate.length());
	
	   // String strDate = dateFormat.format(processingDate).concat(" ").concat(strAUSTime);//Im doing this hack because,
	    																	   //when you format the date using the simpleDateFormat,
	    																	   //it adds the hours as 12, even if you set it your preferred value.
		*/
		
		String strDate = dateFormat.format(processingDate).concat(" 00:00:01");//Im doing this hack because,
		header.setValues( NABMessageCreater.RecordType.HEADER.getType(), 
				      new Integer(3).toString(),//Fixed  Value
				      headerDTO.getClient_id(),
				      strDate,
				      recordCount
				       );
		
		return header.createEntries();
	}
	
	public List<String> createCreditCardDetails(String...values){
		
		NABMessageCreater.NABDirectEntryMessageCC details = new NABMessageCreater().new NABDirectEntryMessageCC();
		details.setValues(NABMessageCreater.RecordType.DETAILS.getType()
				,headerDTO.getClient_id().concat("00")
				,NABMessageCreater.TransactionType.PAY.name()
				,"E"
				,values[0]//MemberNumber
				,values[1]//creditcardNumber/AccNumber
				,values[2]//Expirydate
				,NABMessageCreater.CurrencyEnum.AUD.getCurrency()//Currency
				,values[3]//Transaction Amount
				,""//Blank 
				,values[4] //Account HolderName
				);
		
		return details.createEntries();
	}
	
	
	public List<String> createCreditCardTrailer(String...values){
		
		NABMessageCreater.NABTransactionTotalsCC trailer = new NABMessageCreater().new NABTransactionTotalsCC();
		trailer.setValues(NABMessageCreater.RecordType.CC_TOTALS.getType()
				,NABMessageCreater.CurrencyEnum.AUD.getCurrency()
				,values[0]//record count
				,values[1]
				,values[2]
				,values[3]
			//	,values[4]
			//	,values[5]
			//	,values[6]
				);
		
		return trailer.createEntries();
	}
	
}
