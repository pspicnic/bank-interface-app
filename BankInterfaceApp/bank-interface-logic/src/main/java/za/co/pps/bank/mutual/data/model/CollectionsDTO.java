/**
 * 
 */
package za.co.pps.bank.mutual.data.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import za.co.pps.bank.mutual.utils.BankAccountTypesEnum;
import za.co.pps.bank.mutual.utils.NABMessageCreater;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */
@Entity
@Table(name="BANK_COLLECTIONS")
public class CollectionsDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;
	private Long member_number;
	private Long benefit_number;
	private Date anniversary_date;
	private BigDecimal closing_balance;
	private BigDecimal monthly_premium;
	private BigDecimal bill_raised;
	private String frequency;
	private String payment_method;
	private String account_holder_name;
	private String bank;
	private String account_number;
	private Long collation_id;
	private String status;
	private Date creation_date;
	private Date status_modified_date;
	private String collection_date;

	private String credit_card_expiry_date;
	
	    private  String record_type;
		private  String bsb_number;
		private  String indicator;
		private  String transaction_code;
		private  String title_of_account;
		private  String lodgement_reference;
		private  String bsb_number_in_format;
		private  String account_number_trace;
		private  String name_of_remitter;
		private  String amount_of_withholding_tax;
		
		
		private String is_collated;

	
	
	@Transient
	private List<String> entry;
	
	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public Date getStatus_modified_date() {
		return status_modified_date;
	}

	public void setStatus_modified_date(Date status_modified_date) {
		this.status_modified_date = status_modified_date;
	}

	public CollectionsDTO(){}
	
	public CollectionsDTO(List<String> entry){
	   this.entry = entry;
	   this.setMember_number(Long.parseLong(entry.get(0)));
	   this.setBenefit_number(Long.parseLong(entry.get(2)));
	   this.setAnniversary_date(entry.get(13));
	   this.setClosing_balace(new BigDecimal(entry.get(12)));// We create the premium we need to collect from the closing Balance
	   String monthlyPremium = StringUtils.isEmpty(entry.get(14)) ? BigDecimal.ZERO.toString() :  entry.get(14);
	   this.setMonthly_premium(new BigDecimal(monthlyPremium));
	 //  System.out.println("============== Bill Raised ========");
	//   System.out.println( entry.get(15));
	   String billRaise = StringUtils.isEmpty(entry.get(15)) ? BigDecimal.ZERO.toString() :  entry.get(15);
	   this.setBill_raised(new BigDecimal(billRaise));
	   this.setFrequency(entry.get(18));
	   this.setPayment_method( entry.get(19));
	   this.setCollection_date(entry.get(22));
	   this.setAccount_holder_name( entry.get(30));
	   this.setBank(  entry.get(31));
	   this.setAccount_number(entry.get(33));
	   this.setCreation_date(new Date());
	   this.setCredit_card_expiry_date(entry.get(34));
	   
	   
	   this.setRecord_type( NABMessageCreater.RecordType.DETAILS.getType());
	   this.setBsb_number(entry.get(32));
	   this.setBsb_number_in_format(entry.get(32));
	   this.setTransaction_code( entry.get(19) );
	   this.setTitle_of_account(entry.get(1));
	   this.setLodgement_reference(entry.get(0));
	   this.setIndicator(" "); //This value we still need to ask the autralian guys what it means;
	   this.setAmount_of_withholding_tax("0000");
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMember_number() {
		return member_number;
	}
	public void setMember_number(Long member_number) {
		this.member_number = member_number;
	}
	public Long getBenefit_number() {
		return benefit_number;
	}
	public void setBenefit_number(Long benefit_number) {
		this.benefit_number = benefit_number;
	}
	public Date getAnniversary_date() {
		return anniversary_date;
	}
	public void setAnniversary_date(String anniversary_date) {
		String pattern = "yyyy/mm/dd";
		if(anniversary_date.contains("-")){
			pattern = "yyyy-mm-dd";
		}
	    DateFormat df = new SimpleDateFormat(pattern);
	    Date date;
		try {
			date = (Date) df.parse( anniversary_date );
			this.anniversary_date = date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAnniversary_date(Date anniversary_date) {
		this.anniversary_date = anniversary_date;
	}
	public void setClosing_balace(BigDecimal closing_balance){
		this.closing_balance = closing_balance;
	}
	public BigDecimal getClosing_balace(){
		return this.closing_balance;
	}
	   
	public BigDecimal getMonthly_premium() {
		return monthly_premium;
	}
	public void setMonthly_premium(BigDecimal monthly_premium) {
		this.monthly_premium = monthly_premium;
	}
	public BigDecimal getBill_raised() {
		return bill_raised;
	}
	public void setBill_raised(BigDecimal bill_raised) {
		this.bill_raised = bill_raised;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public Long getCollation_id() {
		return collation_id;
	}
	public void setCollation_id(Long collation_id) {
		this.collation_id = collation_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCollection_date() {
		return collection_date;
	}
	public void setCollection_date(String collection_date) {
		this.collection_date = collection_date;
	}

	public String getCredit_card_expiry_date() {
		return credit_card_expiry_date;
	}
	public void setCredit_card_expiry_date(String credit_card_expiry_date) {
		if(credit_card_expiry_date.contains("/")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMyy");
			try {
				Date date = format.parse(credit_card_expiry_date);
				credit_card_expiry_date = monthYearFormat.format(date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		this.credit_card_expiry_date = credit_card_expiry_date;
	}

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public String getBsb_number() {
		return bsb_number;
	}

	public void setBsb_number(String bsb_number) {
		this.bsb_number = bsb_number;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getTransaction_code() {
		return transaction_code;
	}

	public void setTransaction_code(String transaction_code) {
		 if(transaction_code.equalsIgnoreCase(BankAccountTypesEnum.CREDIT_CARD.getAccountType())){
			 transaction_code = "50";
		 }
		 if(transaction_code.equalsIgnoreCase(BankAccountTypesEnum.DEBIT.getAccountType())){
			 transaction_code = "13";
		 }
		 if(transaction_code.equalsIgnoreCase(BankAccountTypesEnum.DEBIT.getAccountType())){
			 transaction_code = "13";
		 }
		 if(transaction_code.equalsIgnoreCase(BankAccountTypesEnum.DEBIT.getAccountType())){
			 transaction_code = "13";
		 }
		 if("Invalid Payment Method".equalsIgnoreCase(transaction_code)){
			 transaction_code = "Invalid PM";
		 }
		this.transaction_code = transaction_code;
	}

	public String getTitle_of_account() {
		return title_of_account;
	}

	public void setTitle_of_account(String title_of_account) {
		this.title_of_account = title_of_account;
	}

	public String getLodgement_reference() {
		return lodgement_reference;
	}

	public void setLodgement_reference(String lodgement_reference) {
		this.lodgement_reference = lodgement_reference;
	}

	public String getBsb_number_in_format() {
		return bsb_number_in_format;
	}

	public void setBsb_number_in_format(String bsb_number_in_format) {
		this.bsb_number_in_format = bsb_number_in_format;
	}

	public String getAccount_number_trace() {
		return account_number_trace;
	}

	public void setAccount_number_trace(String account_number_trace) {
		this.account_number_trace = account_number_trace;
	}

	public String getName_of_remitter() {
		return name_of_remitter;
	}

	public void setName_of_remitter(String name_of_remitter) {
		this.name_of_remitter = name_of_remitter;
	}

	public String getAmount_of_withholding_tax() {
		return amount_of_withholding_tax;
	}

	public void setAmount_of_withholding_tax(String amount_of_withholding_tax) {
		this.amount_of_withholding_tax = amount_of_withholding_tax;
	}

	/**
	 * @return the isCollated
	 */
	public String getIs_collated() {
		return is_collated;
	}

	/**
	 * @param isCollated the isCollated to set
	 */
	public CollectionsDTO setIs_collated(String isCollated) {
		this.is_collated = isCollated;
		return this;
	}

	/**
	 * @return
	 */
	public List<String> toStringList() {
		
		List<String> list = new ArrayList<String>();
		
		list.add(this.member_number.toString());
		list.add(this.benefit_number.toString());
		list.add((this.anniversary_date!=null) ? this.anniversary_date.toString() : new Date().toString());
		list.add(this.closing_balance.toString());
		list.add(this.monthly_premium.toString());
		list.add(this.bill_raised.toString());
		list.add(this.frequency);
		list.add(this.payment_method);
		list.add(this.account_holder_name);
		list.add(this.bank);
		list.add(this.account_number);
		list.add(this.collection_date.toString());
		
		return list;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CollectionsDTO [id=" + id + ", member_number=" + member_number + ", benefit_number=" + benefit_number
				+ ", anniversary_date=" + anniversary_date + ", closing_balance=" + closing_balance
				+ ", monthly_premium=" + monthly_premium + ", bill_raised=" + bill_raised + ", frequency=" + frequency
				+ ", payment_method=" + payment_method + ", account_holder_name=" + account_holder_name + ", bank="
				+ bank + ", account_number=" + account_number + ", collation_id=" + collation_id + ", status=" + status
				+ ", creation_date=" + creation_date + ", status_modified_date=" + status_modified_date
				+ ", collection_date=" + collection_date + ", credit_card_expiry_date=" + credit_card_expiry_date
				+ ", record_type=" + record_type + ", bsb_number=" + bsb_number + ", indicator=" + indicator
				+ ", transaction_code=" + transaction_code + ", title_of_account=" + title_of_account
				+ ", lodgement_reference=" + lodgement_reference + ", bsb_number_in_format=" + bsb_number_in_format
				+ ", account_number_trace=" + account_number_trace + ", name_of_remitter=" + name_of_remitter
				+ ", amount_of_withholding_tax=" + amount_of_withholding_tax + ", is_collated=" + is_collated
				+ ", entry=" + entry + "]";
	}
	
}
