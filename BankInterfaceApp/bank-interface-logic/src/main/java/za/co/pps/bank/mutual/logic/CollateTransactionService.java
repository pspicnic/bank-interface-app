package za.co.pps.bank.mutual.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.pps.bank.mutual.data.DataServiceDAO;
import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 23 Aug 2017
 * @version 1.0
 */
@Service
public class CollateTransactionService { //I use the CollatioWrapper 
	
	private Map<Long,List<CollectionsDTO>> collationWrapper; 
	
	@Autowired
	DataServiceDAO dataService;
	
	
	public void collateObjects(){
		
		for(Long key : this.collationWrapper.keySet()){
			List<CollectionsDTO> collections = (List<CollectionsDTO>)this.collationWrapper.get(key);
	        Set<CollectionsDTO> tmpCollections = new HashSet<CollectionsDTO>();
			for (CollectionsDTO collectionsDTO : collections) {
				   contains_(collectionsDTO, collections, tmpCollections);
			}
			for (CollectionsDTO tmpCol : tmpCollections) {
				collections.remove(tmpCol);
			}
			createCollationsAndUpdateCollections(key, collections, tmpCollections);
		}
	}

	public void contains_(CollectionsDTO obj,List<CollectionsDTO> self, Set<CollectionsDTO> tmpCollections ) {
		for (CollectionsDTO entry : self) {
			 if(entry.getMember_number().longValue() == obj.getMember_number().longValue()
				&& entry.getBank().equalsIgnoreCase(obj.getBank())
				&& entry.getAccount_number().equalsIgnoreCase(obj.getAccount_number())
				){
				 tmpCollections.add(obj);
			 }
        }
     
    }
	
	public CollationDTO createCollationData(Long key, List<CollectionsDTO> tmpCollections ) {
	            
		 CollationDTO collationData = new CollationDTO();
		 BigDecimal totalMonthlyPremium = BigDecimal.ZERO;
		 BigDecimal totalBillRaised = BigDecimal.ZERO;
		 Long member_number=0L;
		
		 Date anniversary_date=null;
		 String frequency="";
		 String paymentMethod="";
		 String account_holder_name="";
		 String bank="";
		 String account_number="";
		
		
		for (CollectionsDTO tmpCol : tmpCollections) {
			 totalBillRaised = totalBillRaised.add(tmpCol.getBill_raised());
			 totalMonthlyPremium = totalMonthlyPremium.add(tmpCol.getMonthly_premium());
			 if(tmpCol.getAnniversary_date() != null){
				 anniversary_date = tmpCol.getAnniversary_date();
			 }
			 if(tmpCol.getFrequency() != null){
				 frequency = tmpCol.getFrequency();
			 }
			 if(tmpCol.getPayment_method() != null){
				 paymentMethod = tmpCol.getPayment_method();
			 }
			 if(tmpCol.getAccount_holder_name() != null){
				 account_holder_name = tmpCol.getAccount_holder_name();
			 }
			 if(tmpCol.getBank() != null){
				 bank = tmpCol.getBank();
			 }
			 if(tmpCol.getAccount_number() != null){
				 account_number = tmpCol.getAccount_number(); 
			 }
			 if(tmpCol.getMember_number() != null){
				 member_number = tmpCol.getMember_number(); 
			 }
		}
		
		collationData.setAccount_holder_name(account_holder_name);
		collationData.setAccount_number(account_number);
		collationData.setTotal_bill_raised(totalBillRaised);
		collationData.setTotal_monthly_premium(totalMonthlyPremium);
		collationData.setFrequency(frequency);
		collationData.setPayment_method(paymentMethod);
		collationData.setAnniversary_date(anniversary_date);
		collationData.setBank(bank);
		collationData.setMember_number(member_number);
		
		return  dataService.getCollationDAO().save(collationData);
		
	}

	public void updateCollections( CollationDTO collationData, List<CollectionsDTO> tmpCollections ){
		for (CollectionsDTO collectionsDTO : tmpCollections) {
			collectionsDTO.setCollation_id(collationData.getId());
		}
		dataService.getCollectionDAO().save(tmpCollections);
	}
	
	private void createCollationsAndUpdateCollections(Long key, List<CollectionsDTO> collections,
			Set<CollectionsDTO> tmpCollections) {
		if(!collections.isEmpty()){
			updateCollections(createCollationData(key, collections), collections);  
		}
		if(!tmpCollections.isEmpty()){
			List<CollectionsDTO> collectionList = new ArrayList<CollectionsDTO>(tmpCollections);
			updateCollections(createCollationData(key,collectionList ),collectionList);
		}
	}
	
	
	
}
