/**
 * 
 */
package za.co.pps.bank.mutual.logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.Validate;

import za.co.pps.bank.mutual.data.DataServiceDAO;
import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollationHeaderDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;
import za.co.pps.bank.mutual.utils.BankAccountTypesEnum;
import za.co.pps.bank.mutual.utils.BankInterfaceContext;
import za.co.pps.bank.mutual.utils.CSVWriter;
import za.co.pps.bank.mutual.utils.CollationWrapper;
import za.co.pps.bank.mutual.utils.NABMessageCreater;
import za.co.pps.bank.mutual.utils.NABTransactionStatusEnum;
import za.co.pps.bank.mutual.validation.NABMessageValidator;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 10 Sep 2017
 * @version 1.0
 */
@Transactional(propagation=Propagation.MANDATORY)
@Service
public class CreateNABTransactionLayer {
	
	
	@Autowired
	CSVWriter csvWriter;
	
	@Autowired
	NABMessageValidator validator;
	
	@Autowired
	DataServiceDAO dataService;
	
	@Autowired
	CollationWrapper  wrapper;	
	
	private Map<String,String> fileName;
	
	
	public Map<String,String> getFileName(){
		return this.fileName;
	}
	
	public void createNABMessageByTypeOfAccount(BankInterfaceContext context, String outputFile, String accountType )throws Exception{
		Validate.notNull(outputFile, "Output file location is missing");
		Validate.notNull(accountType,"File Type to create is missing");
		
		Date collectionDate = dataService.getCollationDAO().getMaxCollectionDate();
		if(collectionDate != null){
			
			List<Date> datesList = new ArrayList<Date>();
			datesList.add(0,collectionDate);//Future or Current Date
			datesList.add(1,new DateTime(collectionDate).minus(1).toDate());
			
			boolean isBackDated = false;
			for (Date date : datesList) {
				if(datesList.get(1).compareTo(date) == 0){
					isBackDated = true;
				}
			    createNABMessageByAccountTypeHelper(context, outputFile, accountType, date, isBackDated);
			}
		}
	}

	/**
	 * @param outputFile
	 * @param accountType
	 * @param collectionDate
	 * @throws IOException
	 */
	private void createNABMessageByAccountTypeHelper(BankInterfaceContext context, String outputFile, String accountType, Date collectionDate, boolean isBackDated)
			throws IOException {
		
		List<CollationDTO> collationList = null;
		List<Date> datesList = new ArrayList<Date>();
		if(!isBackDated){
			 collationList = dataService.getCollationDAO().findUnProcessedCollationsByType(accountType,collectionDate,BigDecimal.ZERO);
		}else{
			 collationList = dataService.getCollationDAO().findBackDatedUnProcessedCollationsByType(accountType,collectionDate,BigDecimal.ZERO);
		}
		
	    FileWriter writer = null;
		String uniqueRef = UUID.randomUUID().toString();
		
	    if(collationList == null || collationList.isEmpty()){
	    	return;  // Report that, there's no data to create a file
	    }
	    
	    for (CollationDTO collation : collationList) {
			datesList.add( collation.getCollection_date_date() );
		}
		collectionDate = (Date)Collections.max(datesList);
	    
	    
	    if("Debit Order".equalsIgnoreCase(accountType)){
			List<String> csvFileDdata = new ArrayList<String>();
	    	writer = createDirectDebitFile(context,outputFile, accountType, uniqueRef, csvFileDdata, collationList, collectionDate);
		    csvWriter.writeLine(writer, csvFileDdata, '~');
    	}else{
    		List<List<String>> csvFileDdata = new ArrayList<List<String>>();
    		writer = createDirectCreditCardFile(context,outputFile, accountType, uniqueRef, csvFileDdata, collationList, collectionDate);
    	    csvWriter.writeRecord(writer, csvFileDdata);
    	}
	    
	    writer.flush();
		writer.close();
		
		updateTransactions(context,collationList,uniqueRef); //Update status to sent
	}

	private FileWriter createDirectDebitFile(BankInterfaceContext context, String outputFile, String accountType, String uniqueRef,
			List<String> csvFileDdata, List<CollationDTO> collationList, Date collectionDate) throws IOException {
		File nabfile = new File(outputFile);
		FileWriter writer = getNABEntryFile(context,outputFile, accountType, nabfile, uniqueRef);
		
	    String headerDetails = wrapper.createHeaderMessage(collectionDate);
	    validator.validateMessage(NABMessageCreater.RecordType.HEADER.getType(), headerDetails);
	    csvFileDdata.add(headerDetails);
	   
	    BigDecimal creditTotalAmount = addCollationEntries(context,csvFileDdata, collationList, accountType);
	    String totals = getTotals(context, creditTotalAmount,collationList, accountType);
	    validator.validateMessage(NABMessageCreater.RecordType.DD_TOTALS.getType(), totals);
	    
	    csvFileDdata.add(totals);
		return writer;
	}
	
	private FileWriter createDirectCreditCardFile(BankInterfaceContext context,String outputFile, String accountType, String uniqueRef,List<List<String>> csvFileDdata, List<CollationDTO> collationList, Date collectionDate) throws IOException {
		
		File nabfile = new File(outputFile);
		FileWriter writer = getNABEntryFile(context,outputFile, accountType, nabfile, uniqueRef);
	    
		List<String> header = wrapper.createCreditCardHeader(collationList.size()+"", collectionDate);

		csvFileDdata.add(header);
		
		addCreditCardEntries(context,csvFileDdata, collationList);
	
		addCreditCardTotals(context,csvFileDdata, collationList, accountType);
		
		return writer;
	}
	
	
	private BigDecimal addCollationEntries(BankInterfaceContext context,List<String> csvFileDdata, List<CollationDTO> collationList,String accountType) {
		for(CollationDTO data : collationList){
			 
	    	String details = wrapper.createDetailsMessage(
	    			data.getBsb_number(),
					data.getAccount_number(),
					data.getIndicator(),
					data.getTransaction_code(),
					data.getClosing_balance().toString(),
					data.getAccount_holder_name(),
					data.getLodgement_reference(),
					data.getBsb_number_in_format(),
					data.getAccount_number_trace(),
					data.getName_of_remitter(),
					data.getAmount_of_withholding_tax());
	    	validator.validateMessage(NABMessageCreater.RecordType.DETAILS.getType(), details);
	    	csvFileDdata.add(details);
	    }
		
		return createCreditEntryForNAD(context,collationList, csvFileDdata, accountType);
	}
	
	private BigDecimal createCreditEntryForNAD(BankInterfaceContext context,List<CollationDTO> collationList,List<String> csvFileDdata, String accountType ){
		
		CollationHeaderDTO header = dataService.getCollationHeaderDAO().findAll().iterator().next();

		BigDecimal netTotalAmount =BigDecimal.ZERO;
		BigDecimal debitTotalAmount =  BigDecimal.ZERO;
		
		for (CollationDTO collation: collationList) {
			netTotalAmount = netTotalAmount.add(collation.getClosing_balance());
			if(BankAccountTypesEnum.DEBIT.getAccountType().equalsIgnoreCase(accountType)){
				debitTotalAmount = debitTotalAmount.add(collation.getClosing_balance());
	    	}
		}
				
		String creditDetail = wrapper.createDetailsMessage(
    			header.getPps_bsb_number(),
    			header.getPps_account_number_trace(),
				" ",
				"50", //Transaction Type Credit
				debitTotalAmount.toString(),
				header.getName_of_user_supplying_file(),
				//"PPS MUTUAL BENEF", //Lodgement Reference
				header.getName_of_user_supplying_file().toUpperCase(),
    			header.getPps_bsb_number(),
    			header.getPps_account_number_trace(),
				header.getName_of_user_supplying_file(),
				"00");
		csvFileDdata.add(creditDetail);
		
		return debitTotalAmount;
	}
	
	private void addCreditCardEntries(BankInterfaceContext context,List<List<String>> csvFileDdata, List<CollationDTO> collationList){
		
		for(CollationDTO data : collationList){ 
			List<String> details = wrapper.createCreditCardDetails(
					 data.getMember_number().toString()
					,data.getAccount_number()
					,data.getCredit_card_expiry_date()
					,data.getClosing_balance().toString()  //Changed from total_monthly_premium  
					,data.getAccount_holder_name()
					);
			
			csvFileDdata.add(details);
		}
	}

	private FileWriter getNABEntryFile(BankInterfaceContext context, String outputFile, String accountType, File nabfile, String uniqueRef) throws IOException {
		FileWriter writer;
		this.fileName = new HashMap<String,String>();
		if(nabfile.exists()){ //If directory exists
			if(nabfile.isDirectory()){
				if(accountType.equalsIgnoreCase( BankAccountTypesEnum.DEBIT.getAccountType())){
					outputFile =outputFile.concat(uniqueRef).concat("_").concat("DirectEntry.csv");
					this.fileName.put(uniqueRef,outputFile); //This is used when we encrypting the files for NAB
				}else{
					outputFile = outputFile.concat(uniqueRef).concat("_").concat("CreditCard.csv");
					this.fileName.put(uniqueRef,outputFile); //This is used when we encrypting the files for NAB
				}
				nabfile = new File(outputFile);
				if(!nabfile.exists()){
					nabfile.createNewFile();
				}
			}
		}
		writer = new FileWriter(nabfile);
		return writer;
	}
	
	private String getTotals(BankInterfaceContext context,BigDecimal creditAmountTotal, List<CollationDTO> collationList, String accountType ){
		
		String bsbNumber = "999-999"; //Need to get it from Nab
		BigDecimal netTotalAmount =BigDecimal.ZERO;
		BigDecimal creditTotalAmount = BigDecimal.ZERO;
		BigDecimal debitTotalAmount =  BigDecimal.ZERO;
		Integer fileCountOfRecordType = collationList.size();
		
		for (CollationDTO collation: collationList) {
			netTotalAmount = netTotalAmount.add(collation.getClosing_balance());
			
			if(BankAccountTypesEnum.DEBIT.getAccountType().equalsIgnoreCase(accountType)){
				debitTotalAmount = debitTotalAmount.add(collation.getClosing_balance());
				fileCountOfRecordType = collationList.size() + 1;
	    	}
	    	if(BankAccountTypesEnum.CREDIT_CARD.getAccountType().equalsIgnoreCase(accountType)){
	    		creditTotalAmount = creditTotalAmount.add(collation.getClosing_balance());
	    	}
		}
		
		if(BankAccountTypesEnum.DEBIT.getAccountType().equalsIgnoreCase(accountType)){
			netTotalAmount  = netTotalAmount.subtract(creditAmountTotal);
			creditTotalAmount = creditAmountTotal;
		}
		
		return wrapper.createTotalsMessage( bsbNumber, 
				netTotalAmount.toString(),
				creditTotalAmount.toString(),
				debitTotalAmount.toString(),
				fileCountOfRecordType.toString());
	
	}
	
	
	private void addCreditCardTotals(BankInterfaceContext context,List<List<String>> csvFileDdata,List<CollationDTO> collationList, String accountType){
		
		BigDecimal preTotalAmount =BigDecimal.ZERO;
		BigDecimal creditTotalAmount = BigDecimal.ZERO;
		BigDecimal debitTotalAmount =  BigDecimal.ZERO;
		String fileCountOfRecordType = collationList.size()+"";
		
		for (CollationDTO collation: collationList) {
		
			if(BankAccountTypesEnum.DEBIT.getAccountType().equalsIgnoreCase(accountType)){
				debitTotalAmount = debitTotalAmount.add(collation.getClosing_balance());
	    	}
	    	if(BankAccountTypesEnum.CREDIT_CARD.getAccountType().equalsIgnoreCase(accountType)){
	    		creditTotalAmount = creditTotalAmount.add(collation.getClosing_balance());
	    	}
		}
		 
		List<String> trailer = wrapper.createCreditCardTrailer(
				 fileCountOfRecordType
				,creditTotalAmount.toString()
				,debitTotalAmount.toString()
				,preTotalAmount.toString()
				//,"0" // NAB hasn't told us what this values are for, and they are not in their ,multi merchant spec
				//,"0" // NAB hasn't told us what this values are for
				//,"0" // NAB hasn't told us what this values are for
				);
		csvFileDdata.add(trailer);
	}
	
	
	private void  updateTransactions(BankInterfaceContext context,List<CollationDTO> collationList , String uniqueRef){
		
		for (CollationDTO collation : collationList) {
			collation.setUuid(uniqueRef);
			collation.setTransaction_status( NABTransactionStatusEnum.SENT.getValue() );
			List<CollectionsDTO> collectionsList = dataService.getCollectionDAO().findCollectionsByCollationId(collation.getId());
			for (CollectionsDTO collection : collectionsList) {
				collection.setStatus( NABTransactionStatusEnum.SENT.getValue());
				collection.setStatus_modified_date(new java.util.Date());
			}
			dataService.getCollectionDAO().save(collectionsList);
		}
		dataService.getCollationDAO().save(collationList);
	}
}
