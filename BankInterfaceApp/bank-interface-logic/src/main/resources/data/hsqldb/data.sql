--use bank_collation_header;
insert into bank_collation_header(reel_sequence_number,financial_institution,name_of_user_supplying_file,number_of_user_supplying_file,description_of_entries_on_file,pps_remitter_name,pps_bsb_number,pps_account_number_trace,date_of_processing,client_id)
--values(1,'NAB','PPS Mutual Benefit Fund',5,'COLLECTION','NobleOak Life Ltd','082-057','988812390',DATE_FORMAT(CURDATE() + 1, "%M %d %Y"),'XT8');
values(1,'NAB','PPS Mutual Benefit Fund',5,'COLLECTION','NobleOak Life Ltd','082-057','988812390','2017/10/24','XT8');


--CONVERT(VARCHAR(10), DATEADD(DAY, 1, GETDATE()), 101) AS [MM/DD/YYYY]


