/**
 * 
 */
package za.co.pps.bank.mutual.data.model;

import java.math.BigDecimal;
import java.util.Date;
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
public interface CollationDAO extends JpaRepository<CollationDTO, Long>{
	
	
	@Query(BankInterfaceQueryEnum.Constants. )
	public List<CollationDTO> findCollationByAccountType(@Param("accountType")String accountType);
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_UNPROCESSED_COLLATIONS)
	public List<CollationDTO> findUnProcessedCollations();
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_UNPROCESSED_COLLATIONS_BY_TYPE)
	public List<CollationDTO> findUnProcessedCollationsByType(@Param("account_type")String accountType, @Param("collection_date")java.util.Date collection_date, @Param("zero_closing_balance")BigDecimal closing_balance); 
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_BACK_DATED_COLLATIONS_BY_BANK_ACCOUNT_TYPE_AND_DATE)
	public List<CollationDTO> findBackDatedUnProcessedCollationsByType(@Param("account_type")String accountType, @Param("collection_date")java.util.Date collection_date, @Param("zero_closing_balance")BigDecimal closing_balance); 
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_COLLATIONS_BY_STATUS)
	public List<CollationDTO> findCollationsByStatus(@Param("status")String status); 
	
	@Query(BankInterfaceQueryEnum.Constants.FIND_COLLATIONS_BY_UUID)
	public List<CollationDTO> findCollationsByUUID(@Param("uuid")String uuid);
	
	@Query(BankInterfaceQueryEnum.Constants.GET_MAX_COLLECTION_DATE)
	public Date getMaxCollectionDate();
}
