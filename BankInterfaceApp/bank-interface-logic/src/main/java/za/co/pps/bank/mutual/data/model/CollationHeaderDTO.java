/**
 * 
 */
package za.co.pps.bank.mutual.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 11 Sep 2017
 * @version 1.0
 */
@Entity
@Table(name="bank_collation_header")
public class CollationHeaderDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long reel_sequence_number;
	private String financial_institution;
	private String name_of_user_supplying_file;
	private Long   number_of_user_supplying_file;
	private String description_of_entries_on_file;
	private String date_of_processing;
	
	///========New fields
	private String pps_remitter_name;
	private String pps_bsb_number;
	private String pps_account_number_trace;
	
	private String client_id;
	
	
	public Long getId() {
		return id;
	}
	
	public Long getReel_sequence_number() {
		return reel_sequence_number;
	}
	public void setReel_sequence_number(Long reel_sequence_number) {
		this.reel_sequence_number = reel_sequence_number;
	}
	public String getFinancial_institution() {
		return financial_institution;
	}
	public void setFinancial_institution(String financial_institution) {
		this.financial_institution = financial_institution;
	}
	public String getName_of_user_supplying_file() {
		return name_of_user_supplying_file;
	}
	public void setName_of_user_supplying_file(String name_of_user_supplying_file) {
		this.name_of_user_supplying_file = name_of_user_supplying_file;
	}
	public Long getNumber_of_user_supplying_file() {
		return number_of_user_supplying_file;
	}
	public void setNumber_of_user_supplying_file(Long number_of_user_supplying_file) {
		this.number_of_user_supplying_file = number_of_user_supplying_file;
	}
	public String getDescription_of_entries_on_file() {
		return description_of_entries_on_file;
	}
	public void setDecription_of_entries_on_file(String decription_of_entries_on_file) {
		this.description_of_entries_on_file = decription_of_entries_on_file;
	}
	public String getDate_of_processing() {
		return date_of_processing;
	}
	public void setDate_of_processing(String date_of_processing) {
		this.date_of_processing = date_of_processing;
	}
	public String getPps_remitter_name() {
		return pps_remitter_name;
	}

	public void setPps_remitter_name(String pps_remitter_name) {
		this.pps_remitter_name = pps_remitter_name;
	}

	public String getPps_bsb_number() {
		return pps_bsb_number;
	}

	public void setPps_bsb_number(String pps_bsb_number) {
		this.pps_bsb_number = pps_bsb_number;
	}

	public String getPps_account_number_trace() {
		return pps_account_number_trace;
	}

	public void setPps_account_number_trace(String pps_account_number_trace) {
		this.pps_account_number_trace = pps_account_number_trace;
	}

	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}
	/**
	 * @param client_id the client_id to set
	 */
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CollationHeaderDTO [id=" + id + ", reel_sequence_number=" + reel_sequence_number
				+ ", financial_institution=" + financial_institution + ", name_of_user_supplying_file="
				+ name_of_user_supplying_file + ", number_of_user_supplying_file=" + number_of_user_supplying_file
				+ ", description_of_entries_on_file=" + description_of_entries_on_file + ", date_of_processing="
				+ date_of_processing + ", pps_remitter_name=" + pps_remitter_name + ", pps_bsb_number=" + pps_bsb_number
				+ ", pps_account_number_trace=" + pps_account_number_trace + ", client_id=" + client_id + "]";
	}

}