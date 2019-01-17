/**
 * 
 */
package za.co.pps.bank.mutual.data;



import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollationHeaderDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;
import za.co.pps.bank.mutual.utils.CollationWrapper;
import za.co.pps.bank.mutual.validation.BankInterfaceValidator;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class DataServiceLayerImpl implements DataServiceLayer{

	private static final Logger LOG = LoggerFactory.getLogger(DataServiceLayerImpl.class); 
	
	@Autowired
	private DataServiceDAO dataService;
	
	@Autowired
	private BankInterfaceValidator validator;
	
	@Autowired
	CollationWrapper wrapper;
	
	@Value(value="file.output.exception.location")
	private String exceptionOutput;
	
	
	public void processDataEntryies(List<String> entry){
		
		CollationHeaderDTO header = dataService.getCollationHeaderDAO().findAll().iterator().next();
		//printInfo(entry);
		if(!validator.validateIsFirstRowInFile(entry)){
			if(true){// validator.validateEntryForNullsOrEmpty(entry)){
				CollectionsDTO collection = new CollectionsDTO(entry);
				  
				collection.setAccount_number_trace(header.getPps_account_number_trace());
				collection.setBsb_number_in_format(header.getPps_bsb_number());
				//collection.setName_of_remitter(header.getPps_remitter_name());	
				collection.setName_of_remitter(header.getName_of_user_supplying_file());
				  
 				createCollection(collection);
			}else{
				//Write to csv file 
				//if file doesnt exist 
				//then create it else use the existing one and append to it.
			}
		}
	}

	private void printInfo(List<String> list) {
		System.out.println("============");
		System.out.println( "Member Number :" + list.get(0) );
		System.out.println( "Benefit Number :" +list.get(2) );
		System.out.println( "Anniversary Date :" +list.get(13) );
		System.out.println( "Monthly Premium :" +list.get(14) );
		System.out.println( "Bill Raised :" +list.get(15) );
		System.out.println( "Frequency :" +list.get(18) );
		System.out.println( "Payment Method :" +list.get(19) );
		System.out.println( "Collection Day :" +list.get(22) );
		System.out.println( "Executed Day :" +list.get(25) );
		System.out.println( "Account Holder Name :" +list.get(30) );
		System.out.println( "Account Number :" +list.get(32) );
		System.out.println( "Credit Card Expiry Date :" +list.get(33) );

		
	}
	
	public void collateRelatedData(){
		
		List<Long> list = dataService.getCollectionDAO().getDistinctMemberNumbers();
		for(Long memberNum : list){
			List<CollectionsDTO> collections = dataService.getCollectionDAO().findCollectionByMemberNumber(memberNum);
			wrapper.getWrapper().put(memberNum,collections );
	    }
		wrapper.collateObjects();
		wrapper.getWrapper().clear();
	}
	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#findAllCollation()
	 */
	@Override
	public Collection<CollationDTO> findAllCollation() {
		// TODO Auto-generated method stub
		return dataService.getCollationDAO().findAll();
	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#findAllCollections()
	 */
	@Override
	public Collection<CollectionsDTO> findAllCollections() {
		return dataService.getCollectionDAO().findAll();

	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#findCollation(java.lang.Long)
	 */
	@Override
	public CollationDTO findCollation(Long id) {
		// TODO Auto-generated method stub
		return dataService.getCollationDAO().findOne(id);
	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#findCollection(java.lang.Long)
	 */
	@Override
	public CollectionsDTO findCollection(Long id) {
		return dataService.getCollectionDAO().findOne(id);
	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#createCollation(za.co.pps.bank.mutual.data.model.CollationDTO)
	 */
	@Override
	public CollationDTO createCollation(CollationDTO collation) {
		// TODO Auto-generated method stub
		return dataService.getCollationDAO().save(collation);
	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#createCollection(za.co.pps.bank.mutual.data.model.CollectiosDTO)
	 */
	@Override
	public CollectionsDTO createCollection(CollectionsDTO collection) {
		// TODO Auto-generated method stub
		//System.out.println( collection.toString() );
		return dataService.getCollectionDAO().save(collection);
	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#updateCollation(za.co.pps.bank.mutual.data.model.CollationDTO)
	 */
	@Override
	public CollationDTO updateCollation(CollationDTO collation) {
		// TODO Auto-generated method stub
		return dataService.getCollationDAO().save( collation );
	}

	/* (non-Javadoc)
	 * @see za.co.pps.bank.mutual.data.DataServiceLayer#updateCollection(za.co.pps.bank.mutual.data.model.CollectiosDTO)
	 */
	@Override
	public CollectionsDTO updateCollection(CollectionsDTO collection) {
		// TODO Auto-generated method stub
		return null;
	}

		
}
