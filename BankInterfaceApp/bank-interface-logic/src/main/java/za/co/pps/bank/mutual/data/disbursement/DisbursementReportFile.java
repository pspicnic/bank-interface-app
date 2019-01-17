/**
 * 
 */
package za.co.pps.bank.mutual.data.disbursement;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 07 Sep 2017
 * @version 1.0
 */
public class DisbursementReportFile {

	
	public class Header{
		List<String> entries = new ArrayList<String>();

		private String record = "00";
		private String bankName="";
		private String productName="";
		private String reportName="";
		private String runDate="";
		private String runTime="";
		private String fundId="";
		private String customerName="";
		private String importFileName="";
		private String paymentDate="";
		private String batchNoLinks="";
		private String exportFileName="";
		private String deUserId="";
		private String meId="";
		private String fileNameOfReport="";
		

		public void init(){
			entries.add(0, record);
			entries.add(1, bankName);
			entries.add(2, productName);
			entries.add(3, reportName);
			entries.add(4, runDate);
			entries.add(5, runTime);
			entries.add(6, fundId);
			entries.add(7, customerName);
			entries.add(8, importFileName);
			entries.add(9, paymentDate);
			entries.add(10, batchNoLinks);
			entries.add(11, exportFileName);
			entries.add(12, deUserId);
			entries.add(13, meId);
			entries.add(14, fileNameOfReport);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public void setReportName(String reportName) {
			this.reportName = reportName;
		}

		public void setRunDate(String runDate) {
			this.runDate = runDate;
		}
		public void setRunTime(String runTime) {
			this.runTime = runTime;
		}
		public void setFundId(String fundId) {
			this.fundId = fundId;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public void setImportFileName(String importFileName) {
			this.importFileName = importFileName;
		}

		public void setPaymentDate(String paymentDate) {
			this.paymentDate = paymentDate;
		}

		public void setBatchNoLinks(String batchNoLinks) {
			this.batchNoLinks = batchNoLinks;
		}

		public void setExportFileName(String exportFileName) {
			this.exportFileName = exportFileName;
		}

		public void setDeUserId(String deUserId) {
			this.deUserId = deUserId;
		}

		public void setMeId(String meId) {
			this.meId = meId;
		}

		public void setFileNameOfReport(String fileNameOfReport) {
			this.fileNameOfReport = fileNameOfReport;
		}
		
	}
	
	public class CreditPayment{ ////////////////////// Credit Payment
		private String record = "53";
		private String paymentTypeDnn="";
		private String lodgementReference="";
		private String amount="";
		private String currency="";
		private String credit_debit="";
		private String titleOfAccount="";
		private String bsbNumber="";
		private String accountNo="";
		
		List<String> entries = new ArrayList<String>();

		public void init(){
			entries.add(0, record);
			entries.add(1, paymentTypeDnn);
			entries.add(2, lodgementReference);
			entries.add(3, amount);
			entries.add(4, currency);
			entries.add(5, credit_debit);;
			entries.add(6, titleOfAccount);
			entries.add(7, bsbNumber);
			entries.add(8, accountNo);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}

		public void setPaymentTypeDnn(String paymentTypeDnn) {
			this.paymentTypeDnn = paymentTypeDnn;
		}

		public void setLodgementReference(String lodgementReference) {
			this.lodgementReference = lodgementReference;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public void setCredit_debit(String credit_debit) {
			this.credit_debit = credit_debit;
		}

		public void setTitleOfAccount(String titleOfAccount) {
			this.titleOfAccount = titleOfAccount;
		}

		public void setBsbNumber(String bsbNumber) {
			this.bsbNumber = bsbNumber;
		}

		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}

	}
	
	public class DebitPayment{ //////////////////// Debit Payment
		List<String> entries = new ArrayList<String>();
		private String record = "57";
		private String paymentTypeDnn="";
		private String lodgementReference="";
		private String amount="";
		private String currency="";
		private String credit_debit="";
		private String titleOfAccount="";
		private String bsbNumber="";
		private String accountNo="";
		

		public void init(){
			entries.add(0, record);
			entries.add(1, paymentTypeDnn);
			entries.add(2, lodgementReference);
			entries.add(3, amount);
			entries.add(4, currency);
			entries.add(5, credit_debit);
			entries.add(6, titleOfAccount);
			entries.add(7, bsbNumber);
			entries.add(8, accountNo);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}

		public void setPaymentTypeDnn(String paymentTypeDnn) {
			this.paymentTypeDnn = paymentTypeDnn;
		}

		public void setLodgementReference(String lodgementReference) {
			this.lodgementReference = lodgementReference;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public void setCredit_debit(String credit_debit) {
			this.credit_debit = credit_debit;
		}

		public void setTitleOfAccount(String titleOfAccount) {
			this.titleOfAccount = titleOfAccount;
		}

		public void setBsbNumber(String bsbNumber) {
			this.bsbNumber = bsbNumber;
		}

		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
	}
	
	public class ValueSummary1{ //////////////// Value Summary 1
		List<String> entries = new ArrayList<String>();

		private String record = "54";
		private String subTrancodeUV="";
		private String numberOfItemsDEC="";
		private String totalOfItemsDEC="";
		

		public void init(){
			entries.add(0, record);
			entries.add(1, subTrancodeUV);
			entries.add(2, numberOfItemsDEC);
			entries.add(3, totalOfItemsDEC);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}
		public void setSubTrancodeUV(String subTrancodeUV) {
			this.subTrancodeUV = subTrancodeUV;
		}

		public void setNumberOfItemsDEC(String numberOfItemsDEC) {
			this.numberOfItemsDEC = numberOfItemsDEC;
		}

		public void setTotalOfItemsDEC(String totalOfItemsDEC) {
			this.totalOfItemsDEC = totalOfItemsDEC;
		}

	}
	public class ValueSummary2{ //////////////// Value Summary 2
		private String record = "58";
		private String subTrancodeUVE="";
		private String numberOfItemsDED="";
		private String totalOfItemsDED="";
		
		List<String> entries = new ArrayList<String>();

		public void init(){
			entries.add(0, record);
			entries.add(1, subTrancodeUVE);
			entries.add(2, numberOfItemsDED);
			entries.add(3, totalOfItemsDED);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}

		public void setSubTrancodeUVE(String subTrancodeUVE) {
			this.subTrancodeUVE = subTrancodeUVE;
		}

		public void setNumberOfItemsDED(String numberOfItemsDED) {
			this.numberOfItemsDED = numberOfItemsDED;
		}

		public void setTotalOfItemsDED(String totalOfItemsDED) {
			this.totalOfItemsDED = totalOfItemsDED;
		}

	}
	
	public class FailedItems{ ////////////////////// Failed Items 
		private String record = "61";
		private String subTrancodeUXD="";
		private String paymentType="";
		private String lodgementReference="";
		private String amount="";
		private String currency="";
		private String credit_debit="";
		private String titleOfAccount="";
		private String bsbNumber="";
		private String accountNo="";
		private String failedReasonnCode="";
		private String reasonForRejection="";
		
		List<String> entries = new ArrayList<String>();

		public void init(){
			entries.add(0, record);
			entries.add(1, subTrancodeUXD);
			entries.add(2, paymentType);
			entries.add(3, lodgementReference);
			entries.add(4, amount);
			entries.add(5, currency);
			entries.add(6, credit_debit);
			entries.add(7, titleOfAccount);
			entries.add(8, bsbNumber);
			entries.add(9, accountNo);
			entries.add(10, failedReasonnCode);
			entries.add(11, reasonForRejection);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}

		public void setSubTrancodeUXD(String subTrancodeUXD) {
			this.subTrancodeUXD = subTrancodeUXD;
		}

		public void setPaymentType(String paymentType) {
			this.paymentType = paymentType;
		}

		public void setLodgementReference(String lodgementReference) {
			this.lodgementReference = lodgementReference;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public void setCredit_debit(String credit_debit) {
			this.credit_debit = credit_debit;
		}

		public void setTitleOfAccount(String titleOfAccount) {
			this.titleOfAccount = titleOfAccount;
		}

		public void setBsbNumber(String bsbNumber) {
			this.bsbNumber = bsbNumber;
		}

		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}

		public void setFailedReasonnCode(String failedReasonnCode) {
			this.failedReasonnCode = failedReasonnCode;
		}

		public void setReasonForRejection(String reasonForRejection) {
			this.reasonForRejection = reasonForRejection;
		}
	}
	
	
	public class FailedSummary{ ////////////////// Failed Summmary 
		List<String> entries = new ArrayList<String>();
		private String record = "62";
		private String subTrancodeUXS="";
		private String numberOfFailedItems="";
		private String totalOfFailedItems="";
		private String failedItemTreatmentOption="";
		private String thisAmountWas="";
		

		public void init(){
			entries.add(0, record);
			entries.add(1, subTrancodeUXS);
			entries.add(2, numberOfFailedItems);
			entries.add(3, totalOfFailedItems);
			entries.add(4, failedItemTreatmentOption);
			entries.add(5, thisAmountWas);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}

		public void setSubTrancodeUXS(String subTrancodeUXS) {
			this.subTrancodeUXS = subTrancodeUXS;
		}

		public void setNumberOfFailedItems(String numberOfFailedItems) {
			this.numberOfFailedItems = numberOfFailedItems;
		}

		public void setTotalOfFailedItems(String totalOfFailedItems) {
			this.totalOfFailedItems = totalOfFailedItems;
		}

		public void setFailedItemTreatmentOption(String failedItemTreatmentOption) {
			this.failedItemTreatmentOption = failedItemTreatmentOption;
		}

		public void setThisAmountWas(String thisAmountWas) {
			this.thisAmountWas = thisAmountWas;
		}
		
	}
	
	public class Trailer{//////////////// Trailer
		
		List<String> entries = new ArrayList<String>();
		
		private String record = "99";
		private String netFileTotal="";
		private String creditFileTotal="";
		private String debitFileTotal="";
		private String totalNumberOfRecordsInFile="";
	
		public void init(){
			entries.add(0, record);
			entries.add(1, netFileTotal);
			entries.add(2, creditFileTotal);
			entries.add(3, debitFileTotal);
			entries.add(4, totalNumberOfRecordsInFile);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}
		public void setRecord(String record) {
			this.record = record;
		}
		public void setNetFileTotal(String netFileTotal) {
			this.netFileTotal = netFileTotal;
		}
		public void setCreditFileTotal(String creditFileTotal) {
			this.creditFileTotal = creditFileTotal;
		}
		public void setDebitFileTotal(String debitFileTotal) {
			this.debitFileTotal = debitFileTotal;
		}
		public void setTotalNumberOfRecordsInFile(String totalNumberOfRecordsInFile) {
			this.totalNumberOfRecordsInFile = totalNumberOfRecordsInFile;
		}

	}
	
	
	public class Disclaimer{ ///////////////////// Disclaimer
		List<String> entries = new ArrayList<String>();

		private String record = "100";
		private String disclaimerText="";
		
		public void init(){
			entries.add(0, record);
			entries.add(1, disclaimerText);
		}
		
		public List<String> getEntries() {
			init();
			return this.entries;
		}
		
		public void setDisclaimerText(String disclaimerText){
			this.disclaimerText = disclaimerText;
		}
	}
	
}
