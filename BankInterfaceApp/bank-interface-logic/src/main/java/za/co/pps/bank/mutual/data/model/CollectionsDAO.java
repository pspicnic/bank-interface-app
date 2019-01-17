/**
 * 
 */
package za.co.pps.bank.mutual.data.model;

import java.util.Collections;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.co.pps.bank.mutual.data.queries.BankInterfaceQueryEnum;

/**
 * @author <a href="mailto:s.singatha@gmail.com">Sonwabo Singatha</a>
 * @date 17 Aug 2017
 * @version 1.0
 */
@Repository
public interface CollectionsDAO extends JpaRepository<CollectionsDTO, Long> {

	@Query(BankInterfaceQueryEnum.Constants.FIND_COLLECTION_BY_ACC_NUMBER)
	public List<CollectionsDTO> findCollectionByAccountNumber(@Param("acc_num")String acc_num);
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_COLLECTION_BY_MEMBER_NUMBER)
	public List<CollectionsDTO> findCollectionByMemberNumber(@Param("member_num")Long member_num);
	
	@Query(BankInterfaceQueryEnum.Constants.GET_DISTINCT_MEMBER_NUMBERS)
	public List<Long> getDistinctMemberNumbers();
	
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_COLLECTIONS_WITH_SAME_ACC_NUMBER_AND_ACC_TYPE)
	public List<CollectionsDTO> findCollectionWithSameCollationValues(@Param("member_num")String member_num,
																   @Param("col_day")String col_day,
																   @Param("bank")String bank,
																   @Param("acc_num")String acc_num);
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_COLLECTION_BY_COLLATION_ID)
	public List<CollectionsDTO> findCollectionsByCollationId(@Param("collation_id")Long collation_id);
																   
	
}
