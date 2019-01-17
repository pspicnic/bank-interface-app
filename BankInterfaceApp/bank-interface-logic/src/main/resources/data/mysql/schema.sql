drop table if exists bank_collections;

--Schema Name
-- BANK-INTERFACE-APP

create table bank_collections( 

	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	member_number numeric , 
	benefit_number numeric,  
	anniversary_date timestamp, 
	closing_balance decimal not null,
	monthly_premium decimal not null,
	bill_raised decimal not null,
	frequency varchar(50) not null,
	payment_method varchar(50) not null,
	collection_date varchar(100),
	account_holder_name varchar(100) not null,
	bank varchar(100), 
	account_number varchar(100) not null,
	collation_id numeric,
	status varchar(100),
	creation_date timestamp,
	status_modified_date timestamp,
	credit_card_expiry_date varchar(100),
	
	record_type varchar(100),
	bsb_number varchar(100),
	indicator varchar(100),
	transaction_code varchar(100),
	title_of_account varchar(100),
	lodgement_reference varchar(100),
	bsb_number_in_format varchar(100),
	account_number_trace varchar(100),
	name_of_remitter varchar(100),
	amount_of_withholding_tax varchar(100),
	is_collated varchar(10)
	
);


drop table if exists bank_collation; 

create table bank_collation(

	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	member_number numeric,
	anniversary_date timestamp, 
	total_monthly_premium decimal not null,
	total_bill_raised decimal not null,
	closing_balance  decimal not null,
	frequency varchar(50) not null,
	payment_method varchar(50) not null,
	collection_date varchar(10),
	collection_date_date timestamp,
	account_holder_name varchar(100) not null,
	bank varchar(100), 
	account_number varchar(100) not null,
	
	record_type varchar(100),
	credit_card_expiry_date varchar(100),
	bsb_number varchar(100),
	indicator varchar(100),
	transaction_code varchar(100),
	title_of_account varchar(100),
	lodgement_reference varchar(100),
	bsb_number_in_format varchar(100),
	account_number_trace varchar(100),
	name_of_remitter varchar(100),
	amount_of_withholding_tax varchar(100),
	
	transaction_status varchar(100),
	creation_status varchar(100),
	creation_date timestamp,
	processed_date timestamp,
	uuid varchar(1000)

);

drop table if exists bank_collation_header; 

create table bank_collation_header(

	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	reel_sequence_number numeric,
	financnial_institution varchar(100), 
	name_of_user_supplying_file varchar(1000),
	number_of_user_supplying_file numeric,
	description_of_entries_on_file varchar(100),
	pps_remitter_name varchar(100), 
	pps_bsb_number varchar(100),
	pps_account_number_trace varchar(100),
	date_of_processing varchar(100),
	client_id varchar(100) 

);
	






