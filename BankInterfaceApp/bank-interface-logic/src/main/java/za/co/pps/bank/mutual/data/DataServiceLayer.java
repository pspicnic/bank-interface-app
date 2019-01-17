/**
 * 
 */
package za.co.pps.bank.mutual.data;

import java.util.Collection;
import java.util.List;

import za.co.pps.bank.mutual.data.model.CollationDTO;
import za.co.pps.bank.mutual.data.model.CollectionsDTO;


/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 21 Aug 2017
 * @version 1.0
 */
public interface DataServiceLayer {
	
	public Collection<CollationDTO> findAllCollation();
	public  Collection<CollectionsDTO> findAllCollections();
	
	public  CollationDTO findCollation(Long id);
	public  CollectionsDTO findCollection(Long id);
	
	public CollationDTO createCollation(CollationDTO collation);
	public  CollectionsDTO createCollection(CollectionsDTO collection);

	public  CollationDTO updateCollation(CollationDTO collation);
	public  CollectionsDTO updateCollection(CollectionsDTO collection);
	
	public void processDataEntryies(List<String> entry);
	public void collateRelatedData();


}
