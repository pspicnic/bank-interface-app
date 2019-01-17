package za.co.pps.bank.mutual.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import za.co.pps.bank.mutual.data.model.CollationDAO;
import za.co.pps.bank.mutual.data.model.CollationHeaderDAO;
import za.co.pps.bank.mutual.data.model.CollectionsDAO;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */

@Repository
public class DataServiceDAO {

	@Autowired
	private CollationDAO collations;
	@Autowired 
	private CollectionsDAO collections;
	
	@Autowired
	private CollationHeaderDAO collationHeader;
	
	public CollationDAO getCollationDAO(){
		return collations;
	}
	public CollectionsDAO getCollectionDAO(){
		return collections;
	}
	public CollationHeaderDAO getCollationHeaderDAO(){
		return collationHeader;
	}
}
