package id.com.templates.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.com.templates.model.account.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>{
	List<Transaction> findAllByAccountNumber(String account);
	List<Transaction> findAllByAccountNumberAndTransactionDateBetweenOrderByTimestamp(String account, Date first, Date end);
	List<Transaction> findTop5AllByAccountNumberOrderByTimestampDesc(String account);
	List<Transaction> findTop10AllByAccountNumberOrderByTimestampDesc(String account);
	List<Transaction> findTop15AllByAccountNumberOrderByTimestampDesc(String account);
	
	@Query(value="select coalesce(sum(txamt), 0.00) as amount from mst_trx where accnbr = :account", nativeQuery=true)
	BigDecimal balanceTransaction(@Param("account") String account);
}
