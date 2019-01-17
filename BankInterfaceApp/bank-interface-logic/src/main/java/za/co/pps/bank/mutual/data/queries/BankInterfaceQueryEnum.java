/**
 * 
 */
package za.co.pps.bank.mutual.data.queries;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 23 Aug 2017
 * @version 1.0
 */
public enum BankInterfaceQueryEnum {
	QUERY("");
	
	BankInterfaceQueryEnum(String query){
		this.query = query; 
	}
	
    private String query;

	public String getValue(){
		return query;
	}
	
	public static class Constants{
		
		public final static String FIND_COLLECTION_BY_ACC_NUMBER = 
				    "select c from CollectionsDTO c"
				  + " where c.account_number =:acc_num"; 
		
		public final static String FIND_COLLECTION_BY_MEMBER_NUMBER = 
			    "select c from CollectionsDTO c"
			  + " where c.member_number =:member_num"
			  + " and c.collation_id is null"  		
			  + " and c.status is null"; 
		
	    public final static String FIND_COLLECTIONS_WITH_SAME_ACC_NUMBER_AND_ACC_TYPE = 
	    		"select c from CollectionsDTO c "
	    		+ "where "
	    		+ "c.member_number =:member_num "		
	    		+ "and c.collection_date =:col_day "
	    		+ "and c.bank =:bank "
	    		+ "and c.account_number =:acc_num";
	    
	    public final static String GET_DISTINCT_MEMBER_NUMBERS = 
	    		"select distinct c.member_number from CollectionsDTO c"
	    		+ " where c.status is null "
	    		+ " and c.collation_id is null ";
	    
	    
	    public final static String FIND_COLLATION_BY_BANK_ACCOUNT_TYPE =
	    		"select c from CollationDTO c " 
	    	   +" where c.payment_method = :accountType";
	    
	    public final static String FIND_COLLATION_BY_BANK_ACCOUNT_TYPE_AND_DATE  =
	    		"select c from CollationDTO c " 
	    	   + " where c.payment_method = :accountType"
	    	   + " and  c.collection_date_date:=collecton_date"
	    	   + " and  c.uuid is null";
	    
	    public final static String FIND_BACK_DATED_COLLATIONS_BY_BANK_ACCOUNT_TYPE_AND_DATE = 
	    		"select c from CollationDTO c " 
	    		+ " where c.payment_method = :account_type"
	    		+ " and  c.collection_date_date <= :collection_date"
	    		+ " and  c.uuid is null"
	    		+ " and  c.processed_date is null"
	    		+" and c.closing_balance > :zero_closing_balance";

	    
	    
	    public final static String FIND_UNPROCESSED_COLLATIONS = 
	    		"select c from CollationDTO c "
	    	   +" where c.transaction_status is null and c.processed_date is null";
	    
	    public final static String FIND_UNPROCESSED_COLLATIONS_BY_TYPE = 
	    		"select c from CollationDTO c "
	    	   +" where c.transaction_status is null"
	    	   +" and c.uuid is null"		
	    	   +" and c.processed_date is null"
	    	   +" and c.payment_method =:account_type"
	    	   +" and c.collection_date_date =:collection_date"
	    	   +" and c.closing_balance > :zero_closing_balance"
	    	   ;
	   
	    
	    public final static String FIND_COLLECTION_BY_COLLATION_ID =
	    		"select c from CollectionsDTO c"
	    	   +" where c.collation_id =:collation_id";

		public static final String FIND_COLLATIONS_BY_STATUS = 
		 		"select c from CollationDTO c"
		 	   +" where c.transaction_status =:status";
	    
		public static final String FIND_COLLATIONS_BY_UUID =
				"select c from CollationDTO c "
			   +" where c.uuid =:uuid";
		
		public static final String GET_MAX_COLLECTION_DATE =
				"select distinct MAX(c.collection_date_date) from CollationDTO c ";
				
	    
	}
	
	

}
