/**
 * 
 */
package za.co.pps.bank.mutual.data.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */
@Entity
@Table(name="BANK_COLLATION")
public class CollationDTO {
	
	public CollationDTO() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;

	private Long member_number;
	private Date anniversary_date;
	private BigDecimal total_monthly_premium;
	private BigDecimal total_bill_raised;
	
	@Column(name="CLOSING_BALANCE")
	private BigDecimal closing_balance;
	
	private String frequency;
	private String payment_method;
	private String account_holder_name;
	private String bank;
	private String account_number;
	
	private String transaction_status;
	private String creation_status; 
	private Date creation_date;
	private Date processed_date;
	private String collection_date;
	private Date  collection_date_date;
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
	
	private String uuid;
	
	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid = uuid;
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
	public Date getAnniversary_date() {
		return anniversary_date;
	}
	public void setAnniversary_date(Date anniversary_date) {
		this.anniversary_date = anniversary_date;
	}
	public BigDecimal getTotal_monthly_premium() {
		return total_monthly_premium;
	}
	public void setTotal_monthly_premium(BigDecimal total_monthly_premium) {
		this.total_monthly_premium = total_monthly_premium;
	}
	public BigDecimal getTotal_bill_raised() {
		return total_bill_raised;
	}
	public void setTotal_bill_raised(BigDecimal total_bill_raised) {
		this.total_bill_raised = total_bill_raised;
	}
	public BigDecimal getClosing_balance() {
		return closing_balance;
	}
	public void setClosing_balance(BigDecimal closing_balance) {
		this.closing_balance = closing_balance;
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
	public void setPayment_method(String paymentMethod) {
		this.payment_method = paymentMethod;
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
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	public Date getProcessed_date() {
		return processed_date;
	}
	public void setProcessed_date(Date processed_date) {
		this.processed_date = processed_date;
	}
	public String getCollection_date() {
		return collection_date;
	}
	public void setCollection_date(String collection_date) {
		this.collection_date = collection_date;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			setCollection_date_date(format.parse(this.collection_date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the collection_date_date
	 */
	public Date getCollection_date_date() {
		return collection_date_date;
	}
	/**
	 * @param collection_date_date the collection_date_date to set
	 */
	private void setCollection_date_date(Date collection_date_date) {
		this.collection_date_date = collection_date_date;
	}
	public String getCredit_card_expiry_date() {
		return credit_card_expiry_date;
	}
	public void setCredit_card_expiry_date(String credit_card_expiry_date) {
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
	public String getTransaction_status() {
		return transaction_status;
	}
	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}
	public String getCreation_status() {
		return creation_status;
	}
	public void setCreation_status(String creation_status) {
		this.creation_status = creation_status;
	}
}
